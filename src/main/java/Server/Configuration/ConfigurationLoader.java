package Server.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private Properties properties;

    /**
     * constructor
     * @author Abu Hussnain Saeed
     */
    public ConfigurationLoader(){
        properties = new Properties();
    }

    /**
     * load from the configuration file the time that players need to wait for other players to join the lobby if there aren't four players
     * @return LobbyWaitingTimer
     * @throws IOException if the configuration file isn't found
     * @author Abu Hussnain Saeed
     */
    public int getLobbyTimer() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        inputStream.close();
        return Integer.parseInt(properties.getProperty("LobbyWaitingTimer"));
    }

    /**
     * load from the configuration file the time that a player have to play his turn
     * @return TurnWaitingTimer
     * @throws IOException if the configuration file isn't found
     * @author Abu Hussnain Saeed
     */
    public int getTurnTimer() throws IOException{
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        inputStream.close();
        return Integer.parseInt(properties.getProperty("TurnWaitingTimer"));
    }
}
