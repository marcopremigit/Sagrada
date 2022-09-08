package Client.View.GUI;

import Client.Client;
import Shared.Color;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class GUIAlertBox {
    private static final double VIEWWIDHT = GUIPlayerHUD.getBoxWidht();
    private static final double GRIDWIDHT = GUIPlayerHUD.getBoxWidht() - GUIPlayerHUD.getBoxWidht() /4;

    /**
     * displays popup for given title and message
     * @param title title of the popup box
     * @param message message of the popup box
     * @author Fabrizio Siciliano
     * */
    static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Chiudi questa finestra");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * displays popup for given title and image url
     * @param title title of the popup box
     * @param url url of the image to display
     * @author Fabrizio Siciliano
     * */
    public static void displayCard(String title, String url){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Image image = new Image(url, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2, false, false);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight((Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3)*2);
        imageView.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        imageView.autosize();

        Button closeButton = new Button("Chiudi questa finestra");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(imageView, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.centerOnScreen();
        window.showAndWait();
    }

    /**
     * shows popup box for increasing or decreasing value of a {@link Shared.Model.Dice.Dice}
     * @param client client's controller
     * @author Marco Premi
     * */
    public static void showIncreaseOrDecrease(Client client) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Aumenta o diminuisci?");

        Button increase = new Button("Aumenta di 1");
        increase.setOnAction(e->{
            client.getToolViewController().getPinzaSgrossatrice().setPinzaSgrossatrice2(true);
            window.close();
        });
        Button decrease = new Button("Diminuisci di 1");
        decrease.setOnAction(e->{
            client.getToolViewController().getPinzaSgrossatrice().setPinzaSgrossatrice2(false);
            window.close();
        });
        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(increase,decrease,close);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * shows popup box for a certain {@link Shared.Model.RoundTrace.RoundTraceCell}
     * @param round value of the round from trace to be shown
     * @param client client's controller
     * @author Marco Premi
     * */
    public static void showSingleRound(int round, Client client){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Round: "+ (round+1));

        RoundTrace roundTrace = client.getHandler().getTableController().getRoundTrace();
        VBox layout = new VBox(10);
        for(int i=0; i< roundTrace.getTrace().get(round).getCell().size(); i++){
            ImageView view = new ImageView(new Image("Images/Dice/" + roundTrace.getTrace().get(round).getCell().get(i).getColor() + roundTrace.getTrace().get(round).getCell().get(i).getTop() + ".png"));
            view.autosize();
            view.setFitHeight(VIEWWIDHT/(roundTrace.getTrace().size()*2));
            view.setFitWidth(VIEWWIDHT/(roundTrace.getTrace().size()*2));
            layout.getChildren().add(view);
        }

        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        layout.getChildren().add(close);
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * shows popup box for entire {@link RoundTrace}
     * @param client client's controller
     * @param toolUsed value of the tool used
     * @author Marco Premi
     * */
    public static void showAllRounds(String toolUsed, Client client){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Round Trace");

        RoundTrace roundTrace = client.getHandler().getTableController().getRoundTrace();

        HBox layout = new HBox();
        for(int i=0; i<roundTrace.getTrace().size();i++){
            VBox roundBox = new VBox();
            for(int j=0; j<roundTrace.getTrace().get(i).getCell().size();j++){
                ImageView imageView = new ImageView(new Image("Images/Dice/" + roundTrace.getTrace().get(i).getCell().get(j).getColor() + roundTrace.getTrace().get(i).getCell().get(j).getTop() + ".png"));
                imageView.setId("ROUND"+i+j);
                imageView.autosize();
                imageView.setFitHeight(VIEWWIDHT/(roundTrace.getTrace().size()*2));
                imageView.setFitWidth(VIEWWIDHT/(roundTrace.getTrace().size()*2));

                if ("taglierinaCircolare2".equals(toolUsed)) {
                    imageView.setOnMouseClicked(e -> {
                        client.getToolViewController().getTaglierinaCircolare().setTaglierinaCircolare2(imageView.getId());
                        window.close();
                    });

                } else if ("normalView".equals(toolUsed)) {
                    imageView.setOnMouseClicked(null);

                }

                roundBox.getChildren().add(imageView);
            }
            layout.getChildren().add(roundBox);
        }
        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * shows popup box for {@link Shared.Model.Dice.Dice} top setting
     * @param client client's controller
     * @author Marco Premi
     * */
    public static void setDiceValue(Client client){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Seleziona valore dado");


        VBox layout = new VBox(10);


        for(int i=1; i<=6; i++){
            final int j=i;
            Button newButton = new Button("Valore: "+i);
            newButton.setId(""+i);
            newButton.setOnAction(e->{
                client.getToolViewController().getDiluentePerPastaSalda().setDiluentePerPastaSalda2(j);
                window.close();
            });
            layout.getChildren().add(newButton);
        }

        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        layout.getChildren().add(close);

        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * shows popup box for other player's scheme and favors
     * @param playerBox player's info to be shown
     * @author Marco Premi
     * */
    public static void showPlayerScheme(VBox playerBox){
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);

        layout.getChildren().add(playerBox);

        Button close = new Button("Chiudi la finestra");
        close.setOnAction(e -> window.close());

        layout.getChildren().add(close);

        layout.setAlignment(Pos.CENTER);
        layout.autosize();

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * creates other players' facade
     * @return {@link VBox} for player's info
     * @author Marco Premi
     * */
    public static VBox createNewFacade(Scheme scheme, int favours){
        VBox newVbox = new VBox();
        GridPane gridPane = new GridPane();
        for(int i=0; i<scheme.getScheme().length; i++){
            for(int j=0; j<scheme.getScheme()[i].length; j++){
                ImageView newImageView = new ImageView();
                String url = null;
                if(scheme.getScheme()[i][j].isOccupied()){
                    url = "Images/Dice/"+scheme.getScheme()[i][j].getDado().getColor().toString() + scheme.getScheme()[i][j].getDado().getTop() + ".png";
                }else{
                    if (!scheme.getScheme()[i][j].getColor().equals(Color.WHITE)) {
                        url = "Images/Dice/" + scheme.getScheme()[i][j].getColor().toString() + "0.png";
                    } else {
                        url = "Images/Dice/W" + scheme.getScheme()[i][j].getNum() + ".png";
                    }
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
        javafx.scene.control.TextField textField = new TextField("Favori: "+favours);
        textField.autosize();
        textField.setOpacity(1);
        textField.setStyle("-fx-font-weight: bolder");
        textField.setAlignment(Pos.CENTER);
        textField.setDisable(true);
        textField.setMinWidth(GRIDWIDHT);
        textField.setMaxWidth(GRIDWIDHT);
        textField.setPrefWidth(GRIDWIDHT);

        newVbox.getChildren().addAll( gridPane, textField);
        return newVbox;
    }
}