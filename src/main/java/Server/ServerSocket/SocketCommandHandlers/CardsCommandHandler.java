package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.DraftPoolController;
import Server.RMIInterfaceImplementation.PrivateObjectiveController;
import Server.RMIInterfaceImplementation.PublicObjectiveController;
import Server.RMIInterfaceImplementation.SchemeController;
import Server.ServerSocket.SocketClient;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.SchemeCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardsCommandHandler {
    private DraftPoolController draftPoolController;
    private PrivateObjectiveController privateObjectiveController;
    private PublicObjectiveController publicObjectiveController;
    private SchemeController schemeController;
    private SocketClient.SyncedOutput out;

    public CardsCommandHandler(SocketClient.SyncedOutput out){
        this.draftPoolController = MainServer.getDraftPoolController();
        this.publicObjectiveController = MainServer.getPublicObjectiveController();
        this.privateObjectiveController = MainServer.getPrivateObjectiveController();
        this.schemeController = MainServer.getSchemeController();
        this.out = out;
    }

    public void getPrivateObjective1(String name){
        PrivateObjective p = privateObjectiveController.getPrivateObjective1(name);
        out.println((new JSONObject()).put("privatecard", PrivateObjective.fromPrivateToJSON(p)).toString());
    }

    public void getPrivateObjective2(String name){
        PrivateObjective p = privateObjectiveController.getPrivateObjective2(name);
        if(p!=null)
            out.println((new JSONObject()).put("privatecard", PrivateObjective.fromPrivateToJSON(p)).toString());
        else
            out.println("{\"privatecard\"&null");
    }

    public void getSchemeCard(){
        SchemeCard card = schemeController.getSchemeCard();
        out.println(((new JSONObject()).put("schemecard",SchemeCard.fromCardToJSON(card))).toString());
    }

    public void getPublicObjectives(){
        ArrayList<PublicObjective> publics = publicObjectiveController.getPublicObjective();
        JSONArray array = new JSONArray();
        for(PublicObjective p : publics){
            array.put(PublicObjective.fromPublicToJSON(p));
        }
        JSONObject obj = new JSONObject();
        obj.put("publics", array);
        out.println(obj.toString());
    }

    public void setScheme(String playerName, String schemeName){
        schemeController.setScheme(playerName, schemeName);
    }

    public void saveScheme(String toSplit){
        JSONObject obj = new JSONObject(toSplit);
        String[] names = {obj.getString("schemename1"), obj.getString("schemename2")};
        String[] favors = {obj.getString("schemefavors1"), obj.getString("schemefavors2")};
        String[] scheme1 = new String[obj.getJSONArray("scheme1").length()];
        for(int i=0; i<obj.getJSONArray("scheme1").length(); i++){
            scheme1[i] = obj.getJSONArray("scheme1").getString(i);
        }
        String[] scheme2 = new String[obj.getJSONArray("scheme2").length()];
        for(int i=0; i<obj.getJSONArray("scheme2").length(); i++){
            scheme2[i] = obj.getJSONArray("scheme2").getString(i);
        }
        String[] col = {obj.getString("frontcol"), obj.getString("rearcol")};
        String[] rows = {obj.getString("frontrow"), obj.getString("rearrow")};
        try {
            schemeController.saveCardOnServer(names, favors, scheme1, scheme2, col, rows);
            out.println("savecard&ok");
        } catch (CannotSaveCardException i){
            out.println("savecard&CannotSaveCardException");
        }
    }

    public void setPrivateObjective(String name){
        privateObjectiveController.setPrivateObjective(name);
    }
}
