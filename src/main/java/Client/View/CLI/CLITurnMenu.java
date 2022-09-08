package Client.View.CLI;

import Client.Client;
import Client.View.CLI.CLIHUDItems.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.*;

import static org.fusesource.jansi.Ansi.ansi;

public class CLITurnMenu extends CLI implements CLIInterface{

    private Client client;

    public CLITurnMenu(Client client){
        this.client = client;
    }

    private static final int OPTION1 = 1;
    private static final int OPTION2 = 2;
    private static final int OPTION3 = 3;
    private static final int OPTION4 = 4;
    private static final int OPTION5 = 5;
    private static final int OPTION6 = 6;
    private static final int OPTION7 = 7;
    private static final int OPTION8 = 8;
    private static final int MAX_OPTIONS = 10; //it is equal to last option + 2! (ex: OPTION6 + 2 = 8)

    /**
     * Creates a new Executor Thread every turn for the turn options and actions
     * @author Marco Premi,Fabrizio Siciliano
     * */
    private boolean waitForNextRound(){
        long MAX_TIME_WAITING = 5*1000;
        long startTime = System.currentTimeMillis();
        while(!client.getHandler().getMainController().isMyTurn()){
            if(client.getHandler().getMainController().hasGameEnded()){
                return false;
            }
            if(System.currentTimeMillis() - startTime >= MAX_TIME_WAITING){
                System.out.println(ansi().fg(getOptionColor()).a("Aspetto che gli altri client finiscano i rispettivi turni"));
                startTime = System.currentTimeMillis();
            }
        }
        return true;
    }

    /**
     * Creates a new Executor Thread every turn for the turn options and actions
     * @author Marco Premi,Fabrizio Siciliano
     * */
    public void showCLI(){
        do {
            if (waitForNextRound()) {
                try {
                    System.in.read(new byte[System.in.available()]);
                } catch (IOException e) {
                    System.err.println("Errore in SystemIn read");
                }
                ExecutorService ex = Executors.newSingleThreadExecutor();
                Future<Void> result = ex.submit(new CLIThread());
                while (client.getHandler().getMainController().isMyTurn()) {
                }
                result.cancel(true);
                ex.shutdownNow();
            }
        } while(!client.getHandler().getMainController().hasGameEnded());

    }

    /**
     * Shows all the different actions the player can do
     * @author Marco Premi,Fabrizio Siciliano
     * */
    private class CLIThread implements Callable{

