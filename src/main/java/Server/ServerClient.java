package Server;

import Shared.RMIInterface.RMIClientInterface;

public  class ServerClient{
    private RMIClientInterface client;
    private String username;

    public ServerClient(String name, RMIClientInterface client){
        this.username = name;
        this.client = client;
    }

    public RMIClientInterface getClient(){
        return client;
    }

    public String getUsername(){
        return username;
    }
}
