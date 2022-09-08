package Client.View.CLI;

import Client.Client;

import java.util.Date;

public class CLIStaticMethods implements Runnable{
    private Client client;
    public CLIStaticMethods(Client client){
        this.client = client;
    }

    /**
     * Strategy pattern for game succession
     * @author Fabrizio Siciliano, Marco Premi
     * */
    @Override
    public void run(){
        CLI context = new CLI();

        context.setStrategy(new CLIUsername(client));
        context.executeStrategy();

        context.setStrategy(new CLIMainMenu(client));
        context.executeStrategy();

        if(client.getHandler().getMainController().isSinglePlayer()){
            context.setStrategy(new CLISinglePlayerSetup(client));
            context.executeStrategy();
        }

        if(!continueGame(client)) {
            context.setStrategy(new CLIPlayerSetup(client));
            context.executeStrategy();
            syncToGame(client);
        }

        context.setStrategy(new CLITurnMenu(client));
        context.executeStrategy();

        context.setStrategy(new CLIEndGame(client));
        context.executeStrategy();

        closeGame();
        System.exit(42);
    }

    /**
     * closing CLI state
     * @author Fabrizio Siciliano
     * */
    private void closeGame(){
        long MAX_TIME_WAITING = 10*1000L; //10 seconds waiting before closing application
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < MAX_TIME_WAITING) {
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }

    /**
     * shows game sync status
     * @author Fabrizio Siciliano
     * */
    private void syncToGame(Client client){
        long MAX_TIME_WAITING = 10*1000L;
        long startTime = System.currentTimeMillis();
        while (!client.getHandler().getMainController().arePlayersReady()) {
            if (System.currentTimeMillis() - startTime >= MAX_TIME_WAITING) {
                System.out.println("Aspetto di sincronizzarmi");
                startTime = System.currentTimeMillis();
            }
        }
    }


    /**
     * shows lobby sync status
     * @author Fabrizio Siciliano
     * */
    private boolean continueGame(Client client) {
        long MAX_TIME_WAITING = 10*1000;
        long startTime = System.currentTimeMillis();
        while (!client.getHandler().getMainController().isGameStarted() && !client.getHandler().getMainController().canContinueGame()) {
            if (System.currentTimeMillis() - startTime >= MAX_TIME_WAITING) {
                System.out.println("Aspetto che la lobby sia pronta");
                startTime = System.currentTimeMillis();
            }
        }
        if(client.getHandler().getMainController().canContinueGame())
            return true;
        else
            return false;
    }
}
