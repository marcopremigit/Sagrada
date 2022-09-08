package Client.View.GUI;

import Client.Client;
import Client.Main;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUIWaitForLobbyReady {
    private Client client;
    GUIWaitForLobbyReady(Client client){
        this.client = client;
    }


    /**
     * shows GUI for lobby syncing
     * @see GUIPlayerHUD for next screen
     * @author Fabrizio Siciliano
     * */
    public void showGUI() {
        Main.setBackgroundImage(Main.getMainBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();

        VBox box = new VBox(30);
        box.setAlignment(Pos.CENTER);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(-1.0);

        TextField field = new TextField("Aspetto che la lobby sia pronta");
        field.setStyle("-fx-text-alignment: center; -fx-font-weight: bolder; ");
        field.setDisable(true);
        field.setOpacity(2.0);

        box.getChildren().addAll(field, progressBar);
        layer.getChildren().add(box);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);

        Main.getRootLayout().setCenter(layer);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if(client.getHandler().getMainController().isGameStarted()) {
                Platform.runLater(() -> {
                    GUIChooseSchemeBox fifthPane = new GUIChooseSchemeBox(client);
                    fifthPane.showGUI();
                    scheduler.shutdown();
                });
            } else if(client.getHandler().getMainController().canContinueGame()){
                Platform.runLater(()-> {
                    GUIPlayerHUD pane = new GUIPlayerHUD(client);
                    client.getHandler().getMainController().setPlayerHUD(pane);
                    pane.showGUI();
                    pane.setGameStarted(true);
                    scheduler.shutdown();
                });
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}
