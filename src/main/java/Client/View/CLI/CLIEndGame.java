package Client.View.CLI;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIPrivateCards;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Shared.Model.ObjectiveCard.PrivateObjective;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIEndGame extends CLI implements CLIInterface {

    private Client client;

    CLIEndGame(Client client){
        this.client = client;
    }

    /**
     * shows end game state
     * @author Fabrizio Siciliano
     * */
    public void showCLI(){
        if(!client.getHandler().getMainController().isSinglePlayer()) {
            System.out.println(ansi().fg(getOptionColor()));
            System.out.println("****************************************************************************");
            System.out.println("*                                                                          *");
            System.out.println("*                     ECCO I RISULTATI DELLA PARTITA                       *");
            System.out.println("*                                                                          *");
            System.out.println("****************************************************************************\n");

            ArrayList<String> ranking = client.getHandler().getSetupController().getRanking();
            if (ranking != null) {
                for (int i = 0; i < ranking.size(); i++) {
                    int points = client.getHandler().getSetupController().getPoints(ranking.get(i));
                    System.out.println(ansi().fg(getOptionColor()).a(i + 1 + ") " + ranking.get(i) + ": " + points).fg(getDefaultColor()));
                }
            } else {
                System.err.println("Errore in ranking catching");
            }
        } else {
            System.out.println(ansi().fg(getOptionColor()));
            System.out.println("****************************************************************************");
            System.out.println("*                                                                          *");
            System.out.println("*                        SCEGLI UNA CARTA PRIVATA                          *");
            System.out.println("*                                                                          *");
            System.out.println("****************************************************************************\n");

            ArrayList<PrivateObjective> cards = client.getHandler().getCardsController().getPrivateObjective();
            CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
            CLIPrivateCards.showSinglePrivateCard(cards.get(0), cards.get(1));
            String choice = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            do{
                try {
                    choice = br.readLine();
                } catch (IOException i){
                    System.out.println("Prova a reinserire");
                }
            } while(!choice.equals("1") && !choice.equals("2"));

            if(choice.equals("1"))
                client.getHandler().getCardsController().setPrivateObjective(cards.get(0).getName());
            else if(choice.equals("2"))
                client.getHandler().getCardsController().setPrivateObjective(cards.get(1).getName());

            while(!client.getHandler().getMainController().isServerComputing()){ }

            int myPoints = client.getHandler().getSetupController().getPoints(client.getHandler().getMainController().getPlayerName());
            int totalPoints = client.getHandler().getSetupController().getObjectivePoints();

            if(myPoints <= totalPoints){
                System.out.println(ansi().fg(getOptionColor()));
                System.out.println("****************************************************************************");
                System.out.println("*                                                                          *");
                System.out.println("*                                 HAI PERSO                                *");
                System.out.println("*                                                                          *");
                System.out.println("****************************************************************************\n");
            }
            else{
                System.out.println(ansi().fg(getOptionColor()));
                System.out.println("****************************************************************************");
                System.out.println("*                                                                          *");
                System.out.println("*                                 HAI VINTO                                *");
                System.out.println("*                                                                          *");
                System.out.println("****************************************************************************\n");
            }

            System.out.println("I miei punti: " + myPoints);
            System.out.println("I punti obbiettivo: " + totalPoints);

        }

        System.out.println(ansi().fg(getAcceptedColor()).a("La partita è finita. Chiudo la partita tra pochi secondi"));
    }
}
