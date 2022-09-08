package Shared.Exceptions;

import Server.Lobby;
import org.junit.Test;

import static org.junit.Assert.*;

public class LobbyFullExceptionTest {
    @Test
    public void test(){
        LobbyFullException lobbyFullException = new LobbyFullException();
        LobbyFullException lobbyFullException1 = new LobbyFullException("excpetion");
    }

}