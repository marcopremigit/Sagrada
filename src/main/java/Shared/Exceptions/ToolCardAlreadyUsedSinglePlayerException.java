package Shared.Exceptions;

public class ToolCardAlreadyUsedSinglePlayerException extends Exception{
    public ToolCardAlreadyUsedSinglePlayerException(String cardName){
        super("Card " + cardName + " has already been used");
    }
}
