package Client.View.GUI;

import Client.Client;
import Client.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;


public class GUISinglePlayerSetup{
    private Client client;

    public GUISinglePlayerSetup(Client client){
        this.client = client;
    }

    /**
     * creates GUI View for single player difficulty
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        Main.setBackgroundImage(Main.getPlayingBackground());
        AnchorPane layer = new AnchorPane();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        TextField field = new TextField("Inserisci a che difficoltà vuoi giocare");
        field.setDisable(true);
        field.setOpacity(2.0);
        field.setMinSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);
        field.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);
        field.setPrefSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5);

        Slider difficultySliderBar = new Slider();
        difficultySliderBar.setMin(1.0);
        difficultySliderBar.setMax(5.0);
        difficultySliderBar.setSnapToTicks(true);
        difficultySliderBar.setMajorTickUnit(1);
        difficultySliderBar.setMinorTickCount(0);
        difficultySliderBar.setShowTickLabels(true);
        difficultySliderBar.setShowTickMarks(true);

        Button playButton = new Button("Gioca");
        playButton.setOnAction(e-> {
            int difficulty = (int) difficultySliderBar.getValue();
            client.getHandler().getSetupController().setDifficulty(difficulty);
            GUIWaitForLobbyReady fourthPane = new GUIWaitForLobbyReady(client);
            fourthPane.showGUI();
        });


        box.getChildren().addAll(field, difficultySliderBar, playButton);
        layer.getChildren().add(box);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        Main.getRootLayout().setCenter(layer);
    }
}
