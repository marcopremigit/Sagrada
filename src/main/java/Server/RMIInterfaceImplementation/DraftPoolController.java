package Server.RMIInterfaceImplementation;

import Shared.RMIInterface.DraftPoolInterface;
import Shared.Model.Dice.Dice;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DraftPoolController extends UnicastRemoteObject implements DraftPoolInterface {

    public DraftPoolController() throws RemoteException {

    }

    @Override
    public ArrayList<Dice> getDraftPool() {
        return MainServer.getLobby().getGame().getDraftPool();
    }

    @Override
    public ArrayList<Dice> rollDiceInDraftpool(int index) {
        MainServer.getLobby().getGame().getDraftPool().get(index).roll();
        return MainServer.getLobby().getGame().getDraftPool();
    }
}
