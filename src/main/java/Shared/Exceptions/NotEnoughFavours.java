package Shared.Exceptions;

public class NotEnoughFavours extends Exception {
    public NotEnoughFavours(String message){
        super(message);
    }

    public NotEnoughFavours(){
        super();
    }
}