        @Override
        public Void call() {
            Vector noNormalMove = new Vector();
            noNormalMove.add("false");
            boolean normalMoveUsed = false;
            boolean toolUsed = false;
            if(!client.getHandler().getMainController().hasGameEnded()) {
                normalMoveUsed = false;
                toolUsed = false;
                System.out.println(ansi().fg(getOptionColor()).a("E' il tuo turno!"));

                //option 1
                System.out.println(ansi().fg(getOptionColor()).a(OPTION1 + ") ").a("Mostra le carte pubbliche"));

                //option 2
                System.out.println(ansi().fg(getOptionColor()).a(OPTION2 + ") ").fg(getDefaultColor()).a("Mostra le carte utensili"));

                //option 3
                if (client.getHandler().getMainController().isSinglePlayer())
                    System.out.println(ansi().fg(getOptionColor()).a(OPTION3 + ") ").fg(getDefaultColor()).a("Mostra le carte private"));
                else
                    System.out.println(ansi().fg(getOptionColor()).a(OPTION3 + ") ").fg(getDefaultColor()).a("Mostra la carta privata"));

                //option 4
                System.out.println(ansi().fg(getOptionColor()).a(OPTION4 + ") ").fg(getDefaultColor()).a("Mostra lo schema attuale"));

                //option 5
                System.out.println(ansi().fg(getOptionColor()).a(OPTION5 + ") ").fg(getDefaultColor()).a("Utilizza una carta tool"));

                //option 6
                System.out.println(ansi().fg(getOptionColor()).a(OPTION6 + ") ").fg(getDefaultColor()).a("Prendi un dado e piazzalo sullo schema"));

                //option 7
                System.out.println(ansi().fg(getOptionColor()).a(OPTION7 + ") ").fg(getDefaultColor()).a("Mostra draftpool"));

                //option 8
                System.out.println(ansi().fg(getOptionColor()).a(OPTION8 + ") ").fg(getDefaultColor()).a("Mostra tracciato dei round"));

                //option MAX_OPTIONS - 1
                if (!client.getHandler().getMainController().isSinglePlayer())
                    System.out.println(ansi().fg(getOptionColor()).a(MAX_OPTIONS - 1 + ") ").fg(getDefaultColor()).a("Mostra la facciata degli altri giocatori"));

                //last option (10)
                System.out.println(ansi().fg(getOptionColor()).a(MAX_OPTIONS + ") ").fg(getDefaultColor()).a("Passa il turno"));
                int option;
                try {
                    while (true) {
                        if(!client.getHandler().getMainController().isMyTurn()){
                            System.out.println(ansi().fg(getWarningColor()).a("E' scaduto il tempo, aspetta il prossimo turno").fg(getDefaultColor()));
                            break;
                        }


                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            String line;
                                try{
                                    do{
                                        while(!br.ready()){
                                            Thread.sleep(100);
                                        }
                                        line = br.readLine();
                                    }while (!line.equals("1") && !line.equals("2") && !line.equals("3") && !line.equals("4") && !line.equals("5") && !line.equals("6") && !line.equals("7") && !line.equals("8") && !line.equals("9")&&!line.equals("10"));

                                } catch (InterruptedException e){
                                    System.err.println("E' finito il tuo turno, troppo tempo!");
                                    return null;
                                }

                        option = Integer.parseInt(line);

                        switch (option) {
                            case OPTION1:
                                CLIPublicCards.showPublicCards(client.getHandler().getCardsController().getPublicObjectives());
                                break;
                            case OPTION2:
                                CLIToolCards.showToolCards(client.getHandler().getToolController().getToolCards());
                                break;
                            case OPTION3:
                                if(client.getHandler().getMainController().isSinglePlayer()){
                                    CLIPrivateCards.showSinglePrivateCard(client.getHandler().getCardsController().getPrivateObjective().get(0),client.getHandler().getCardsController().getPrivateObjective().get(1));
                                }else{
                                    CLIPrivateCards.showMultiPrivateCard(client.getHandler().getCardsController().getPrivateObjective().get(0));
                                }
                                break;
                            case OPTION4:
                                if(!client.getHandler().getMainController().isSinglePlayer()){
                                    System.out.println("Favori: "+client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
                                }
                                CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                                break;
                            case OPTION5:
                                if(toolUsed){
                                    System.out.println("Hai già usato una tool card in questo turno!");
                                }else{
                                    if(CLIToolCards.useToolCards(client.getHandler().getToolController().getToolCards(), client,normalMoveUsed, noNormalMove)){
                                        toolUsed=true;
                                    }else {
                                        toolUsed=false;
                                    }

                                }
                                break;
                            case OPTION6:
                                if(normalMoveUsed||noNormalMove.get(0).equals("true")){
                                    System.out.println("Hai già piazzato un dado in questo turno!");
                                }else{
                                    if(CLIToolCards.useNormalMove(client)){
                                        normalMoveUsed=true;
                                        System.out.println("Sono riuscito a piazzare il dado!");
                                    }else {
                                        System.out.println("Non sono riuscito a piazzare il dado!");
                                        normalMoveUsed=false;
                                    }

                                }
                                break;
                            case OPTION7:
                                CLIDraftPool.showDraftPool(client.getHandler().getTableController().getDraftPool());
                                break;
                            case OPTION8:
                                CLIRoundTrace.showRoundTrace(client.getHandler().getTableController().getRoundTrace());
                                break;
                            case MAX_OPTIONS - 1: {
                                if(!client.getHandler().getMainController().isSinglePlayer()){
                                    client.getHandler().getTableController().showOtherPlayerSchemes();

                                }
                                break;
                            }
                            case MAX_OPTIONS:
                                System.out.println(ansi().fg(getWarningColor()).a("Passo il turno...").fg(getDefaultColor()));
                                client.getHandler().getMainController().setMyTurn(false);
                                client.getHandler().getTurnController().endMyTurn();
                                break;
                            default:
                                System.out.println(ansi().fg(getWarningColor()).a("Scelta sbagliata, mettine una nuova!").fg(getDefaultColor()));
                                break;
                        }
                        //exit from menu = pass the turn
                        if (option == MAX_OPTIONS)
                            break;
                    }
                    return null;
                } catch (IOException i) {
                    System.err.println("Errore dalla CLITurnMenu");
                }
            }
            return null;
        }
    }
}