package Client.View.GUI;


import Client.Client;
import Shared.Color;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Client.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;


public class GUIChooseSchemeBox {
    private Client client;
    private static final double GRIDSIZE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4 + 10;
    private static final double BUTTONSIZE = GRIDSIZE / 5;
    private static final double IMAGEWIDHT = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 10;
    private static final double IMAGEHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 6;

    public GUIChooseSchemeBox(Client client){
        this.client = client;
    }

    /**
     * shows GUI for user's scheme choice
     * @see GUIPlayerHUD for next
     * @author Marco Premi
     * */
    public void showGUI(){
        Main.setBackgroundImage(Main.getPlayingBackground());

        AnchorPane layer = new AnchorPane();
        layer.autosize();
        ArrayList<PrivateObjective> privateObjectives = client.getHandler().getCardsController().getPrivateObjective();
        ArrayList<PublicObjective> publicObjectives = client.getHandler().getCardsController().getPublicObjectives();
        SchemeCard schemeCard1 = client.getHandler().getCardsController().getSchemeCard();
        SchemeCard schemeCard2 = client.getHandler().getCardsController().getSchemeCard();

        HBox box = new HBox(BUTTONSIZE*2);
        box.setAlignment(Pos.CENTER);

        VBox boxRight = new VBox(BUTTONSIZE);
        boxRight.autosize();

        Button rightButton = new Button("Scegli questo");
        rightButton.setId(schemeCard1.getFront().getName());
        rightButton.setOnAction(e -> {
            client.getHandler().getCardsController().setScheme(rightButton.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(client);
            client.getHandler().getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        Button rightButton2 = new Button("Scegli questo");
        rightButton2.setId(schemeCard1.getRear().getName());
        rightButton2.setOnAction(e -> {
            client.getHandler().getCardsController().setScheme(rightButton2.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(client);
            client.getHandler().getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        VBox boxLeft = new VBox(BUTTONSIZE);
        Button leftButton;
        boxLeft.autosize();
        leftButton = new Button("Scegli questo");
        leftButton.setId(schemeCard2.getFront().getName());
        leftButton.setOnAction(e -> {
            client.getHandler().getCardsController().setScheme(leftButton.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(client);
            client.getHandler().getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        Button leftButton2 = new Button("Scegli questo");
        leftButton2.setId(schemeCard2.getRear().getName());
        leftButton2.setOnAction(e -> {
            client.getHandler().getCardsController().setScheme(leftButton2.getId());
            GUIPlayerHUD pane = new GUIPlayerHUD(client);
            client.getHandler().getMainController().setPlayerHUD(pane);
            pane.showGUI();
        });

        GridPane gridPaneRight = new GridPane();
        GridPane gridPaneRight2 = new GridPane();
        GridPane gridPaneLeft = new GridPane();
        GridPane gridPaneLeft2 = new GridPane();

        boxRight.getChildren().addAll(rightButton,  createGrid(gridPaneRight, schemeCard1.getFront()), rightButton2, createGrid(gridPaneRight2, schemeCard1.getRear()));
        boxRight.setAlignment(Pos.CENTER);

        boxLeft.getChildren().addAll(leftButton, createGrid(gridPaneLeft, schemeCard2.getFront()), leftButton2, createGrid(gridPaneLeft2, schemeCard2.getRear()));
        boxLeft.setAlignment(Pos.CENTER);

        //------------------------------------Private anc Public cards show ---------------------------
        VBox objectBox = new VBox((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (IMAGEHEIGHT*4))/4);
        objectBox.setAlignment(Pos.CENTER);

        //private cards setup
        for(PrivateObjective privateObjective: privateObjectives){
            String privateURL = "Images/ObjectiveCards/Private/private"+ privateObjective.getColor().toString() +".jpg";
            Image privateObjectImage = new Image(privateURL,IMAGEWIDHT, IMAGEHEIGHT, false, false);
            ImageView privateObjectImageView = new ImageView(privateObjectImage);
            privateObjectImageView.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta pubblica", privateURL));
            privateObjectImageView.autosize();
            objectBox.getChildren().addAll(privateObjectImageView);
        }

        //public cards setup
        for (PublicObjective publicObjective : publicObjectives) {
            String url;
            if(publicObjective != null)
                url = "Images/ObjectiveCards/Public/" + publicObjective.getName() + ".jpg";
            else
                url = "Images/ObjectiveCards/Public/publicCardsBack.jpg";
            Image publicCardImage = new Image(url, IMAGEWIDHT, IMAGEHEIGHT, false, false);
            ImageView newPublicCard = new ImageView(publicCardImage);
            newPublicCard.autosize();
            newPublicCard.setOnMouseClicked(e -> GUIAlertBox.displayCard("Carta pubblica", url));
            objectBox.getChildren().add(newPublicCard);
        }


        box.getChildren().addAll(boxLeft, boxRight, objectBox);
        layer.getChildren().add(box);

        AnchorPane.setBottomAnchor(box, 0.0);
        AnchorPane.setTopAnchor(box, 0.0);
        AnchorPane.setRightAnchor(box, 0.0);
        AnchorPane.setLeftAnchor(box, 0.0);

        Main.getRootLayout().setCenter(layer);
        //waits for all players to be ready
        new Thread(() -> {
            while(true) {
               try{
                   if(client.getHandler().getMainController().arePlayersReady()) {
                       client.getHandler().getMainController().getPlayerHUD().setGameStarted(true);
                       break;
                   }
               }catch (NullPointerException e){}
            }
        }).start();
    }

    /**
     * @param scheme {@link Scheme} to be shown
     * @param grid {@link GridPane} where to show the scheme
     * @return {@link VBox} with scheme's info ({@link GridPane} for scheme and {@link TextField} for favors value)
     * @author Marco Premi
     * */
    private VBox createGrid(GridPane grid, Scheme scheme) {
        VBox box = new VBox(10);
        box.autosize();
        double buttonSize = scheme.getScheme().length>= scheme.getScheme()[0].length ? GRIDSIZE/scheme.getScheme().length : GRIDSIZE/scheme.getScheme()[0].length;

        for (int i = 0; i < scheme.getScheme().length; i++) {
            for (int j = 0; j < scheme.getScheme()[i].length; j++) {
                Image image;
                if (!scheme.getScheme()[i][j].getColor().equals(Color.WHITE)) {
                    image = new Image("Images/Dice/" + scheme.getScheme()[i][j].getColor().toString() + "0.png");
                } else {
                    image = new Image("Images/Dice/W" + scheme.getScheme()[i][j].getNum() + ".png");
                }
                ImageView newPane = new ImageView(image);
                newPane.setFitHeight(buttonSize);
                newPane.setFitWidth(buttonSize);

                newPane.autosize();
                grid.add(newPane, j, i);
            }
        }
        BackgroundFill filling = new BackgroundFill(javafx.scene.paint.Color.rgb(216, 163, 67), null, null);
        grid.setBackground(new Background(filling));

        grid.setPrefHeight(GRIDSIZE);
        grid.setPrefWidth(GRIDSIZE);
        grid.autosize();
        grid.setAlignment(Pos.CENTER);
        if(!client.getHandler().getMainController().isSinglePlayer()) {
            TextField field = new TextField("Favori: " + scheme.getFavors());
            field.autosize();
            field.setDisable(true);
            field.setOpacity(2.0);
            field.setStyle("-fx-font-weight: bolder; -fx-text-alignment: center; ");
            box.getChildren().addAll(grid, field);
        }
        else{
            box.getChildren().addAll(grid);
        }

        return box;
    }
}
