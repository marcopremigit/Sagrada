package Client.View.GUI;


import Client.Client;
import Client.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.awt.*;

public class GUISettingsMenu{
    private Client client;

    public GUISettingsMenu(Client client){
        this.client = client;
    }

    /**
     * creates GUI View for settings menu. More functionalities can be implemented
     * @see GUICustomCardsCreation for next window
     * @author Fabrizio Siciliano
     * */
    void showSettingsMenu(){
        AnchorPane layer = new AnchorPane();
        layer.autosize();

        HBox box = new HBox(10);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        Button createCustomCard = new Button("Crea un nuovo schema");
        createCustomCard.autosize();
        createCustomCard.setAlignment(Pos.CENTER);
        createCustomCard.setOnAction(e->{
            GUICustomCardsCreation pane = new GUICustomCardsCreation(client);
            pane.showCustomCardCreationMenu();
        });

        Button goBackButton = new Button("Torna al menù principale");
        goBackButton.autosize();
        goBackButton.setAlignment(Pos.CENTER);
        goBackButton.setOnAction(e-> {
            GUIMainMenu pane = new GUIMainMenu(client);
            pane.showGUI();
        });

        box.getChildren().addAll(createCustomCard, goBackButton);


        layer.getChildren().add(box);
        AnchorPane.setRightAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setLeftAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        AnchorPane.setTopAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
        AnchorPane.setBottomAnchor(box, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);

        Main.getRootLayout().setCenter(layer);
    }
}
