package Client.View.GUI.PlayerHUDGUIItems;

import Client.View.GUI.GUIPlayerHUD;
import Shared.Model.Dice.Dice;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.ArrayList;

public class GUIDraftPool {
    private HBox draftPoolBox;
    private static final double VIEWHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;
    private static final double VIEWWIDHT = GUIPlayerHUD.getBoxWidht();

    public GUIDraftPool(ArrayList<Dice> draftPool){
        if(draftPoolBox==null)
            draftPoolBox = new HBox(VIEWWIDHT/(draftPool.size()*2));
        draftPoolBox.setAlignment(Pos.CENTER);
        draftPoolBox.autosize();

        for(int i=0; i<draftPool.size(); i++){
            ImageView imageView = new ImageView(new Image("Images/Dice/W0.png"));
            imageView.autosize();
            imageView.setId(draftPool.get(i).getColor().toString() + draftPool.get(i).getTop());
            imageView.setFitHeight(VIEWWIDHT/(draftPool.size()*2));
            imageView.setFitWidth(VIEWWIDHT/(draftPool.size()*2));

            draftPoolBox.getChildren().add(imageView);
        }
    }

    public HBox updateDraftPoolBox(ArrayList<Dice> draftPool){
        Platform.runLater(()-> {
            if(draftPoolBox==null)
                draftPoolBox = new HBox(VIEWWIDHT/(draftPool.size()*2));
            int i = 0;
            ImageView actualView;
            ObservableList<Node> children = draftPoolBox.getChildren();
            for(Node node : children){
                ((ImageView)node).setImage(null);
            }
            try {
                if (draftPool != null) {
                    for (Dice dice : draftPool) {
                        actualView = (ImageView) children.get(i);
                        actualView.setImage(new Image("Images/Dice/" + dice.getColor().toString() + dice.getTop() + ".png"));
                        actualView.setId("draftpoolButton" + i);
                        i++;
                    }
                }
            } catch (IndexOutOfBoundsException e){
                System.err.println("Error in update draftpoolbox");
            }
        });
        return draftPoolBox;
    }

    public void setButtonDraftPoolNull(){
        for(Node child : draftPoolBox.getChildren()){
            ((Button)child).setOnAction(null);
        }
    }

    public HBox getDraftPoolBox() {
        return draftPoolBox;
    }

    public static double getHeight(){
        return VIEWHEIGHT;
    }
}
