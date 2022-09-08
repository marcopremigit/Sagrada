package Shared.Exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotEnoughFavoursTest {
    @Test
    public void test(){
        NotEnoughFavours notEnoughFavours = new NotEnoughFavours();
        NotEnoughFavours notEnoughFavours1 = new NotEnoughFavours("exception");
    }

}