package Client.View.GUI;

import Client.Client;

public class GUIMultiPlayerSetup {
    private Client client;

    public GUIMultiPlayerSetup(Client client){
        this.client = client;
    }

    /**
     * shows GUI for MultiPlayer setup
     * @see GUIWaitForLobbyReady for next screen
     * @author Fabrizio Siciliano
     * */
    public void showGUI(){
        GUIWaitForLobbyReady fourthPane = new GUIWaitForLobbyReady(client);
        fourthPane.showGUI();
    }
}
