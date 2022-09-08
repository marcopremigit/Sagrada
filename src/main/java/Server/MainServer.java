package Server;

import Server.Configuration.ConfigurationLoader;
import Server.RMIInterfaceImplementation.*;
import Server.ServerSocket.SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    /*-------------------------------Controllers-------------------------------*/
    private static ConnectionController connectionController;
    private static DraftPoolController draftPoolController;
    private static PrivateObjectiveController privateObjectiveController;
    private static PublicObjectiveController publicObjectiveController;
    private static RoundTraceController roundTraceController;
    private static SchemeController schemeController;
    private static ToolController toolController;
    private static TurnController turnController;

    private static String address;
    private final static int RMIPORT = 10000;
    private final static int SOCKETPORT = 10001;
    private static ServerSocket serverSocket;

    private static long lobbyTimer;
    private static boolean serverOn;
    private static Lobby lobby;

    public MainServer(){  }

    public static void main(String[] args) {
        lobby = null;
        serverOn = true;
        //handle closing thread
        new Thread(() -> {
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while(serverOn) {
                    if (br.ready()) {
                        String line = br.readLine();
                        if(line.equals("stop")){
                            System.out.println("Chiudo il server tra 5 secondi...");
                            serverOn = false;
                        }
                    }
                    Thread.sleep(5000);
                }
                System.exit(0);
            } catch (IOException | InterruptedException i){
                System.exit(10);
            }
        }).start();

        //Start configuration
        try{
            ConfigurationLoader configurationLoader = new ConfigurationLoader();
            lobbyTimer = configurationLoader.getLobbyTimer();
        }
        catch (IOException i){
            System.out.println("[Server]\tIOException in main\n" + i.getMessage());
        }
        startRMIServer();

        System.out.println();

        startSocketServer();
    }

    /**
     * starts server socket
     * @author Fabrizio Siciliano
     * */
    public static void startSocketServer(){
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("[Socket Server]\tBooting server...");
        int port = SOCKETPORT;
        while(true) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("[Socket Server]\tServer succesfully booted on port " + port);
                break;
            } catch (IOException i) {
                System.out.println("[Socket Server]\tError during server setup");
                port++;
                System.out.println("[Socket Server]\tAttempting connection on port: "+ port);
            }
        }
        while(serverOn) {
            try{
                Socket socket = serverSocket.accept();
                System.out.println("[Socket Server]\tAccepted connection from " + socket.getInetAddress().toString());
                executor.submit(new     SocketClient(socket));
            } catch (IOException i) {
                if (i instanceof BindException) {
                    System.out.println("[Socket Server]\tNon sono riuscito ad accettare la connessione sulla porta" + port);
                    port ++;
                }
            }
        }
        executor.shutdown();
    }

    public static void startRMIServer(){
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                if(!n.isLoopback()) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        if(i.getHostAddress().startsWith("192.168.")||i.getHostAddress().startsWith("10.")){
                            address=i.getHostAddress();
                            //System.out.println(address);
                        }
                    }
                }
            }
            if(address==null){
                address = "localhost";
            }
            System.out.println("[Server]\tServer address: " + address);
        }
        catch (Exception e) {
            System.out.println("[RMI Server]\tCan't get inet address.");
        }
        System.out.println("[RMI Server]\tBooting server...");
        int port = RMIPORT;
        while(true) {
            try {
                Registry registry = LocateRegistry.createRegistry(port);
                bindAllControllers(registry);
                System.out.println("[RMI Server]\tSuccessful registry bind on port: " + port);
                break;
            } catch (RemoteException | AlreadyBoundException e) {
                if (e instanceof RemoteException)
                    System.out.println("[RMI Server]\tError during controllers export");
                if (e instanceof AlreadyBoundException)
                    System.out.println("[RMI Server]\tController already binded");
                port++;
                System.out.println("[RMI Server]\tAttempting connection on port: " + port);
            }
        }
    }

    private static void bindAllControllers(Registry registry) throws RemoteException,AlreadyBoundException {
        System.setProperty("java.rmi.server.hostname",address);
        connectionController = new ConnectionController();
        draftPoolController = new DraftPoolController();
        privateObjectiveController = new PrivateObjectiveController();
        publicObjectiveController = new PublicObjectiveController();
        roundTraceController = new RoundTraceController();
        schemeController = new SchemeController();
        toolController = new ToolController();
        turnController = new TurnController();
        registry.bind("ConnectionController", connectionController);
        registry.bind("DraftPoolController", draftPoolController);
        registry.bind("PrivateObjectiveController", privateObjectiveController);
        registry.bind("PublicObjectiveController", publicObjectiveController);
        registry.bind("RoundTraceController", roundTraceController);
        registry.bind("SchemeController", schemeController);
        registry.bind("RMIToolClientController", toolController);
        registry.bind("RMITurnController", turnController);
    }

    public static void setLobby(Lobby lobby1){
         lobby = lobby1;
    }

    public static Lobby getLobby(){
        return lobby;
    }

    public static long getLobbyTimer(){
        return lobbyTimer;
    }

    public static ConnectionController getConnectionController() {
        return connectionController;
    }

    public static DraftPoolController getDraftPoolController() {
        return draftPoolController;
    }

    public static PrivateObjectiveController getPrivateObjectiveController() {
        return privateObjectiveController;
    }

    public static PublicObjectiveController getPublicObjectiveController() {
        return publicObjectiveController;
    }

    public static RoundTraceController getRoundTraceController() {
        return roundTraceController;
    }

    public static SchemeController getSchemeController() {
        return schemeController;
    }

    public static ToolController getToolController() {
        return toolController;
    }

    public static TurnController getTurnController() {
        return turnController;
    }
}
