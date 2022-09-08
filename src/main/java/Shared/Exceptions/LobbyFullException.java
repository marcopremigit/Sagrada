package Shared.Exceptions;

public class LobbyFullException extends Exception {
    public LobbyFullException(){
        super();
    }

    public LobbyFullException(String message){
        super(message);
    }
}
