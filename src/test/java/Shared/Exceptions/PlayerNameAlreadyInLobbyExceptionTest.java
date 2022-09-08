package Shared.Exceptions;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerNameAlreadyInLobbyExceptionTest {
    @Test
    public void test(){
        PlayerNameAlreadyInLobbyException playerNameAlreadyInLobbyException = new PlayerNameAlreadyInLobbyException();
        PlayerNameAlreadyInLobbyException playerNameAlreadyInLobbyException1 = new PlayerNameAlreadyInLobbyException("exception");
    }

}