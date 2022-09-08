package Client.View.GUI.PlayerHUDGUIItems;

import Client.Client;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIPlayerHUD;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;

public class GUIRoundTrace {
    private static HBox roundTrace;
    private static final double VIEWWIDHT = GUIPlayerHUD.getBoxWidht();

    public GUIRoundTrace(Client client){
        roundTrace = new HBox(1);
        roundTrace.setAlignment(Pos.CENTER);
        roundTrace.autosize();

        for(int i=1; i<=10; i++){
            int j=i;
            ImageView newView = new ImageView(new Image("Images/RoundTrace/RT" + i + ".png"));
            newView.setFitWidth(VIEWWIDHT/10);
            newView.setFitHeight(VIEWWIDHT/10);
            newView.setOnMouseClicked(e->GUIAlertBox.showSingleRound(j-1, client));
            roundTrace.getChildren().add(newView);
        }
    }

    /**
     * updates view of the drafpool
     * @param trace {@link RoundTrace} to be updated
     * @author Fabrizio Siciliano
     * */
    public void updateRoundTrace(RoundTrace trace){
        Platform.runLater(()->{
            String url;
            for (int i = 0; i < 10; i++) {
                List<Dice> cell = trace.getTrace().get(i).getCell();
                if (cell != null) {
                    for (Dice dice : cell) {
                        url = "Images/Dice/" + dice.getColor().toString() + dice.getTop() + ".png";
                        ImageView view = (ImageView) roundTrace.getChildren().get(i);
                        view.setImage(new Image(url));
                    }
                }
            }
        });
    }

    public HBox getRoundTrace(){
        return roundTrace;
    }
}
