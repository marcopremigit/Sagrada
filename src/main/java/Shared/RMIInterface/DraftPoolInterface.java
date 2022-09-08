package Shared.RMIInterface;

import Shared.Model.Dice.Dice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DraftPoolInterface extends Remote {
    /**
     * @return list of {@link Dice}, updated draft pool
     * @author Fabrizio Siciliano
     * */
    ArrayList<Dice> getDraftPool() throws RemoteException;
    /**
     * @return list of {@link Dice} updated after use of tool card
     * @author Marco Premi
     * */
    ArrayList<Dice> rollDiceInDraftpool(int index)throws RemoteException;
}