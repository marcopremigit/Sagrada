package Server.RMIInterfaceImplementation;

import Shared.RMIInterface.RoundTraceInterface;
import Shared.Model.RoundTrace.RoundTrace;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RoundTraceController extends UnicastRemoteObject implements RoundTraceInterface {
    public RoundTraceController() throws RemoteException {

    }


    @Override
    public RoundTrace getRoundTrace() {
        return MainServer.getLobby().getGame().getTrace();
    }

}
