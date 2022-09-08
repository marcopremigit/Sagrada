package Server.RMIInterfaceImplementation;

import Client.Main;
import Server.Lobby;
import Server.MainServer;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Player;
import Shared.RMIInterface.PrivateObjectiveInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrivateObjectiveController extends UnicastRemoteObject implements PrivateObjectiveInterface {

    public PrivateObjectiveController() throws RemoteException {

    }


    @Override
    public PrivateObjective getPrivateObjective1(String name) {
        PrivateObjective privateObjective = null;
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name)){
                privateObjective = player.getPrivateObjective1();
                break;
            }
        }
        return privateObjective;
    }
    @Override
    public PrivateObjective getPrivateObjective2(String name){
        PrivateObjective privateObjective = null;
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name)){
                privateObjective = player.getPrivateObjective2();
                break;
            }

        }
        return privateObjective;
    }

    @Override
    public void setPrivateObjective(String name) {
        MainServer.getLobby().getPlayers().get(0).setNameChosenPrivateObjective(name);
    }
}
