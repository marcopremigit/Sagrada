package Server.ServerSocket;

import Server.MainServer;
import Server.RMIInterfaceImplementation.ConnectionController;
import Server.ServerSocket.SocketCommandHandlers.*;
import Shared.Player;
import Shared.RMIInterface.RMIClientInterface;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SocketClient implements RMIClientInterface, Runnable {
    /*-----------------------------Static attributes---------------------------------------*/
    private static final String LOGIN = "login";
    private static final String JOINGAME = "joingame";
    private static final String RANKING = "getRanking";
    private static final String GETPOINTS = "getpoints";
    private static final String GETCOLOR = "getcolor";
    private static final String DIFFICULTY = "difficulty";
    private static final String OBJECTIVEPOINTS = "getobjectivepoints";

    private static final String PRIVATEOBJECTIVE1 = "getprivateobjective1";
    private static final String PRIVATEOBJECTIVE2 = "getprivateobjective2";
    private static final String SETPRIVATE = "setprivate";
    private static final String SCHEMECARD = "getSchemeCard";
    private static final String PUBLICOBJECTIVES = "getpublicobjectives";
    private static final String SETSCHEME = "setscheme";

    private static final String DRAFTPOOL = "getdraftpool";
    private static final String ROUNDTRACE = "getroundtrace";
    private static final String PLAYERSCHEME = "getplayerscheme";
    private static final String PLAYERSNAME = "getplayersnames";

    private static final String FIRSTTURN = "iffirstturn";
    private static final String ENDTURN = "endturn";
    private static final String ASKROUND = "whatroundis";

    private static final String FAVORS = "getfavors";
    private static final String TOOLCARDS = "gettools";
    private static final String NORMALMOVE = "movedice";

    private static final String REMOVEDICEDRAFTPOOL = "removedicedraftpool";


    //save card on server
    private static final String SAVECARD = "savecard";
    private static final String regex = "/";

    //all tools
    private static final String ALESATOREPERLAMINADIRAME = "alesatoreperlaminadirame";
    private static final String DILUENTEPERPASTASALDASWITCH = "diluenteperpastasaldaswitch";
    private static final String DILUENTEPERPASTASALDASETVALUE = "diluenteperpastasaldasetvalue";
    private static final String DILUENTEPERPASTASALDAPLACE = "diluenteperpastasaldaplace";
    private static final String LATHEKIN = "lathekin";
    private static final String MARTELLETTO = "martelletto";
    private static final String PENNELLOPEREGLOMISE = "pennellopereglomise";
    private static final String PENNELLOPERPASTASALDAROLL = "pennelloperpastasaldaroll";
    private static final String PENNELLOPERPASTASALDPLACE = "pennelloperpastasaldaplace";
    private static final String PINZASGROSSATRICE = "pinzasgrossatrice";
    private static final String RIGAINSUGHERO = "rigainsughero";
    private static final String TAGLIERINACIRCOLARE = "taglierinacircolare";
    private static final String TAGLIERINAMANUALE = "taglierinamanuale";
    private static final String TAMPONEDIAMANTATO = "tamponediamanto";
    private static final String TENAGLIAAROTELLE = "tenagliaarotelle";
    /*-------------------------------------------------------------------------------------*/
    private TurnCommandHandler turnHandler;
    private BufferedReader inputFromClient;
    private PrintWriter outputToClient;
    private SyncedOutput syncedOutput;
    private ReentrantLock lock;
    private String name;

    private ConnectionCommandHandler connectionHandler;
    private CardsCommandHandler cardsHandler;
    private TableCommandHandler tableHandler;
    private ToolCommandHandler toolHandler;

    /**
     * @param  socket for input/output/connection
     * @author Fabrizio Siciliano
     * */
    public SocketClient(Socket socket){
        try{
            inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputToClient = new PrintWriter(socket.getOutputStream(), true);
            syncedOutput = new SyncedOutput(outputToClient);
            connectionHandler = new ConnectionCommandHandler(syncedOutput);
            cardsHandler = new CardsCommandHandler(syncedOutput);
            tableHandler = new TableCommandHandler(syncedOutput);
            turnHandler = new TurnCommandHandler(syncedOutput);
            toolHandler = new ToolCommandHandler(syncedOutput);
            lock = new ReentrantLock();
        }catch (IOException e){}
    }

    public void setUserName(String name){
        this.name = name;
    }

    /**
     * handles IO buffers for the server
     * @author Fabrizio Siciliano
     * */
    @Override
    public void run(){
        List<String> input;
        String command = "InitialValue";

        while(!command.equals("disconnect")) {
            try {
                String stringInput = inputFromClient.readLine();
                if(stringInput!=null) {
                    lock.lock();
                    input = Arrays.asList(stringInput.split(regex));
                    command = input.get(0);
                    switch (command) {
                        //setup
                        case LOGIN:
                            connectionHandler.login(this, input.get(1));
                            break;
                        case JOINGAME:
                            connectionHandler.joinGame(input.get(1),input.get(2));
                            break;
                        case RANKING:
                            connectionHandler.getRanking();
                            break;
                        case GETCOLOR:
                            connectionHandler.getColor(input.get(1));
                        break;
                        case DIFFICULTY:
                            connectionHandler.setDifficulty(input.get(1));
                        break;
                        case OBJECTIVEPOINTS:
                            connectionHandler.getObjectivePoints();
                            break;
                        case REMOVEDICEDRAFTPOOL:
                            toolHandler.removeDiceFromDraftPool(input.get(1));
                        break;
                        //cards
                        case PRIVATEOBJECTIVE1:
                            cardsHandler.getPrivateObjective1(input.get(1));
                        break;
                        case PRIVATEOBJECTIVE2:
                            cardsHandler.getPrivateObjective2(input.get(1));
                        break;
                        case SETPRIVATE:
                            cardsHandler.setPrivateObjective(input.get(1));
                        break;
                        case SCHEMECARD:
                            cardsHandler.getSchemeCard();
                        break;
                        case GETPOINTS:
                            connectionHandler.getPoints(input.get(1));
                        break;
                        case PUBLICOBJECTIVES:
                            cardsHandler.getPublicObjectives();
                        break;
                        case SETSCHEME:
                            cardsHandler.setScheme(input.get(1), input.get(2));
                        break;
                        //table
                        case DRAFTPOOL:
                            tableHandler.getDraftPool();
                        break;
                        case ROUNDTRACE:
                            tableHandler.getRoundTrace();
                        break;
                        case PLAYERSCHEME:
                            tableHandler.getScheme(input.get(1));
                        break;
                        case PLAYERSNAME:
                            tableHandler.getPlayersNames(name);
                        break;
                        //turn
                        case FIRSTTURN:
                            turnHandler.isFirstTurn();
                            break;
                        case ENDTURN:
                            turnHandler.endTurn(input.get(1));
                            break;
                        case ASKROUND:
                            turnHandler.whatRoundIs();
                            break;
                        //tool
                        case FAVORS:
                            toolHandler.getFavors(input.get(1));
                            break;
                        case TOOLCARDS:
                            toolHandler.getToolCards();
                            break;
                        case NORMALMOVE:
                            toolHandler.normalMove(input.get(1), input.get(2), input.get(3), input.get(4));
                            break;
                        case ALESATOREPERLAMINADIRAME:
                            toolHandler.useAlesatorePerLaminaDiRame(input.get(1), input.get(2), input.get(3), input.get(4),input.get(5));
                            break;
                        case DILUENTEPERPASTASALDASWITCH:
                            toolHandler.useDiluentePerPastaSaldaSwitch(input.get(1), input.get(2));
                            break;
                        case DILUENTEPERPASTASALDASETVALUE:
                            toolHandler.useDiluentePerPastaSaldaSetValue(input.get(1), input.get(2));
                            break;
                        case DILUENTEPERPASTASALDAPLACE:
                            toolHandler.useDiluentePerPastaSaldaPlace(input.get(1), input.get(2), input.get(3), input.get(4));
                            break;
                        case LATHEKIN:
                            toolHandler.useLathekin(input.get(1), input.get(2), input.get(3), input.get(4), input.get(5), input.get(6), input.get(7), input.get(8), input.get(9));
                            break;
                        case MARTELLETTO:
                            toolHandler.useMartelletto(input.get(1), input.get(2));
                            break;
                        case PENNELLOPEREGLOMISE:
                            toolHandler.usePennelloPerEglomise(input.get(1), input.get(2),input.get(3), input.get(4), input.get(5));
                            break;
                        case PENNELLOPERPASTASALDAROLL:
                            toolHandler.usePennelloPerPastaSaldaRoll(input.get(1), input.get(2));
                            break;
                        case PENNELLOPERPASTASALDPLACE:
                            toolHandler.usePennelloPerPastaSaldaPlace(input.get(1), input.get(2), input.get(3), input.get(4));
                            break;
                        case PINZASGROSSATRICE:
                            toolHandler.usePinzaSgrossatrice(input.get(1), input.get(2), input.get(3));
                            break;
                        case RIGAINSUGHERO:
                            toolHandler.useRigaInSughero(input.get(1), input.get(2), input.get(3), input.get(4));
                            break;
                        case TAGLIERINACIRCOLARE:
                            toolHandler.useTaglierinaCircolare(input.get(1), input.get(2),input.get(3), input.get(4));
                            break;
                        case TAGLIERINAMANUALE:
                            toolHandler.useTaglierinaManuale(input.get(1), input.get(2),input.get(3),input.get(4),input.get(5),input.get(6), input.get(7),input.get(8),input.get(9));
                            break;
                        case TAMPONEDIAMANTATO:
                            toolHandler.useTamponeDiamantato(input.get(1), input.get(2));
                            break;
                        case TENAGLIAAROTELLE:
                            toolHandler.useTenagliaARotelle(input.get(1), input.get(2),input.get(3), input.get(4));
                            break;
                            //save card setting
                        case SAVECARD:
                            cardsHandler.saveScheme(input.get(1));
                            break;
                        default:
                            System.out.println("[Socket Server]\tInput command not defined: " + command + " from client " + name);
                            break;
                    }
                    lock.unlock();
                }else{
                    throw new IOException();
                }
            } catch (IOException i) {
                for(Player player:MainServer.getLobby().getPlayers()){
                    if(player.getName().equals(name)){
                        MainServer.getLobby().clientNotAvailable(name);
                        break;
                    }
                }
                MainServer.getLobby().removePlayerFromLobby(name);
                ConnectionController.removeClient(name);
                return;
            }
        }
    }


    private static final String GAMESTARTED = "gamestarted";
    private static final String PLAYERNOTAVL = "playernotavailable";
    private static final String STARTTURN = "startturn";
    private static final String ENDPLAYERTURN = "endturn";
    private static final String PLAYERAVL = "playeravailable";
    private static final String UPDATE = "update";
    private static final String CONTINUEGAME = "continue";
    private static final String GAMEENDED = "gameended";
    private static final String PLAYERSREADY = "playersready";
    private static final String ENDEDCOMPUTING = "endedcomputing";


    @Override
    public synchronized void ping(){

    }

    @Override
    public synchronized void startGame()  {
        syncedOutput.println(GAMESTARTED);
    }

    @Override
    public synchronized void notifyStartTurn() {
        syncedOutput.println(STARTTURN);
    }

    @Override
    public synchronized void notifyEndTurn() {
        syncedOutput.println(ENDPLAYERTURN);
    }

    @Override
    public synchronized void playerNotAvailable(String name) {
        syncedOutput.println(PLAYERNOTAVL + regex + name);
    }

    @Override
    public void playersReady() {
        syncedOutput.println(PLAYERSREADY);
    }

    @Override
    public synchronized void update() {
        syncedOutput.println(UPDATE);
    }

    @Override
    public synchronized void playerAgainAvailable(String name) {
        syncedOutput.println(PLAYERAVL + regex + name);
    }

    @Override
    public synchronized void continuePlaying() {
        syncedOutput.println(CONTINUEGAME);
    }

    @Override
    public void gameEnded(){
        syncedOutput.println(GAMEENDED);
    }

    @Override
    public void notifyEndedComputing() {
        syncedOutput.println(ENDEDCOMPUTING);
    }

    public class SyncedOutput{
        private PrintWriter out;

        public SyncedOutput(PrintWriter out){
            this.out = out;
        }

        public synchronized void println(String message){
            out.flush();
            out.println(message);
        }
    }
}
