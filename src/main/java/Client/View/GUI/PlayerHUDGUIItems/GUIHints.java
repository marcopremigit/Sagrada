package Client.View.GUI.PlayerHUDGUIItems;

import Client.View.GUI.GUIPlayerHUD;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.awt.*;

public class GUIHints {
    private StringProperty string;
    private TextField field;
    private static final double FIELDWIDHT = GUIPlayerHUD.getBoxWidht();
    private static final double FIELDHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5;

    public GUIHints(){
        field = new TextField();
        field.autosize();
        field.setDisable(true);
        string = new SimpleStringProperty("Benvenuto in Sagrada!");
    }


    /**
     * creates view for hints screen
     * @return {@link TextField} screen for hints to be shown
     * @author Fabrizio Siciliano
     * */
    public TextField createScreen(){
        field.textProperty().bind(string);
        field.setOpacity(2.0);
        field.setStyle("-fx-font-weight: bolder; -fx-text-alignment: center; -fx-column-halignment: center;");
        field.setMaxSize(FIELDWIDHT, FIELDHEIGHT);
        field.setPrefSize(FIELDWIDHT, FIELDHEIGHT);
        field.setMinSize(FIELDWIDHT, FIELDHEIGHT);

        return field;
    }

    /**
     * updates hints screen
     * @param message message to be shown
     * @author Fabrizio Siciliano
     * */
    public void updateScreen(String message){
        Platform.runLater(()-> string.setValue(message));
    }
}
