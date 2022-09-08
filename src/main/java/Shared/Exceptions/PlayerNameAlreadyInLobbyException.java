package Shared.Exceptions;

public class PlayerNameAlreadyInLobbyException extends Exception {
    public PlayerNameAlreadyInLobbyException(String message){
        super(message);
    }

    public PlayerNameAlreadyInLobbyException(){
        super();
    }
}
