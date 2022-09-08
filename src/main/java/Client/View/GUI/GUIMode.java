package Client.View.GUI;

import Client.Client;
import Client.Main;
import Client.View.CLI.CLIStaticMethods;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIMode {
    private Client client;
    private Stage stage;

    public GUIMode(Client client, Stage stage){
        this.client = client;
        this.stage = stage;
    }

    /**
     * shows GUI for GUI or CLI play mode choice
     * @see GUIUsername for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        AnchorPane layer = new AnchorPane();
        layer.autosize();

        VBox box = new VBox(20);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        Button GUIButton = new Button("GUI");
        GUIButton.autosize();
        GUIButton.setOnAction(e->{
            client.getHandler().getMainController().setUsingCLI(false);
            stage.setFullScreen(true);
            GUIUsername firstPane = new GUIUsername(client);
            firstPane.showGUI();
        });
        GUIButton.setStyle("-fx-font-weight: bolder; -fx-background-color: #e88e40;");

        Button CLIButton = new Button("CLI");
        CLIButton.autosize();
        CLIButton.setOnAction(e->{
            client.getHandler().getMainController().setUsingCLI(true);
            ExecutorService ex = Executors.newSingleThreadExecutor();
            ex.submit(new CLIStaticMethods(client));
            stage.close();
        });
        CLIButton.setStyle("-fx-font-weight: bolder; -fx-background-color: #e88e40;");

        box.getChildren().addAll(GUIButton, CLIButton);
        layer.getChildren().add(box);

        AnchorPane.setLeftAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);

        Main.getRootLayout().setCenter(layer);
    }

}
