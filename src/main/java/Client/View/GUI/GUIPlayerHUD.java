package Client.View.GUI;


import Client.Client;
import Client.Main;
import Client.View.GUI.PlayerHUDGUIItems.*;
import Shared.Model.ObjectiveCard.PrivateObjective;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUIPlayerHUD {
    private volatile GUIPlayersBox playersSchemes;
    private volatile GUIFacade playerFacade;
    private volatile GUIDraftPool draftPool;
    private volatile GUIRoundTrace roundTrace;
    private volatile GUIToolCards tools;
    private volatile GUIPublicCards publicCards;
    private volatile GUIPrivateCard privateCards;
    private volatile GUIButtonMoves buttonMoves;
    private volatile GUIHints hintsScreen;

    private volatile BooleanProperty gameStarted;
    private volatile BooleanProperty gameEnded;

    private Client client;

    private static final double BOXWIDHT = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3;
    private static final double VERTICALSPACING = 20;

    public GUIPlayerHUD(Client client){
        this.gameStarted    = new SimpleBooleanProperty();
        this.gameEnded      = new SimpleBooleanProperty();
        this.buttonMoves    = new GUIButtonMoves(client);
        this.client         = client;
    }

    /**
     * shows GUI player's HUD
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public void showGUI() {
        gameStarted.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                createPlayerHUD();
            }
        });

        if(!gameStarted.getValue()) {
            Main.setBackgroundImage(Main.getPlayingBackground());

            AnchorPane syncLayer = new AnchorPane();
            syncLayer.autosize();

            VBox syncBox = new VBox(50);
            syncBox.autosize();
            syncBox.setAlignment(Pos.CENTER);

            ProgressIndicator indicator = new ProgressIndicator();
            indicator.setProgress(-1.0);
            indicator.autosize();

            TextField field = new TextField("Aspetto il sync con la lobby");
            field.autosize();
            field.setDisable(true);
            field.setOpacity(2.0);
            field.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
            field.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
            field.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
            field.setStyle("-fx-font-weight: bolder; ");

            syncBox.getChildren().addAll(field, indicator);
            syncLayer.getChildren().add(syncBox);
            AnchorPane.setTopAnchor(syncBox, 0.0);
            AnchorPane.setBottomAnchor(syncBox, 0.0);
            AnchorPane.setRightAnchor(syncBox, 0.0);
            AnchorPane.setLeftAnchor(syncBox, 0.0);
            Main.getRootLayout().setCenter(syncLayer);
        }
        else{
            createPlayerHUD();
        }

        gameEnded.addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                Platform.runLater(()->{
                    GUIEndGame pane = new GUIEndGame(client);
                    pane.showGUI();
                });
            }
        }));
    }

    /**
     * creates HUD used during whole game phase
     * @author Fabrizio Siciliano
     * */
    private void createPlayerHUD(){
        Platform.runLater(()-> {
            AnchorPane layer = new AnchorPane();
            layer.autosize();

            //--------------------------------------------------create HUD elements--------------------------------------------------
            roundTrace = new GUIRoundTrace(client);
            HBox roundTraceBox = roundTrace.getRoundTrace();
            roundTraceBox.setAlignment(Pos.CENTER);

            draftPool = new GUIDraftPool(client.getHandler().getTableController().getDraftPool());
            HBox draftPoolBox = draftPool.updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            draftPoolBox.setAlignment(Pos.TOP_CENTER);

            tools = new GUIToolCards();
            HBox toolCardsBox = tools.createToolCardsBox(client);
            toolCardsBox.setAlignment(Pos.BOTTOM_CENTER);

            hintsScreen = new GUIHints();
            TextField hintsField = hintsScreen.createScreen();
            hintsField.setAlignment(Pos.BOTTOM_CENTER);

            publicCards = new GUIPublicCards();
            HBox publiCardsBox = publicCards.createPublicCardsBox(client.getHandler().getCardsController().getPublicObjectives());
            publiCardsBox.setAlignment(Pos.TOP_CENTER);

            HBox buttonMovesBox = buttonMoves.createButtonBox();
            buttonMovesBox.setAlignment(Pos.CENTER);

            privateCards = new GUIPrivateCard();
            HBox privateCardsBox = privateCards.createPrivateCardBox(client.getHandler().getCardsController().getPrivateObjective());
            privateCardsBox.setAlignment(Pos.BOTTOM_CENTER);

            playersSchemes = new GUIPlayersBox(client);
            HBox playersBox = playersSchemes.createPlayersBox(client.getHandler().getTableController().getOtherPlayersNames());
            playersBox.setAlignment(Pos.TOP_CENTER);

            playerFacade = new GUIFacade(client.getHandler().getSetupController().getPlayerColor(), client);
            VBox playerBox = playerFacade.createFacade(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
            playerBox.setAlignment(Pos.CENTER);
            //--------------------------------------------------create HUD elements--------------------------------------------------

            VBox leftBox = new VBox(VERTICALSPACING);
            leftBox.setAlignment(Pos.CENTER);
            leftBox.getChildren().addAll(roundTraceBox, draftPoolBox, toolCardsBox, hintsField);

            VBox centerBox = new VBox(VERTICALSPACING);
            centerBox.setAlignment(Pos.CENTER);
            centerBox.getChildren().addAll(publiCardsBox, buttonMovesBox, privateCardsBox);

            VBox rightBox = new VBox(VERTICALSPACING);
            rightBox.setAlignment(Pos.CENTER);
            rightBox.getChildren().addAll(playersBox, playerBox);

            HBox mainBox = new HBox(20);
            mainBox.autosize();
            mainBox.setAlignment(Pos.CENTER);
            mainBox.getChildren().addAll(leftBox, centerBox, rightBox);

            //add everything to layer
            layer.getChildren().addAll(mainBox);

            //add layer to pane
            Main.getRootLayout().setCenter(layer);

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                if (client.getHandler().getMainController().hasGameEnded()) {
                    if(!client.getHandler().getMainController().isSinglePlayer()) {
                        gameEnded.setValue(true);
                    } else {
                        Platform.runLater(()->{
                            layer.getChildren().removeAll(mainBox);
                            layer.getChildren().add(choosePrivateObjectiveBox());
                        });
                    }
                    scheduler.shutdownNow();
                }
            }, 0, 3, TimeUnit.SECONDS);
        });

    }

    /**
     * @param value value of game ended
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public void setGameEnded(boolean value){
        Platform.runLater(()->{
            if(gameEnded==null){
                gameEnded = new SimpleBooleanProperty();
            }
            gameEnded.setValue(value);
        });
    }

    /**
     * @return {@link GUIFacade} for further player's scheme and favors updates
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public GUIFacade getPlayerFacade() {
        return playerFacade;
    }

    /**
     * @return {@link GUIDraftPool} for further draftPool updates
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public GUIDraftPool getDraftPool() {
        return draftPool;
    }

    /**
     * @return {@link GUIRoundTrace} for further round trace updates
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public GUIRoundTrace getRoundTrace() {
        return roundTrace;
    }

    /**
     * @return {@link GUIToolCards} for further tool cards updates
     * @author Fabrizio Siciliano
     * */
    public GUIToolCards getTools() {
        return tools;
    }

    /**
     * @return {@link GUIButtonMoves} for further possible moves updates
     * @author Fabrizio Siciliano
     * */
    public GUIButtonMoves getButtonMoves() {
        return buttonMoves;
    }

    /**
     * @return {@link GUIHints} for further hints on screen updates
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public GUIHints getHintsScreen() {
        return hintsScreen;
    }

    /**
     * @return {@link GUIPlayersBox} for further updates on players' availability
     * @author Fabrizio Siciliano
     * */
    public GUIPlayersBox getPlayersSchemes() {
        return playersSchemes;
    }

    /**
     * @return value for single box width
     * @author Fabrizio Siciliano
     * */
    public static double getBoxWidht() {
        return BOXWIDHT;
    }

    /**
     * setter for {@link GUIPlayerHUD#gameStarted}
     * @param value boolean value for {@link GUIPlayerHUD#gameStarted} to be set
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void setGameStarted(boolean value){
        try{
            gameStarted.setValue(value);
        }catch (NullPointerException e){
            gameStarted = new SimpleBooleanProperty();
            gameStarted.setValue(value);
        }
    }

    /**
     * resets all function and values at the end of each turn
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public void resetEndTurn(){
        tools.setCardUsed(false);
        buttonMoves.setDiceMoved(false);
        GUIStaticMethods.setButtonFacadeNull(client);
        GUIStaticMethods.setButtonDraftPoolNull(client);
        GUIStaticMethods.setRoundTraceButtonNull(client);
        hintsScreen.updateScreen("Aspetta che gli altri giocatori finiscano i rispettivi turni");
    }

    /**
     * single player only: shows GUI for choosing private objective
     * @return {@link VBox} created
     * @author Fabrizio Siciliano
     * */
    private VBox choosePrivateObjectiveBox(){
        VBox box = new VBox(20);
        box.autosize();
        box.setAlignment(Pos.CENTER);

        VBox privates = new VBox(20);
        privates.setAlignment(Pos.CENTER);
        privates.autosize();
        for(PrivateObjective card : client.getHandler().getCardsController().getPrivateObjective()){
            String url = "Images/ObjectiveCards/Private/private" + card.getColor().toString() +".jpg";
            ImageView newPrivateCard = new ImageView(new Image(url));
            newPrivateCard.autosize();
            newPrivateCard.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4);
            newPrivateCard.setFitWidth(getBoxWidht()/ 3);
            newPrivateCard.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta privata" , url));

            Button button = new Button("Scegli questa");
            button.autosize();
            button.setOnAction(event -> client.getHandler().getCardsController().setPrivateObjective(card.getName()));

            privates.getChildren().addAll(newPrivateCard, button);
        }

        box.getChildren().addAll(privates);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (client.getHandler().getMainController().isServerComputing()) {
                gameEnded.setValue(true);
                scheduler.shutdownNow();
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
        return box;
    }
}
