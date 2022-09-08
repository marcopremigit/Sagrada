package Shared.Exceptions;

public class CannotUseCardException extends Exception {
    public CannotUseCardException(String message){
        super(message);
    }

    public CannotUseCardException(){
        super();
    }
}
