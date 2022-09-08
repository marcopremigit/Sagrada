package Client.View.GUI;

import Client.Client;
import Client.Main;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;

public class GUIEndGame{
    private Client client;
    private boolean singlePlayer;

    public GUIEndGame(Client client){
        this.client = client;
        this.singlePlayer = client.getHandler().getMainController().isSinglePlayer();
    }

    /**
     * creates GUI for ending game phase. Closes application after 10 seconds
     * @author Fabrizio Siciliano
     * */
    public void showGUI() {
        Main.setBackgroundImage(Main.getMainBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        if(!singlePlayer) {
            ArrayList<String> ranking = client.getHandler().getSetupController().getRanking();
            if (ranking != null) {
                for (int i = 0; i < ranking.size(); i++) {
                    TextField field = new TextField();
                    field.setDisable(true);
                    field.setOpacity(2.0);
                    field.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
                    field.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
                    field.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
                    int points = client.getHandler().getSetupController().getPoints(ranking.get(i));
                    field.setText((i + 1) + "° posto: " + ranking.get(i) + " ha totalizzato " + points + " punti");
                    field.setStyle("-fx-font-weight: bolder;");
                    box.getChildren().add(field);
                }
            }
        }
        else{
            TextField myPoints = new TextField();
            myPoints.setDisable(true);
            myPoints.setOpacity(2.0);
            myPoints.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
            myPoints.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
            myPoints.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
            int personalPoints = client.getHandler().getSetupController().getPoints(client.getHandler().getMainController().getPlayerName());
            myPoints.setText("I miei punti: " + personalPoints);

            TextField objectivePoints = new TextField();
            objectivePoints.setDisable(true);
            objectivePoints.setOpacity(2.0);
            objectivePoints.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
            objectivePoints.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
            objectivePoints.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4);
            int totalPoints = client.getHandler().getSetupController().getObjectivePoints();
            objectivePoints.setText("I punti per vincere: " + totalPoints);

            box.getChildren().addAll(myPoints, objectivePoints);
        }

        Task worker = createWorker();
        ProgressBar progressBar = new ProgressBar();
        progressBar.progressProperty().bind(worker.progressProperty());

        TextField text = new TextField();
        text.textProperty().bind(worker.messageProperty());
        text.setDisable(true);
        text.setOpacity(2.0);
        text.prefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        text.minWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        text.maxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        text.setStyle("-fx-font-weight: bolder");

        Button exitButton = new Button("ESCI");
        exitButton.setOnAction(e->System.exit(0));

        box.getChildren().addAll(progressBar, text, exitButton);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);

        //add labels to pane
        layer.getChildren().addAll(box);
        AnchorPane.setLeftAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);
        //pane to layout
        Main.getRootLayout().setCenter(layer);
        (new Thread(worker)).start();
    }

    /**
     * creates worker thread for closing application timer
     * @return {@link Task} of the worker
     * @author Fabrizio Siciliano
     * */
    private Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
            for (int i = 0; i <= 10; i++) {
                Thread.sleep(1000);
                updateMessage("Chiudo tra " + (10 - i) + " secondi");
                updateProgress(i + 1, 10);
            }
            System.exit(60);
            return true;
            }
        };
    }
}
