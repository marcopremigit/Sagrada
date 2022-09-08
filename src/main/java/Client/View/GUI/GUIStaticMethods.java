package Client.View.GUI;

import Client.Client;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GUIStaticMethods {
    private GUIStaticMethods(){ }

    /**
     * sets different action to the player's grid
     * @param client client's controller
     * @param toolUsed name of the action to set
     * @author Marco Premi
     * */
    //It changes button SetOnAction for different tools
    public static void changeButtonGrid(String toolUsed, Client client){
        for(Node child : client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().getGridPane().getChildren()){
            switch (toolUsed){
                case "diluentePerPastaSalda2" : (child).setOnMouseClicked(e-> client.getToolViewController().getDiluentePerPastaSalda().setDiluentePerPastaSalda3(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "normalMove2" : (child).setOnMouseClicked(e-> client.getToolViewController().getNormalMove().setNormalMove2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "pennelloPerEglomise1" : (child).setOnMouseClicked(e-> client.getToolViewController().getPennelloPerEglomise().setPennelloPerEglomise1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "pennelloPerEglomise2" :  (child).setOnMouseClicked(e-> client.getToolViewController().getPennelloPerEglomise().setPennelloPerEglomise2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "alesatorePerLaminaDiRame1" :  (child).setOnMouseClicked(e-> client.getToolViewController().getAlesatorePerLaminaDiRame().setAlesatorePerLaminaDiRame1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "alesatorePerLaminaDiRame2" : (child).setOnMouseClicked(e-> client.getToolViewController().getAlesatorePerLaminaDiRame().setAlesatorePerLaminaDiRame2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin1" : (child).setOnMouseClicked(e-> client.getToolViewController().getLathekin().setLathekin1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin2" : (child).setOnMouseClicked(e-> client.getToolViewController().getLathekin().setLathekin2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin3" : (child).setOnMouseClicked(e-> client.getToolViewController().getLathekin().setLathekin3(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "lathekin4" : (child).setOnMouseClicked(e-> client.getToolViewController().getLathekin().setLathekin4(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "rigaInSughero2" :  (child).setOnMouseClicked(e-> client.getToolViewController().getRigaInSughero().setRigaInSughero2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale1" :  (child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaManuale().setTaglierinaManuale1(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale2" : (child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaManuale().setTaglierinaManuale2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale3" :  (child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaManuale().setTaglierinaManuale3(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "taglierinaManuale4" :(child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaManuale().setTaglierinaManuale4(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "tenagliaARotelle2" :(child).setOnMouseClicked(e-> client.getToolViewController().getTenagliaARotelle().setTenagliaARotelle2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
                case "pennelloPerPastaSalda2" :(child).setOnMouseClicked(e-> client.getToolViewController().getPennelloPerPastaSalda().setPennelloPerPastaSalda2(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)));
                    break;
            }
        }
    }

    /**
     * sets different action to the player's draftPool
     * @param client client's controller
     * @param toolUsed name of the action to set
     * @author Marco Premi
     * */
    public static void changeButtonDraftPool(String toolUsed, Client client){
        for(Node child : client.getHandler().getMainController().getPlayerHUD().getDraftPool().getDraftPoolBox().getChildren()){
            switch (toolUsed){
                case "dfAlesatore":(child).setOnMouseClicked(e-> client.getToolViewController().getAlesatorePerLaminaDiRame().setDraftPoolPositionAlesatore(((child).getId())));
                    break;
                case "dfDiluente":(child).setOnMouseClicked(e-> client.getToolViewController().getDiluentePerPastaSalda().setDfDiluente(((child).getId())));
                    break;
                case "dfLathekin":(child).setOnMouseClicked(e-> client.getToolViewController().getLathekin().setDfLathekin(((child).getId())));
                    break;
                case "dfMartelletto":(child).setOnMouseClicked(e-> client.getToolViewController().getMartelletto().setDfMartelletto(((child).getId())));
                    break;
                case "dfPennelloPerEglomise":(child).setOnMouseClicked(e-> client.getToolViewController().getPennelloPerEglomise().setDfpennellopereglomise(((child).getId())));
                    break;
                case "dfPennelloPerPastaSalda":(child).setOnMouseClicked(e-> client.getToolViewController().getPennelloPerPastaSalda().setDfpennelloperpastasalda(((child).getId())));
                    break;
                case "dfPinza":(child).setOnMouseClicked(e-> client.getToolViewController().getPinzaSgrossatrice().setDfpinza(((child).getId())));
                    break;
                case "dfRiga":(child).setOnMouseClicked(e-> client.getToolViewController().getRigaInSughero().setdfriga(((child).getId())));
                    break;
                case "dfTaglierinaCircolare":(child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaCircolare().setDftaglierinacircolare(((child).getId())));
                    break;
                case "dfTaglierinaManuale":(child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaManuale().setDftaglerinamanuale(((child).getId())));
                    break;
                case "dfTampone":(child).setOnMouseClicked(e-> client.getToolViewController().getTamponeDiamantato().setDfTampone(((child).getId())));
                    break;
                case "dfTenaglia":(child).setOnMouseClicked(e-> client.getToolViewController().getTenagliaARotelle().setDfTenaglia(((child).getId())));
                    break;
                case "normalMove1": (child).setOnMouseClicked(e-> client.getToolViewController().getNormalMove().setNormalMove1(((child).getId())));
                    break;
                case "rigaInSughero1": (child).setOnMouseClicked(e-> client.getToolViewController().getRigaInSughero().setRigaInSughero1(((child).getId())));
                    break;
                case "taglierinaCircolare1": (child).setOnMouseClicked(e-> client.getToolViewController().getTaglierinaCircolare().setTaglierinaCircolare1(((child).getId())));
                    break;
                case "diluentePerPastaSalda": (child).setOnMouseClicked(e-> client.getToolViewController().getDiluentePerPastaSalda().setDiluentePerPastaSalda(((child).getId())));
                    break;
                case "tamponeDiamantato": (child).setOnMouseClicked(e-> client.getToolViewController().getTamponeDiamantato().setTamponeDiamantato(((child).getId())));
                    break;
                case "tenagliaARotelle1": (child).setOnMouseClicked(e-> client.getToolViewController().getTenagliaARotelle().setTenagliaARotelle1(((child).getId())));
                    break;
                case "pinzaSgrossatrice1": (child).setOnMouseClicked(e-> client.getToolViewController().getPinzaSgrossatrice().setPinzaSgrossatrice1(((child).getId())));
                    break;
                case "pennelloPerPastaSalda1": (child).setOnMouseClicked(e-> client.getToolViewController().getPennelloPerPastaSalda().setPennelloPerPastaSalda1(((child).getId())));
                    break;
            }
        }
    }

    /**
     * sets default action to draftPool
     * @param client client's controller
     * @author Marco Premi
     * */
    public static void setButtonDraftPoolNull(Client client){
        for(Node child : client.getHandler().getMainController().getPlayerHUD().getDraftPool().getDraftPoolBox().getChildren()){
            (child).setOnMouseClicked(null);
        }
    }

    /**
     * sets default action to player's grid
     * @param client client's controller
     * @author Marco Premi
     * */
    public static void setButtonFacadeNull(Client client){
        for(Node child : client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().getGridPane().getChildren()){
            (child).setOnMouseClicked(null);
        }
    }

    /**
     * sets default action to roundTrace
     * @param client client's controller
     * @author Marco Premi
     * */
    static void setRoundTraceButtonNull(Client client){
        ObservableList<Node> children = client.getHandler().getMainController().getPlayerHUD().getRoundTrace().getRoundTrace().getChildren();
        for (int i=0; i<children.size(); i++){
            final int j=i;
            children.get(i).setOnMouseClicked(e->GUIAlertBox.showSingleRound(j, client));
        }
    }
}
