package Client.View.GUI;

import Client.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIConnection {
    private Stage stage;

    public GUIConnection(Stage stage){
        this.stage = stage;
    }

    /**
     * shows GUI for RMIClient or SocketClient choice
     * @see GUIServerSetup for next window shown
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        Main.setBackgroundImage(Main.getSetupBackeground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();
        VBox box = new VBox(20);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        Button RMIButton = new Button("RMI");
        RMIButton.autosize();
        RMIButton.setOnAction(e->{
            GUIServerSetup pane = new GUIServerSetup(stage);
            pane.showGUI(true);
        });
        RMIButton.setStyle("-fx-background-color: #e88e40; -fx-font-weight: bolder");

        Button socketButton = new Button("Socket");
        socketButton.autosize();
        socketButton.setStyle("-fx-background-color: #e88e40; -fx-font-weight: bolder");
        socketButton.setOnAction(e->{
            GUIServerSetup pane = new GUIServerSetup(stage);
            pane.showGUI(false);
        });

        box.getChildren().addAll(RMIButton, socketButton);
        layer.getChildren().add(box);

        AnchorPane.setLeftAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setBottomAnchor(box, 0.0);
        Main.getRootLayout().setCenter(layer);
    }
}
