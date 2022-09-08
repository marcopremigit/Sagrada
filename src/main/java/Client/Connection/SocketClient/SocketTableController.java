package Client.Connection.SocketClient;

import Client.Connection.TableControllerInterface;
import Client.Connection.ToolClientControllerInterface;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.ViewControllers.Controller;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class SocketTableController implements TableControllerInterface {
    private static final String DRAFTPOOLCOMMAND = "getdraftpool";
    private static final String ROUNDTRACECOMMAND = "getroundtrace";
    private static final String SCHEMECOMMAND = "getplayerscheme";
    private static final String PLAYERSNAMESCOMMNAD = "getplayersnames";

    private ClientBuffer buffer;
    private String regex;
    private ToolClientControllerInterface toolController;
    private PrintWriter print;
    private Controller clientController;

    public SocketTableController(Controller clientController, ToolClientControllerInterface toolController, ClientBuffer buffer, String regex, PrintWriter print){
        this.clientController = clientController;
        this.buffer = buffer;
        this.toolController = toolController;
        this.regex = regex;
        this.print = print;
    }

    @Override
    public void showOtherPlayerSchemes() {
        for(String player : getOtherPlayersNames()){
            CLIScheme.showScheme(getScheme(player));
            CLIScheme.showFavorsAndName(player, toolController.getFavours(player));
        }
    }

    @Override
    public ArrayList<Dice> getDraftPool() {
        print.println(DRAFTPOOLCOMMAND);
        print.flush();
        String obj = null;
        while (obj==null){
            obj=buffer.checkInBuffer("{\"draftpool\"");
        }
        JSONObject object = new JSONObject(obj);
        JSONArray array = object.getJSONArray("draftpool");
        ArrayList<Dice> draftPool = new ArrayList<>();
        for(int i=0; i<array.length(); i++){
            draftPool.add(i, Dice.fromJSONToDice(array.getJSONObject(i)));
        }
        return draftPool;
    }

    @Override
    public RoundTrace getRoundTrace() {
        print.println(ROUNDTRACECOMMAND);
        print.flush();
        String obj = null;
        while (obj==null){
            obj=buffer.checkInBuffer("{\"roundtrace\"");
        }
        return RoundTrace.fromJSONToTrace(new JSONObject(obj));
    }

    @Override
    public Scheme getScheme(String playerName) {
        print.println(SCHEMECOMMAND + regex + playerName);
        print.flush();
        String obj = null;
        while(obj==null){
            obj=buffer.checkInBuffer("scheme&"+playerName);
        }
        return Scheme.fromJSONToScheme(new JSONObject((Arrays.asList(obj.split("&"))).get(2)));
    }

    @Override
    public ArrayList<String> getOtherPlayersNames() {
        print.println(PLAYERSNAMESCOMMNAD);
        print.flush();
        String in = null;
        while(in==null){
            in = buffer.checkInBuffer("{\"names\"");
        }
        JSONObject obj = new JSONObject(in);
        JSONArray array = obj.getJSONArray("names");
        ArrayList<String> names = new ArrayList<>();
        for(Object name : array){
            names.add((String) name);
        }
        return names;
    }

    @Override
    public void playerNotAvailable(String name) {
        if(clientController.isUsingCLI())
            System.out.println("Player " + name + " has disconnected");
        else
            clientController.getPlayerHUD().getPlayersSchemes().playerNotAvailable(name);
    }

    @Override
    public void playerAgainAvailable(String name) {
        if(clientController.isUsingCLI())
            System.out.println("Player " + name + " has reconnected");
        else
            clientController.getPlayerHUD().getPlayersSchemes().playerAvailable(name);
    }
}
