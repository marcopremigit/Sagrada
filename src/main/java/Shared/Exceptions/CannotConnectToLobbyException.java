package Shared.Exceptions;

public class CannotConnectToLobbyException extends Exception {
    public CannotConnectToLobbyException(String message){
        super(message);
    }

    public CannotConnectToLobbyException(){
        super();
    }
}
