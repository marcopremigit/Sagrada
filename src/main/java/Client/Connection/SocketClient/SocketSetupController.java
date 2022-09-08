package Client.Connection.SocketClient;

import Client.Connection.SetupControllerInterface;
import Client.View.ViewControllers.Controller;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocketSetupController implements SetupControllerInterface {
    private static final String LOGIN = "login";
    private static final String JOINGAME = "joingame";
    private static final String RANKING = "getRanking";
    private static final String POINTS = "getpoints";
    private static final String COLOR = "getcolor";
    private static final String DIFFICULTY = "difficulty";
    private static final String OBJECTIVEPOINTS = "getobjectivepoints";

    private ClientBuffer buffer;
    private String regex;
    private Controller controller;
    private PrintWriter print;

    public SocketSetupController(ClientBuffer buffer, String regex, Controller controller, PrintWriter print){
        this.buffer = buffer;
        this.regex = regex;
        this.controller = controller;
        this.print = print;
    }

    @Override
    public boolean login() {
        print.println(LOGIN + regex + controller.getPlayerName());
        print.flush();
        String result = null;
        while (result==null){
            result = buffer.checkInBuffer("login&");
        }
        if(result.endsWith("true"))
            return true;
        else
            return false;
    }

    @Override
    public void pingServer() {
        //no need to ping in socket
    }

    @Override
    public boolean joinGame(boolean singleplayer) throws LobbyFullException {
        print.println(JOINGAME + regex + controller.getPlayerName()+regex+singleplayer);
        print.flush();
        String result = null;
        while(result==null){
            result = buffer.checkInBuffer("joingame&");
        }
        if(result.endsWith("LobbyFullException")) {
            throw new LobbyFullException();
        }
        else if(result.endsWith("true")){
            return true;
        } else
            return false;
    }

    @Override
    public ArrayList<String> getRanking() {
        print.println(RANKING);
        print.flush();
        String returnValue = null;
        while(returnValue==null){
            returnValue=buffer.checkInBuffer("ranking&");
        }
        if(!returnValue.equals("null")) {
            ArrayList<String> ranking = new ArrayList<>();
            List<String> trimmed = Arrays.asList(Arrays.asList(returnValue.split("&")).get(1).split("/"));
            for (int i = 0; i < trimmed.size(); i++) {
                ranking.add(i, trimmed.get(i));
            }
            return ranking;
        } else
            return null;
    }

    @Override
    public int getPoints(String name) {
        print.println(POINTS + regex + name);
        print.flush();

        String result = null;
        while(result==null){
            result = buffer.checkInBuffer("points&"+name);
        }
        return Integer.parseInt(Arrays.asList(result.split("&")).get(2));
    }

    @Override
    public Color getPlayerColor() {
        print.println(COLOR + regex + controller.getPlayerName());
        print.flush();
        String returnValue = null;
        while(returnValue==null){
            returnValue = buffer.checkInBuffer("color&"+controller.getPlayerName());
        }
        if(returnValue.equals("null"))
            return null;
        else
            return Color.stringToColor(Arrays.asList(returnValue.split("&")).get(2));
    }
    @Override
    public void setDifficulty(int difficulty){
        print.println(DIFFICULTY + regex + difficulty);
        print.flush();
    }

    @Override
    public int getObjectivePoints(){
        print.println(OBJECTIVEPOINTS);
        print.flush();
        String returnVal = null;
        while(returnVal== null){
            returnVal = buffer.checkInBuffer("objectivepoints&");
        }

        return Integer.parseInt(Arrays.asList(returnVal.split("&")).get(1));
    }
}
