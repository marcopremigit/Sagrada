package Server.RMIInterfaceImplementation;

import Server.Lobby;
import Server.MainServer;
import Server.ServerClient;
import Server.ServerSocket.SocketClient;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;
import Shared.Player;
import Shared.RMIInterface.ConnectionInterface;
import Shared.RMIInterface.RMIClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ConnectionController extends UnicastRemoteObject implements ConnectionInterface {

    private static volatile ArrayList<ServerClient> allClients;

    public ConnectionController() throws RemoteException { }

    public static List<ServerClient> getClients(){
        return allClients;
    }
    /**
     * @return {@link ServerClient} client in {@link ConnectionController#allClients} list
     * @author Fabrizio Siciliano
     * */
    public static ServerClient getClient(String name){
        for(int i=0; i<allClients.size(); i++){
            if(allClients.get(i).getUsername().equals(name))
                return allClients.get(i);
        }
        return null;
    }
    /**
     * removes client with given name
     * @param name name of the client to be removed
     * @author Fabrizio Siciliano
     * */
    public static void removeClient(String name){
        try {
            for (int i=0; i<allClients.size(); i++) {
                ServerClient client = allClients.get(i);
                if (client.getUsername().equals(name)) {
                    allClients.remove(client);

                    return;
                }
            }
        } catch (ConcurrentModificationException c){
            System.err.println("[Server]\tCatched ConcurrentModificationException");
        }
    }

    /**
     * pings all client in map
     * @author Fabrizio Siciliano
     * */
    public void pingAll() {
        Iterator iterator = allClients.iterator();
        while(iterator.hasNext()){
            ServerClient client = (ServerClient) iterator.next();
            try{
                client.getClient().ping();
            } catch (RemoteException r){
                System.out.println("[Server]\tFailed to ping " + client.getUsername() + ", disconnecting");
                iterator.remove();
                if(MainServer.getLobby()!=null)
                    MainServer.getLobby().removePlayerFromLobby(client.getUsername());
            }
        }
    }

    @Override
    public boolean login(String newName, RMIClientInterface newClient, boolean RMI) {
        if(newName==null){
            return false;
        }
        if(allClients == null)
            allClients = new ArrayList<>();
        //ping all clients to check valid connections
        pingAll();

        //checks unique name on server
        for(ServerClient client : allClients){
            if(client.getUsername().equals(newName))
                return false;
        }
        if(RMI) {
            allClients.add(new ServerClient(newName, newClient));
        } else {
            SocketClient client = (SocketClient) newClient;
            client.setUserName(newName);
            allClients.add(new ServerClient(newName, client));
        }
        System.out.println("[Server]\t" + newName + " has entered server");
        return true;
    }


    @Override
    public void setDifficulty( int difficulty){
        MainServer.getLobby().setDifficulty(difficulty);
    }

    @Override
    public void removePlayer(String name){
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name)){
                lobby.removePlayerFromLobby(player.getName());
                break;
            }
        }
    }

    @Override
    public void pingServer(){ }


    @Override
    public boolean joinGame(String playerName, boolean singleplayer) throws LobbyFullException {
        if(MainServer.getLobby() == null){
            MainServer.setLobby(new Lobby("Partita 1"));
            MainServer.getLobby().setSinglePlayer(singleplayer);
            new Thread(MainServer.getLobby()).start();
        }

        return MainServer.getLobby().addPlayerToLobby(playerName);
    }
    @Override
    public Color getPlayerColor(String playerName){
        Color color = null;
        for(Player player : MainServer.getLobby().getPlayers())
            if(player.getName().equals(playerName)){
                color =  player.getColor();
                break;
            }
        return color;
    }

    @Override
    public ArrayList<String> getOtherPlayerName(String actualPlayerName){
        ArrayList<String> otherPlayerName = new ArrayList<>();
        Lobby lobby = MainServer.getLobby();

        for(Player player : lobby.getPlayers()) {
            if (!player.getName().equals(actualPlayerName))
                otherPlayerName.add(player.getName());
        }
        return otherPlayerName;
    }

    @Override
    public ArrayList<String> getRanking() {
        return MainServer.getLobby().getGame().getRanking();
    }
    @Override
    public int getPoints(String playerName){
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getName().equals(playerName)){
                return player.getPoints();
            }
        }
        return -99;
    }
    /**
     * notifies clients that player with given name is available
     * @param name name of the client available
     * @author Abu Hussnain Saeed, Fabrizio Siciliano
     * */
    public static void playerAgainAvailable(String name) {
        for (ServerClient client : allClients) {
            try {
                if (!client.getUsername().equals(name)) {
                    client.getClient().playerAgainAvailable(name);
                } else {
                    client.getClient().continuePlaying();
                }
            } catch (RemoteException r) {
                MainServer.getLobby().removePlayerFromLobby(client.getUsername());
                removeClient(client.getUsername());
            }
        }
    }
    @Override
    public int getObjectivePoints(){
        return MainServer.getLobby().getPlayers().get(0).getObjectivePoint();
    }
}
