package Shared.Exceptions;

import org.junit.Test;
public class TimeExceededExceptionTest {
    @Test
    public void test(){
        TimeExceededException timeExceededException = new TimeExceededException();
        TimeExceededException timeExceededException1 = new TimeExceededException("exception");
    }

}