package Shared.RMIInterface;

import Shared.Model.RoundTrace.RoundTrace;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoundTraceInterface extends Remote {
    /**
     * @return {@link RoundTrace} trace of the game
     * @author Fabrizio Siciliano, Marco Premi
     * */
    RoundTrace getRoundTrace() throws RemoteException;
}
