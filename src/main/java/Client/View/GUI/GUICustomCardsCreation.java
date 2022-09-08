package Client.View.GUI;


import Client.Client;
import Client.Main;
import Shared.Exceptions.CannotSaveCardException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class GUICustomCardsCreation{
    private Client client;

    GUICustomCardsCreation(Client client){
        this.client = client;
    }

    private int scheme1Column;
    private int scheme1Row;
    private int scheme2Column;
    private int scheme2Row;

    /**
     * creates custom cards creation VIEW
     * @author Fabrizio Siciliano
     * */
    void showCustomCardCreationMenu(){
        Main.setBackgroundImage(Main.getPlayingBackground());
        AnchorPane layer = new AnchorPane();
        layer.autosize();

        TextField column1field = createField("Numero di colonne dello schema 1");
        column1field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                if(!newValue.equals(""))
                    scheme1Column = Integer.parseInt(newValue);
            } else {
                if(oldValue!=null)
                    column1field.setText(oldValue);
                else
                    column1field.setText("");
            }
        });

        TextField row1field = createField("Numero di righe dello schema 1");
        row1field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                if(!newValue.equals(""))
                    scheme1Row = Integer.parseInt(newValue);
            } else {
                if(oldValue!=null)
                    row1field.setText(oldValue);
                else
                    row1field.setText("");
            }
        });

        TextField column2field = createField("Numero di colonne dello schema 2");
        column2field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                if(!newValue.equals(""))
                    scheme2Column = Integer.parseInt(newValue);
            } else {
                if(oldValue!=null)
                    column2field.setText(oldValue);
                else
                    column2field.setText("");
            }
        });

        TextField row2field = createField("Numero di righe dello schema 2");
        row2field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                if(!newValue.equals(""))
                    scheme2Row = Integer.parseInt(newValue);
            } else {
                if(oldValue!=null)
                    row2field.setText(oldValue);
                else
                    row2field.setText("");
            }
        });

        VBox vbox = new VBox(10);
        vbox.autosize();
        vbox.setAlignment(Pos.CENTER);

        Button b = new Button("CREA");
        b.autosize();
        b.setOnAction(e->{
            if(scheme1Row <= 0 || scheme1Column <= 0 || scheme2Column <= 0 || scheme2Row <= 0){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("VALORI NON VALIDI");
                a.setHeaderText("VALORI DI RIGA O COLONNA NON VALIDI");
                a.setContentText("Hai inserito dei valori non validi per gli schemi.\nChiudi questa finestra e rimettine di nuovi");
            }
            else{
                AnchorPane layer2 = new AnchorPane();
                VBox box = createCardsCreationBox();
                layer2.getChildren().add(box);
                AnchorPane.setLeftAnchor(box, 0.0);
                AnchorPane.setRightAnchor(box, 0.0);
                AnchorPane.setBottomAnchor(box, 0.0);
                AnchorPane.setTopAnchor(box, 0.0);
                Main.getRootLayout().setCenter(layer2);

            }
        });

        vbox.getChildren().addAll(column1field, row1field, column2field,row2field, b);
        layer.getChildren().add(vbox);

        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);

        Main.getRootLayout().setCenter(layer);

    }

    /**
     * creates {@link TextField} for columns and rows input
     * @param text prompt text to be inserted in textfield
     * @author Fabrizio Siciliano
     * */
    private TextField createField(String text){
        TextField field = new TextField();
        field.autosize();
        field.setAlignment(Pos.CENTER);
        field.setPromptText(text);
        field.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        field.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        field.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        return field;
    }

    /*---------------------------------------creation card stuff--------------------------------------------------*/
    private static final double IVSIZE = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 24;
    private static final double MAXHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3;
    private static final double MAXWIDHT = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4;
    private TextField name1;
    private TextField name2;
    private TextField favors1field;
    private TextField favors2field;
    private int favors1;
    private int favors2;
    private GridPane scheme1;
    private GridPane scheme2;

    /**
     * creates box for custom cards creation
     * @return new {@link VBox} for the creation of each scheme
     * @author Fabrizio Siciliano
     * */
    private VBox createCardsCreationBox(){
        HBox cardsBox = new HBox(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/60);
        cardsBox.autosize();

        cardsBox.getChildren().addAll(createCardsVbox(scheme1Column, scheme1Row), createCardsVbox(scheme2Column, scheme2Row));
        cardsBox.setAlignment(Pos.CENTER);

        Button b = new Button("SALVA");
        b.autosize();
        b.setOnAction(event->{
            try {
                if (name1.getText().equals("") || name2.getText().equals("")) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setHeaderText("NAME NOT INSERTED");
                    a.show();
                    if (name1.getText().equals("")) {
                        a.setContentText("Non hai inserito il nome dello schema a sinistra");
                        name1.setText("");
                    } else {
                        a.setContentText("Non hai inserto il nome dello schema a destra");
                        name2.setText("");
                    }
                } else {

                    String[] names = {name1.getText(), name2.getText()};
                    String[] favors = {String.valueOf(favors1), String.valueOf(favors2)};
                    String[] stringScheme1 = new String[scheme1.getChildren().size()];
                    for (int i = 0; i < scheme1Row; i++) {
                        for (int j = 0; j < scheme1Column; j++) {
                            stringScheme1[i + j * scheme1Row] = scheme1.getChildren().get(i + j * scheme1Row).getId().split("/")[2].substring(0, 2);
                        }
                    }
                    String[] stringScheme2 = new String[scheme2.getChildren().size()];
                    for (int i = 0; i < scheme2Row; i++) {
                        for (int j = 0; j < scheme2Column; j++) {
                            stringScheme2[i + j * scheme2Row] = scheme2.getChildren().get(i + j * scheme2Row).getId().split("/")[2].substring(0, 2);
                        }
                    }

                    String[] col = {String.valueOf(scheme1Column), String.valueOf(scheme2Column)};
                    String[] rows = {String.valueOf(scheme1Row), String.valueOf(scheme2Row)};
                    try {
                        client.getHandler().getCardsController().saveCustomCard(names, favors, stringScheme1, stringScheme2, col, rows);
                        GUIMainMenu menu = new GUIMainMenu(client);
                        menu.showGUI();
                    } catch (CannotSaveCardException c){
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("INFORMATION");
                        a.setHeaderText("SCHEME ALREADY SAVED");
                        a.setContentText("Uno dei nomi degli schemi è già presente sul server\nCarta non salvata");
                        a.show();
                        GUISettingsMenu pane = new GUISettingsMenu(client);
                        pane.showSettingsMenu();
                    }
                }
            } catch (NullPointerException n){
                //NullPointerException catched
            }
        });

        VBox box = new VBox();
        box.autosize();
        box.getChildren().addAll(cardsBox, createPossibleOptionBox(), b);
        box.setAlignment(Pos.CENTER);

        return box;
    }

    /**
     * creates box for each scheme creation
     * @return a {@link VBox} for each scheme
     * @author Fabrizio Siciliano
     * */
    private VBox createCardsVbox(int columns, int rows){
        VBox box = new VBox(50);
        box.autosize();
        box.setAlignment(Pos.CENTER);
        if(columns == scheme1Column) {
            name1 = createTextField();
            favors1field = createField("Numero favori dello schema");
            favors1field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*")) {
                    if(!newValue.equals(""))
                        favors1 = Integer.parseInt(newValue);
                } else {
                    if(oldValue!=null)
                        favors1field.setText(oldValue);
                    else
                        favors1field.setText("");
                }
            });

            scheme1 = createNewGrid(columns, rows);
            box.getChildren().addAll(scheme1, name1 , favors1field);
        }else{
            name2 = createTextField();
            favors2field = createField("Numero favori dello schema");
            favors2field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*")) {
                    if(!newValue.equals(""))
                        favors2 = Integer.parseInt(newValue);
                } else {
                    if(oldValue!=null)
                        favors2field.setText(oldValue);
                    else
                        favors2field.setText("");
                }
            });

            scheme2 = createNewGrid(columns, rows);
            box.getChildren().addAll(scheme2, name2, favors2field);
        }
        return box;
    }

    /**
     * creates {@link TextField} field for scheme naming
     * @return {@link TextField} created
     * @author Fabrizio Siciliano
     * */
    private TextField createTextField(){
        TextField field = new TextField();
        field.autosize();
        field.setAlignment(Pos.CENTER);
        field.setPromptText("Inserisci il nome dello schema");
        field.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        field.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        field.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4);
        return field;
    }

    /**
     * creates {@link HBox} for possible cells to be inserted
     * @return {@link HBox} created
     * @author Fabrizio Siciliano
     * */
    private HBox createPossibleOptionBox(){
        HBox box = new HBox(IVSIZE);
        box.setAlignment(Pos.CENTER);
        box.autosize();

        for(int i=1; i<=6; i++){
            String url = "Images/Dice/W" + i + ".png";
            box.getChildren().add(createOptionImageView(url));
        }
        box.getChildren().add(createOptionImageView("Images/Dice/B0.png"));
        box.getChildren().add(createOptionImageView("Images/Dice/G0.png"));
        box.getChildren().add(createOptionImageView("Images/Dice/P0.png"));
        box.getChildren().add(createOptionImageView("Images/Dice/R0.png"));
        box.getChildren().add(createOptionImageView("Images/Dice/Y0.png"));
        return box;
    }

    /**
     * creates {@link ImageView} with proper function for drag and drop
     * @return {@link ImageView} created
     * @author Fabrizio Siciliano
     * */
    private ImageView createOptionImageView(String url){
        ImageView view = new ImageView(new Image(url));
        view.setId(url);
        view.autosize();
        view.setFitWidth(IVSIZE);
        view.setFitHeight(IVSIZE);
        view.setOnDragDetected(event -> {
            /* drag was detected, start a drag-and-drop gesture*/
            /* allow any transfer mode */
            Dragboard db = view.startDragAndDrop(TransferMode.ANY);

            /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(view.getId());
            db.setContent(content);

            event.consume();
        });

        return view;
    }

    /**
     * creates {@link GridPane} with proper functions for drag and drop
     * @return {@link GridPane} created
     * @author Fabrizio Siciliano
     * */
    private GridPane createNewGrid(int columns, int rows){
        GridPane grid = new GridPane();
        grid.autosize();
        grid.setAlignment(Pos.CENTER);
        //------------------------calc sizes----------------------------
        double cellSize;
        if(columns>rows){
            cellSize = MAXWIDHT/columns;
        }else{
            cellSize = MAXHEIGHT/rows;
        }
        //------------------------calc sizes----------------------------

        for(int i=0; i<columns; i++){
            for(int j=0; j<rows; j++){
                ImageView imageView = new ImageView(new Image("Images/Dice/W0.png"));
                imageView.setId("Images/Dice/W0.png");
                imageView.autosize();
                imageView.setFitHeight(cellSize);
                imageView.setFitWidth(cellSize);

                imageView.setOnDragOver(event -> {
                    /*
                     * data is dragged over the target
                     * accept it only if it is not dragged from the same node
                     * and if it has an url
                     * */
                    if (event.getGestureSource() != imageView && event.getDragboard().hasString()) {
                        /*
                         * allow for both copying and moving, whatever user chooses
                         * */
                        event.acceptTransferModes(TransferMode.ANY);
                    }

                    event.consume();
                });

                imageView.setOnDragEntered(event -> {
                    /*
                     *  the drag-and-drop gesture entered the target
                     * show to the user that it is an actual gesture target
                     * */
                    if (event.getGestureSource() != imageView && event.getDragboard().hasString()) {
                        imageView.setImage(new Image(event.getDragboard().getString()));
                    }

                    event.consume();
                });

                imageView.setOnDragExited(event -> {
                    /*
                     * mouse moved away, remove the graphical cues
                     * */
                    imageView.setImage(new Image(imageView.getId()));

                    event.consume();
                });

                imageView.setOnDragDropped(event -> {
                    /*
                     * data dropped
                     * if there is a string data on dragboard, read it and use it
                     * */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        Platform.runLater(()-> {
                            imageView.setImage(new Image(db.getString()));
                            imageView.setId(db.getString());
                        });
                        success = true;
                    }
                    /*
                     * let the source know whether the string was successfully
                     * transferred and used
                     * */
                    event.setDropCompleted(success);

                    event.consume();
                });

                grid.add(imageView, i, j);
            }
        }
        return grid;
    }
}