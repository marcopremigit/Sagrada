package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.ConnectionController;
import Server.RMIInterfaceImplementation.TurnController;
import Server.ServerSocket.SocketClient;
import joptsimple.internal.Strings;

import java.io.PrintWriter;

public class TurnCommandHandler {
    private TurnController turnController;
    private SocketClient.SyncedOutput out;

    public TurnCommandHandler(SocketClient.SyncedOutput out){
        this.turnController = MainServer.getTurnController();
        this.out = out;
    }

    public void isFirstTurn(){
        out.println("clockwise&" +turnController.isClockwise());
    }

    public void endTurn(String name){
        turnController.endMyTurn(name);
    }

    public void whatRoundIs(){
        out.println("round&" + turnController.round());
    }
}
