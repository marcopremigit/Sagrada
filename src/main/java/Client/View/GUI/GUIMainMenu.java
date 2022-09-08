package Client.View.GUI;

import Client.Client;
import Shared.Exceptions.LobbyFullException;
import Client.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class GUIMainMenu{
    private Client client;

    public GUIMainMenu(Client client){
        this.client = client;
    }

    /**
     * shows Main Menu
     * @see GUISinglePlayerSetup for next possible choice
     * @see GUIMultiPlayerSetup for next possible choice
     * @see GUISettingsMenu for next possible choice
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        Main.setBackgroundImage(Main.getMainBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();

        HBox mainBox = new HBox(20);
        mainBox.setAlignment(Pos.CENTER);

        Button singlePlayerButton = new Button("SinglePlayer");
        singlePlayerButton.autosize();
        singlePlayerButton.setOnAction(e-> {
            client.getHandler().getMainController().insertSinglePlayer(true);
            try{
                if(!client.getHandler().getSetupController().joinGame(true)){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("INFORMATION");
                    a.setHeaderText("SERVER BUSY");
                    a.setContentText("Il server è occupato. Riprova più tardi!");
                    a.showAndWait();
                    a.setOnCloseRequest(event -> System.exit(42));
                }
            }catch (LobbyFullException l){}
            GUISinglePlayerSetup thirdPane = new GUISinglePlayerSetup(client);
            thirdPane.showGUI();
        });

        Button multiPlayerButton = new Button("MultiPlayer");
        multiPlayerButton.autosize();
        multiPlayerButton.setOnAction(e-> {
            client.getHandler().getMainController().insertSinglePlayer(false);
            try {
                if(!client.getHandler().getSetupController().joinGame(false)){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("INFORMATION");
                    a.setHeaderText("SERVER BUSY");
                    a.setContentText("Il server è occupato. Riprova più tardi!");
                    a.showAndWait();
                    a.setOnCloseRequest(event -> System.exit(42));
                }
            } catch ( LobbyFullException  l ){
                if(l instanceof LobbyFullException){
                    GUIAlertBox.display("LobbyFullException", "La lobby è piena\nIl gioco verrà chiuso");
                    System.exit(5);
                } else if(l instanceof LobbyFullException){
                    GUIAlertBox.display("PlayerNotFoundException", "Non ho trovato il giocatore\nIl gioco verrà chiuso");
                    System.exit(6);
                } else{
                    GUIAlertBox.display("RemoteException", "Errore di comunicazione col server");
                }
            }
            GUIMultiPlayerSetup thirdPane = new GUIMultiPlayerSetup(client);
            thirdPane.showGUI();
        });

        Button settingsButton = new Button("Settings");
        settingsButton.autosize();
        settingsButton.setOnAction(e->{
            GUISettingsMenu pane = new GUISettingsMenu(client);
            pane.showSettingsMenu();
        });

        Button exitButton = new Button("Exit");
        exitButton.setAlignment(Pos.CENTER);
        exitButton.autosize();
        exitButton.setOnAction(e-> System.exit(0));


        mainBox.getChildren().addAll(singlePlayerButton, multiPlayerButton, settingsButton, exitButton);

        layer.getChildren().add(mainBox);
        AnchorPane.setLeftAnchor(mainBox, 0.0);
        AnchorPane.setRightAnchor(mainBox, 0.0);
        AnchorPane.setTopAnchor(mainBox, 0.0);
        AnchorPane.setBottomAnchor(mainBox, 0.0);

        Main.getRootLayout().setCenter(layer);
    }
}
