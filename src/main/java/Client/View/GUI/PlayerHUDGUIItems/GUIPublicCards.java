package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Model.ObjectiveCard.PublicObjective;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.ArrayList;

public class GUIPublicCards {
    private HBox box;

    private static final double HSPACING = 5;
    private static final double IMAGEWIDHT = GUIPlayerHUD.getBoxWidht();
    private static final double IMAGEHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4;

    public GUIPublicCards(){
        box = new HBox();
        box.setSpacing(HSPACING);
        box.setAlignment(Pos.CENTER);
        box.autosize();
    }

    /**
     * creates box for public cards
     * @param publicCards list of {@link PublicObjective} cards to be shown
     * @return new {@link HBox} created
     * @author Fabrizio Siciliano
     * */
    public HBox createPublicCardsBox(ArrayList<PublicObjective> publicCards){
        for(int i=0; i<publicCards.size(); i++){
            String url = "Images/ObjectiveCards/Public/" + publicCards.get(i).getName() +".jpg";
            ImageView newPublicCard = new ImageView(new Image(url));
            newPublicCard.autosize();
            newPublicCard.setFitWidth(IMAGEWIDHT/publicCards.size());
            newPublicCard.setFitHeight(IMAGEHEIGHT);
            newPublicCard.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta pubblica" , url));
            box.getChildren().add(newPublicCard);
        }
        return box;
    }
}
