package Client.View.GUI.PlayerHUDGUIItems;

import Client.Client;
import Client.View.GUI.GUIPlayerHUD;
import Shared.Model.Tools.ToolCard;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;


public class GUIToolCards {
    private HBox box;
    private static final int HSPACING = 5;
    private static final double IMAGEHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;
    private BooleanProperty cardUsed;

    public GUIToolCards(){
        cardUsed = new SimpleBooleanProperty();
        cardUsed.setValue(false);
        box = new HBox();
        box.setSpacing(HSPACING);
        box.setAlignment(Pos.CENTER);
        box.autosize();
    }

    /**
     * creates view for tool cards
     * @param client client's controller
     * @return {@link HBox} created
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public HBox createToolCardsBox(Client client) {
        ArrayList<ToolCard> toolCards = client.getHandler().getToolController().getToolCards();
        double imageWidht;
        if(toolCards.size()>=3)
            imageWidht = GUIPlayerHUD.getBoxWidht()/toolCards.size();
        else
            imageWidht = GUIPlayerHUD.getBoxWidht()/3;
        for (ToolCard card : toolCards) {
            String imageURL = "Images/Tools/";
            switch (card.getName()) {
                case "Pinza Sgrossatrice": {
                    imageURL += "pinzaSgrossatrice.jpg";
                    break;
                }
                case "Pennello per Eglomise": {
                    imageURL += "pennelloPerEglomise.jpg";
                    break;
                }
                case "Alesatore per lamina di rame": {
                    imageURL += "alesatorePerLaminaDiRame.jpg";
                    break;
                }
                case "Lathekin": {
                    imageURL += "lathekin.jpg";
                    break;
                }
                case "Taglierina circolare": {
                    imageURL += "taglierinaCircolare.jpg";
                    break;
                }
                case "Pennello per pasta salda": {
                    imageURL += "pennelloPerPastaSalda.jpg";
                    break;
                }
                case "Martelletto": {
                    imageURL += "martelletto.jpg";
                    break;
                }
                case "Tenaglia a Rotelle": {
                    imageURL += "tenagliaARotelle.jpg";
                    break;
                }
                case "Riga in Sughero": {
                    imageURL += "rigaInSughero.jpg";
                    break;
                }
                case "Tampone Diamantato": {
                    imageURL += "tamponeDiamantato.jpg";
                    break;
                }
                case "Diluente per Pasta Salda": {
                    imageURL += "diluentePerPastaSalda.jpg";
                    break;
                }
                case "Taglierina Manuale": {
                    imageURL += "taglierinaManuale.jpg";
                    break;
                }
                default:
                    imageURL += "toolCardBack.jpg";
                    break;
            }
            Image toolCardBackground;
            try {
                toolCardBackground = new Image(imageURL);
            } catch (IllegalArgumentException i){
                toolCardBackground = new Image("Images/Tools/toolCardBack.jpg");
            }
            ImageView toolCard = new ImageView(toolCardBackground);
            toolCard.setFitHeight(IMAGEHEIGHT);
            toolCard.setFitWidth(imageWidht);
            String url = imageURL;
            toolCard.setOnMouseClicked(e -> {
                for(ToolCard toolCard1 : client.getHandler().getToolController().getToolCards()){
                    if(card.getName().equals(toolCard1.getName()))
                        showToolCard("Carta utensile", url, toolCard1, client);
                }
            });

            box.getChildren().add(toolCard);
        }
        return box;
    }

    /**
     * shows popup box for tool card zoom and usage
     * @param title title of the popup
     * @param card card to be shown
     * @param url url of the image to be shown
     * @param client client's controller
     * @author Marco Premi
     * */
    private void showToolCard(String title, String url, ToolCard card, Client client){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        //bottoni per usare la carta
        Button useToolButton = new Button("Usa la carta");
        useToolButton.visibleProperty().bind(cardUsed.not());
        useToolButton.setOnAction(e-> {
            if(client.getToolViewController().useToolCard(card.getName(), client.getHandler().getMainController().getPlayerHUD().getButtonMoves().getDiceMoved().getValue()))
                client.getHandler().getToolController().setUsed(client.getHandler().getMainController().getPlayerName(), card.getName());
            window.close();
        });

        Button closeButton = new Button("Chiudi questa finestra");
        closeButton.setOnAction(e -> window.close());
        TextField field = new TextField();
        if (!card.isUsed())
            field.setText("Costo: 1 favore");
        else
            field.setText("Costo: 2 favori");
        field.setDisable(true);
        field.autosize();
        field.setOpacity(2.0);
        field.setStyle("-fx-font-weight: bolder; -fx-text-alignment: center; ");

        HBox buttonBox = new HBox(10);
        if(client.getHandler().getMainController().isMyTurn())
            buttonBox.getChildren().addAll(field, useToolButton, closeButton);
        else
            buttonBox.getChildren().addAll(field, closeButton);
        buttonBox.setAlignment(Pos.CENTER);

        //imageView per la carta
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitHeight((Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3)*2);
        imageView.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        imageView.autosize();

        VBox layout = new VBox(10);
        layout.getChildren().addAll(imageView, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * setter for turn's usage of a card
     * @param cardUsed value of the card used
     * @author Fabrizio Siciliano
     * */
    public void setCardUsed(boolean cardUsed) {
        Platform.runLater(()-> this.cardUsed.setValue(cardUsed));
    }
}
