package Client;

import Client.View.GUI.GUIConnection;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;

    private static final String SETUPBACKGROUND = "Images/Tools/toolCardBack.jpg";
    private static final String MAINBACKGROUND = "Images/Sagrada.jpg";
    private static final String PLAYINGBACKGROUND = "Images/background.jpg";

    /**
     * main application
     * @author Fabrizio Siciliano
     * */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * @author Fabrizio Siciliano
     * */
    @Override
    public void start(Stage primaryStage){
        startGUI(primaryStage);
    }

    /**
     * starts GUI
     * @param primary main stage
     * @author Fabrizio Siciliano
     * */
    public void startGUI(Stage primary){
        primaryStage = primary;
        primaryStage.setTitle("Sagrada");
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(10);
        });
        initRootLayout();
        GUIConnection zeroPane = new GUIConnection(primaryStage);
        zeroPane.showGUI();
    }

    /**
     * init root layout and primary stage
     * @author Fabrizio Siciliano
     * */
    private void initRootLayout(){
        rootLayout = new BorderPane();
        Image sagradaImage = new Image(SETUPBACKGROUND);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background sagradaBackground = new Background(new BackgroundImage(sagradaImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));

        rootLayout.setBackground(sagradaBackground);
        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.getIcons().add(new Image(MAINBACKGROUND));
        primaryStage.setWidth(400);
        primaryStage.setHeight(590);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), rootLayout);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        primaryStage.show();
    }

    /**
     * @return root layout of GUI
     * @author Fabrizio Siciliano
     * */
    public static BorderPane getRootLayout(){
        return rootLayout;
    }

    /**
     * @return global value for main background
     * @author Fabrizio Siciliano
     * */
    public static String getMainBackground() {
        return MAINBACKGROUND;
    }

    /**
     * @return global value for playing background
     * @author Fabrizio Siciliano
     * */
    public static String getPlayingBackground(){
        return PLAYINGBACKGROUND;
    }

    /**
     * @return global value for setup background
     * @author Fabrizio Siciliano
     * */
    public static String getSetupBackeground() {
        return SETUPBACKGROUND;
    }

    /**
     * @param image URL for image to be set as background of rootLayout
     * @author Fabrizio Siciliano
     * */
    public static void setBackgroundImage(String image){
        Image sagradaImage = new Image(image);
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        Background sagradaBackground = new Background(new BackgroundImage(sagradaImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
        rootLayout.setBackground(sagradaBackground);
    }
}
