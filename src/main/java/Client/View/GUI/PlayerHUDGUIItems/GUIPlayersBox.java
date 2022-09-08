package Client.View.GUI.PlayerHUDGUIItems;

import Client.Client;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import Shared.Model.Schemes.Scheme;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GUIPlayersBox {
    private HBox playersBox;
    private static final double BUTTONWIDHT = GUIPlayerHUD.getBoxWidht();
    private Client client;


    public GUIPlayersBox(Client client){
        this.client = client;

        playersBox = new HBox(20);
        playersBox.autosize();
        playersBox.setAlignment(Pos.BOTTOM_CENTER);
    }

    /**
     * creates {@link HBox} for other players' info popup buttons
     * @param players list of players in the game
     * @author Marco Premi
     * */
    public HBox createPlayersBox(ArrayList<String> players){

        for(int i=0; i<players.size(); i++){
            final int j = i;
            Button newButton = new Button(players.get(i) + "'s scheme");
            newButton.setOnAction(e-> {
                Scheme scheme = client.getHandler().getTableController().getScheme(players.get(j));
                VBox playerBox = GUIAlertBox.createNewFacade(scheme, client.getHandler().getToolController().getFavours(players.get(j)));
                GUIAlertBox.showPlayerScheme(playerBox);
            });
            newButton.setId(players.get(i));
            newButton.autosize();
            newButton.setMaxWidth((BUTTONWIDHT/players.size()) - 20*players.size());
            newButton.setMinWidth((BUTTONWIDHT/players.size()) - 20*players.size());
            newButton.setPrefWidth((BUTTONWIDHT/players.size()) - 20*players.size());
                playersBox.getChildren().add(newButton);
        }
        return playersBox;
    }

    /**
     * sets view for player not available
     * @param name name of the player not available
     * @author Fabrizio Siciliano
     * */
    public void playerNotAvailable(String name){
        for(Node child : playersBox.getChildren()){
            if(name.equals(child.getId())){
                Button b = (Button) child;
                b.setStyle("-fx-background-color: #ff433d");
                break;
            }
        }
    }

    /**
     * sets view for player available
     * @param name name of the player available
     * @author Fabrizio Siciliano
     * */
    public void playerAvailable(String name){
        for(Node child : playersBox.getChildren()){
            if(name.equals(child.getId())){
                Button b = (Button) child;
                b.setStyle("-fx-background-color: #ffffff");
                break;
            }
        }
    }

}