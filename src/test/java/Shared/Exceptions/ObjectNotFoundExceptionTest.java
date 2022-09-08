package Shared.Exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectNotFoundExceptionTest {
    @Test
    public void test(){
        ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException();
        ObjectNotFoundException objectNotFoundException1 = new ObjectNotFoundException("exception");
    }

}