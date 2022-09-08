package Shared.Exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class CannotConnectToLobbyExceptionTest {
    @Test
    public void test(){
        CannotConnectToLobbyException cannotConnectToLobbyException = new CannotConnectToLobbyException();
        CannotConnectToLobbyException cannotConnectToLobbyException1 = new CannotConnectToLobbyException("exception");
    }
}