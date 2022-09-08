package Shared.Exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class CannotUseCardExceptionTest {
    @Test
    public void test(){
        CannotUseCardException cannotUseCardException = new CannotUseCardException();
        CannotUseCardException cannotUseCardException1 = new CannotUseCardException("exception");
    }

}