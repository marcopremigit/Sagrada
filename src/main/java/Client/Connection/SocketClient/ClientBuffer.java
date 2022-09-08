package Client.Connection.SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientBuffer {
    private static final String GAMESTARTED = "gamestarted";
    private static final String PLAYERNOTAVAILABLE = "playernotavailable";
    private static final String STARTTURN = "startturn";
    private static final String ENDTURN = "endturn";
    private static final String PLAYERAVAILABLE = "playeravailable";
    private static final String UPDATE = "UPDATE";
    private static final String CONTINUEGAME = "continue";
    private static final String GAMEENDED = "gameended";
    private static final String PLAYERSREADY = "playersready";
    private static final String ENDEDCOMPUTING = "endedcomputing";

    private volatile ArrayList<String> buffer;

    private volatile SocketClient client;
    private volatile BufferedReader reader;

    public ClientBuffer (SocketClient client,  InputStream iStream){
        this.client=client;
        buffer=new ArrayList<>();
        this.reader = new BufferedReader(new InputStreamReader(iStream));
    }

    /**
     * @param analyze {@link String} to be valued
     * @return true if the {@link String} to be analyzed is a message from the server. False if it answers a question
     * @author Marco Premi, Fabrizio Siciliano
     */
    private boolean analyze(String analyze){
        List<String> splitted = Arrays.asList(analyze.split("/"));
        switch (analyze) {
            case GAMESTARTED:
                client.getMainController().setGameStarted(true);
                return true;
            case STARTTURN:
                client.getTurnController().startTurn();
                return true;
            case ENDTURN:
                client.getMainController().setMyTurn(false);
                return true;
            case ENDEDCOMPUTING:
                client.getMainController().setServerComputing(true);
                return true;
            case PLAYERNOTAVAILABLE:
                client.getTableController().playerNotAvailable(splitted.get(1));
                return true;
            case UPDATE:
                if(!client.getMainController().isUsingCLI()) {
                    new Thread(() -> {
                        client.getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getTableController().getDraftPool());
                        client.getMainController().getPlayerHUD().getRoundTrace().updateRoundTrace(client.getTableController().getRoundTrace());
                        client.getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getToolController().getFavours(client.getMainController().getPlayerName()));
                    }).start();
                }
                return true;
            case PLAYERSREADY:
                client.getMainController().setPlayersReady(true);
                return true;
            case PLAYERAVAILABLE:
                client.getTableController().playerAgainAvailable(splitted.get(1));
                return true;
            case CONTINUEGAME:
                client.getMainController().setContinueGame(true);
                return true;
            case GAMEENDED:
                client.getMainController().setGameEnded(true);
                return true;
            default:
                return false;
        }
    }

    /**
     * inserts all strings read on socket buffer in {@link ClientBuffer#buffer} if {@link ClientBuffer#analyze(String)} returns false
     * @author Marco Premi, Fabrizio Siciliano
     */
    void insertInBuffer() {
        String line;
        try {
            line = reader.readLine();
            if(line==null){
                System.err.println("Errore di comunicazione con il server, chiudo il programma...");
                System.exit(42);
            }
            if (!analyze(line)) {
                buffer.add(line);
            }
        } catch (IOException e) {
            //Server got disconnected
            System.err.println("Errore di comunicazione con il server, chiudo il programma...");
            System.exit(42);
        }
    }

    /**
     * @param startsWith starting string to be looked for
     * @return value of the string if found
     * @author Marco Premi, Fabrizio Siciliano
     */
    String checkInBuffer(String startsWith){
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> result = es.submit(() -> {
            String stringToRemove=null;
            while(stringToRemove == null) {
                if(!buffer.isEmpty()) {
                    for (String string : buffer) {
                        if (string.startsWith(startsWith)) {
                            stringToRemove = string;
                            break;
                        }
                    }
                }
            }
            buffer.remove(stringToRemove);
            return stringToRemove;
        });
        try{
            return result.get();
        } catch (Exception e){
            return null;
        }
    }
}