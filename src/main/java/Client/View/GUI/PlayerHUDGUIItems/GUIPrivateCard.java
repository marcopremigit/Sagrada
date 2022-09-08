package Client.View.GUI.PlayerHUDGUIItems;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.ArrayList;

public class GUIPrivateCard {
    private HBox box;
    private static final double HSPACING = 5;
    private static final double IMAGEWIDHT = GUIPlayerHUD.getBoxWidht();
    private static final double IMAGEHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4;

    public GUIPrivateCard(){
        box = new HBox();
        box.setSpacing(HSPACING);
        box.setAlignment(Pos.CENTER);
        box.autosize();
    }

    /**
     * creates view for {@link PrivateObjective} cards
     * @param cards list of {@link PrivateObjective} cards to be shown
     * @author Fabrizio Siciliano
     * */
    public HBox createPrivateCardBox(ArrayList<PrivateObjective> cards){
        for(PrivateObjective card : cards){
            String url = "Images/ObjectiveCards/Private/private" + card.getColor().toString() +".jpg";
            ImageView newPrivateCard = new ImageView(new Image(url));
            newPrivateCard.autosize();
            newPrivateCard.setFitHeight(IMAGEHEIGHT);
            newPrivateCard.setFitWidth(IMAGEWIDHT / 3);
            newPrivateCard.setOnMouseClicked(e-> GUIAlertBox.displayCard("Carta privata" , url));
            box.getChildren().add(newPrivateCard);
        }
        return box;
    }
}
