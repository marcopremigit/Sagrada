package Client.View.GUI.PlayerHUDGUIItems;

import Client.Client;
import Shared.Color;
import Shared.Model.Schemes.Scheme;
import Client.View.GUI.GUIPlayerHUD;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;

public class GUIFacade {
    private Client client;
    private VBox box;
    private Color facadeColor;
    private GridPane gridPane;
    private static final double GRIDWIDHT = GUIPlayerHUD.getBoxWidht() - GUIPlayerHUD.getBoxWidht()/4;
    private StringProperty favorsString;

    /**
     * Constructor
     * @param client client's controller
     * @param color color of the player. Used for showing different facades on GUI view.
     * @author Fabrizio Siciliano
     * */
    public GUIFacade(Color color, Client client){
        this.client = client;
        box = new VBox();
        box.autosize();

        //create grid
        gridPane = new GridPane();
        gridPane.autosize();
        gridPane.setPrefSize(GRIDWIDHT, GRIDWIDHT);
        gridPane.setMinSize(GRIDWIDHT, GRIDWIDHT);
        gridPane.setMaxSize(GRIDWIDHT, GRIDWIDHT);

        facadeColor = color;
    }

    /**
     * creates view for player's scheme
     * @param scheme scheme of the player
     * @return new {@link VBox} created
     * @author Fabrizio Siciliano
     * */
    public VBox createFacade(Scheme scheme){
        //create background
        ImageView imageView = new ImageView(new Image("Images/Facade/facciataHalf" + facadeColor.toString() +".png"));
        imageView.setFitWidth(GRIDWIDHT);
        imageView.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/ 2.5);
        imageView.autosize();

        for(int i=0; i<scheme.getScheme().length; i++){
            for(int j=0; j<scheme.getScheme()[i].length; j++){
                ImageView newImageView = new ImageView();
                String url ;

                if (!scheme.getScheme()[i][j].getColor().equals(Color.WHITE)) {
                    url = "Images/Dice/" + scheme.getScheme()[i][j].getColor().toString() + "0.png";
                } else {
                    url = "Images/Dice/W" + scheme.getScheme()[i][j].getNum() + ".png";
                }


                newImageView.setImage(new Image(url));
                newImageView.setFitHeight(GRIDWIDHT/scheme.getScheme().length);
                newImageView.setFitWidth(GRIDWIDHT/scheme.getScheme()[i].length);
                newImageView.autosize();

                newImageView.setOnMouseClicked(null);

                gridPane.add(newImageView, j, i);
            }
        }

        //create favors Label
        TextField textField = new TextField("Favori: NA");
        textField.autosize();
        favorsString = new SimpleStringProperty("Favors: " + client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
        textField.textProperty().bind(favorsString);
        textField.setOpacity(1);
        textField.setStyle("-fx-font-weight: bolder");
        textField.setAlignment(Pos.CENTER);
        textField.setDisable(true);
        textField.setMinWidth(GRIDWIDHT);
        textField.setMaxWidth(GRIDWIDHT);
        textField.setPrefWidth(GRIDWIDHT);
        if(client.getHandler().getMainController().isSinglePlayer()){
            box.getChildren().addAll(imageView, gridPane);
        }else{
            box.getChildren().addAll(imageView, gridPane, textField);
        }

        return box;
    }

    /**
     * updates favors of the player (after usage of a toolcard)
     * @param newFavors value of the favors to be set
     * @author Fabrizio Siciliano
     * */
    public void updateFavors(int newFavors){
        favorsString.setValue("Favori: " + newFavors);
    }

    /**
     * updates grid with given {@link Scheme}
     * @param scheme scheme to be updated
     * @author Fabrizio Siciliano
     * */
    public void updategrid(Scheme scheme){
        ObservableList<Node> children = gridPane.getChildren();
        for(Node child : children){
            String url = "Images/Dice/";
            ImageView view = (ImageView) child;
            if(scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].isOccupied()){
                url += scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getDado().getColor().toString() + scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getDado().getTop() + ".png";
            }
            else{
                url += scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getColor().toString() + scheme.getScheme()[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)].getNum() + ".png";
            }
            view.setImage(new Image(url));
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
