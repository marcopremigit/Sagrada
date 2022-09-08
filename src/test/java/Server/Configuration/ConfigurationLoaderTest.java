package Server.Configuration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ConfigurationLoaderTest {
    ConfigurationLoader configurationLoader;
    int lobbyWaitingTimer = 60000;
    int turnWaitingTimer =  50000;

    /**
     * set up the configurarionLoader
     * @author Abu Hussnain Saeed
     */
    @Before
    public void setUp(){
        configurationLoader = new ConfigurationLoader();
    }

    /**
     * check if we get correctly the lobbyTimer
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getLobbyTimer() {
        try {
            Assert.assertEquals(lobbyWaitingTimer, configurationLoader.getLobbyTimer());

        }
        catch (IOException io){}
    }

    /**
     * check if we get correctly the turnTimer
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getTurnTimer(){
        try {
            Assert.assertEquals(turnWaitingTimer, configurationLoader.getTurnTimer());
        }
        catch (IOException io){}
    }

}
