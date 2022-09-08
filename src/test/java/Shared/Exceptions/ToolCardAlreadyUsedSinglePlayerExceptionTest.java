package Shared.Exceptions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToolCardAlreadyUsedSinglePlayerExceptionTest {

    @Test
    public void  test(){
        ToolCardAlreadyUsedSinglePlayerException toolCardAlreadyUsedSinglePlayerException = new ToolCardAlreadyUsedSinglePlayerException("exception");
    }
}