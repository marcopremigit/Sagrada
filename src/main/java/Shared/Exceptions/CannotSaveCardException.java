package Shared.Exceptions;

public class CannotSaveCardException extends Exception {
    public CannotSaveCardException(){
        super();
    }

    public CannotSaveCardException(String message){
        super(message);
    }
}
