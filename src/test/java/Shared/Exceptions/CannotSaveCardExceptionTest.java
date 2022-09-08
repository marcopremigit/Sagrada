package Shared.Exceptions;

import org.junit.Test;

public class CannotSaveCardExceptionTest {
    /**
     * test CannotSaveCardException
     * @author Abu Hussnain Saeed
     */
    @Test
    public void test(){
        CannotSaveCardException cannotSaveCardException = new CannotSaveCardException("exception");
    }
}
