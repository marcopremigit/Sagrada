package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.DraftPoolController;
import Server.RMIInterfaceImplementation.RoundTraceController;
import Server.RMIInterfaceImplementation.SchemeController;
import Server.ServerSocket.SocketClient;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;
import Shared.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TableCommandHandler {
    private DraftPoolController draftPoolController;
    private SchemeController schemeController;
    private RoundTraceController roundTraceController;
    private SocketClient.SyncedOutput out;

    public TableCommandHandler(SocketClient.SyncedOutput out){
        this.draftPoolController = MainServer.getDraftPoolController();
        this.schemeController = MainServer.getSchemeController();
        this.roundTraceController = MainServer.getRoundTraceController();
        this.out = out;
    }

    public void getDraftPool(){
        ArrayList<Dice> draftPool = draftPoolController.getDraftPool();
        JSONArray array = new JSONArray();
        for(int i=0; i<draftPool.size(); i++){
            array.put(Dice.fromDiceToJSON(draftPool.get(i)));
        }
        JSONObject object = new JSONObject();
        object.put("draftpool", array);
        out.println(object.toString());
    }

    public void getRoundTrace(){
        out.println(RoundTrace.fromTraceToJSON(roundTraceController.getRoundTrace()).toString());
    }

    public void getScheme(String playerName){
        Scheme scheme = schemeController.getScheme(playerName);
        out.println("scheme&" + playerName + "&" + Scheme.fromSchemeToJSON(scheme).toString());
    }

    public void getPlayersNames(String name){
        JSONArray playerNames = new JSONArray();

        ArrayList<String> players = MainServer.getConnectionController().getOtherPlayerName(name);
                //MainServer.getLobby().getPlayers();
        for(String p : players){
            playerNames.put(p);
        }
        JSONObject obj = new JSONObject();
        obj.put("names", playerNames);
        out.println(obj.toString());
    }
}
