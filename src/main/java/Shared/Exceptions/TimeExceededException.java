package Shared.Exceptions;

public class TimeExceededException extends Exception {
    public TimeExceededException(String message){
        super(message);
    }

    public TimeExceededException(){
        super();
    }
}
