package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.ConnectionController;
import Server.ServerSocket.SocketClient;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ConnectionCommandHandler {
    private ConnectionController controller;
    private SocketClient.SyncedOutput out;

    public ConnectionCommandHandler(SocketClient.SyncedOutput out){
        this.controller = MainServer.getConnectionController();
        this.out = out;
    }

    public void login(SocketClient client, String name){
        out.println("login&" + controller.login(name, client, false));
    }

    public void joinGame(String name, String singleplayer){
        try {
            out.println("joingame&" + controller.joinGame(name, Boolean.valueOf(singleplayer)));
        } catch (LobbyFullException l) {
            out.println("joingame&LobbyFullException");
        }
    }

    public void getRanking(){
        ArrayList<String> ranking = controller.getRanking();
        String toSend = "";
        if(ranking!=null){
            for(int i=0; i<ranking.size(); i++){
                toSend += ranking.get(i);
                if(i!=ranking.size()-1)
                    toSend += "/";
            }
        } else{
            toSend = "null";
        }
        out.println("ranking&" + toSend);
    }

    public void getPoints(String name){
        out.println("points&" + name + "&" + controller.getPoints(name));
    }

    public void getColor(String name){
        Color color = controller.getPlayerColor(name);
        if (color == null)
            out.println("null");
        else
            out.println("color&" + name + "&" + color.toString());
    }

    public void setDifficulty(String difficulty){
        controller.setDifficulty(Integer.parseInt(difficulty));
    }

    public void getObjectivePoints(){
        out.println("objectivepoints&" + controller.getObjectivePoints());
    }
}
