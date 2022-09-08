package Server.RMIInterfaceImplementation;

import Shared.RMIInterface.DiceBagInterface;
import Shared.Model.Dice.DiceBag;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DiceBagController  extends UnicastRemoteObject implements DiceBagInterface {
    public DiceBagController() throws RemoteException{

    }

    @Override
    public DiceBag getDiceBag() {
        return MainServer.getLobby().getGame().getDiceBag();
    }
}
