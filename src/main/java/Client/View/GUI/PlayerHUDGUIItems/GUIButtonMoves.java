package Client.View.GUI.PlayerHUDGUIItems;

import Client.Client;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

public class GUIButtonMoves {
    private Client client;

    private volatile Button placeDiceButton;
    private volatile Button passTurnButton;
    private volatile BooleanProperty turnOnGoing;
    private volatile BooleanProperty diceMoved;

    public GUIButtonMoves(Client client){
        if(turnOnGoing==null)
            turnOnGoing = new SimpleBooleanProperty();
        this.client = client;
        this.diceMoved = new SimpleBooleanProperty();

    }

    /**
     * creates view for button actions
     * @return new {@link HBox} created with {@link GUIButtonMoves#passTurnButton} and {@link GUIButtonMoves#placeDiceButton}
     * @author Fabrizio Siciliano
     * */
    public HBox createButtonBox(){
        HBox box = new HBox(10);
        box.autosize();

        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setProgress(-1.0);
        indicator.setVisible(false);

        placeDiceButton = new Button("DADO");
        placeDiceButton.autosize();
        placeDiceButton.setOnAction(e->client.getToolViewController().useToolCard("Normal Move",true));

        passTurnButton = new Button("PASSO");
        passTurnButton.autosize();
        passTurnButton.setOnAction(e->{
            client.getHandler().getTurnController().endMyTurn();
            client.getHandler().getMainController().setMyTurn(false);
        });
        if(turnOnGoing.getValue().equals(Boolean.TRUE)){
            box.getChildren().addAll(placeDiceButton,passTurnButton);
        }

        turnOnGoing.addListener((observable, oldValue, newValue) -> Platform.runLater(()->{
            if(newValue){
                diceMoved.setValue(false);
                box.getChildren().removeAll(indicator);
                box.getChildren().addAll(placeDiceButton,passTurnButton);
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("E' il tuo turno. Siamo al round " + client.getHandler().getTurnController().whatRoundIs());
            } else{
                box.getChildren().removeAll(placeDiceButton,passTurnButton);
                box.getChildren().addAll(indicator);
                client.getHandler().getMainController().getPlayerHUD().resetEndTurn();
            }
        }));

        diceMoved.addListener((observable, oldValue, newValue) -> Platform.runLater(()->{
            if(!oldValue&&newValue){
                box.getChildren().remove(placeDiceButton);
            }
        }));
        return box;
    }

    /**
     * setter for {@link GUIButtonMoves#passTurnButton}
     * @param value value to be set
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public void setTurnOnGoing(boolean value){
        Platform.runLater(()->{
            if(turnOnGoing==null){
                turnOnGoing = new SimpleBooleanProperty();
            }
            turnOnGoing.setValue(value);
        });
    }

    /**
     * setter for {@link GUIButtonMoves#diceMoved}
     * @param value value to be set
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public void setDiceMoved(boolean value) {
        Platform.runLater(()->diceMoved.setValue(value));
    }

    BooleanProperty getDiceMoved(){
        return this.diceMoved;
    }
}