package Client.View.CLI.CLIHUDItems;

import Client.Client;
import Shared.Model.Tools.ToolCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

public class CLIToolCards {

    /**
     * It shows the Tool Cards
     * @param toolCards Tool Cards ArrayList
     * @author Marco Premi
     *
     */
    public static void showToolCards(ArrayList<ToolCard> toolCards){
        for(ToolCard toolCard : toolCards){
            System.out.println(toolCard.toString());
        }
    }

    /**
     * It shows the draftpool
     * @param client player's client
     * @param toolCards Tool Cards ArrayList
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @param noNormalMove it checks if the player has already done a Normal Move during the turn
     * @return true if the Tool Cards is used correctly
     * @author Marco Premi
     *
     */
    public static boolean useToolCards(ArrayList<ToolCard> toolCards, Client client, boolean dicePlaced, Vector noNormalMove){
        boolean isPresent = false;
        int option;
        for(int i=0; i<toolCards.size();i++){
            System.out.println(i+") " + toolCards.get(i).getName());

        }

        try{
            //serve questo if?
           /* if(!client.getHandler().getMainController().isMyTurn()){
                System.out.println(ansi().fg(Ansi.Color.RED).a("E' scaduto il tempo, aspetta il prossimo turno").fg(Ansi.Color.DEFAULT));
                return false;
            }*/

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            try{
                do{
                    System.out.println("Inserisci il numero della carta da utilizzare oppure -1 per uscire: ");
                    while(!br.ready()){
                        Thread.sleep(100);
                    }
                    line = br.readLine();
                }while (!line.equals("0") && !line.equals("1") && !line.equals("2") && !line.equals("-1"));

            } catch (InterruptedException e){
              //  System.err.println("E' finito il tuo turno, troppo tempo!");
                return false;
            }

            option = Integer.parseInt(line);
            if(option==-1){
                return false;
            }

            System.out.println("Hai scelto la carta: "+toolCards.get(option));

            if(client.getToolViewController().useToolCard(toolCards.get(option).getName(), dicePlaced)) {
                client.getHandler().getToolController().setUsed(client.getHandler().getMainController().getPlayerName(), line);
                if(line.equals("Pennello per pasta salda")||line.equals("Diluente per Pasta Salda")||line.equals("Riga in Sughero")){
                  noNormalMove.remove(0);
                  noNormalMove.add("true");
                }
                return true;
            } else
                return false;
        }catch (IOException e){
            return false;
        }
    }
    /**
     * It uses the Normal Move
     * @param client player's client
     * @return true if the Normal Move is correctly used
     * @author Marco Premi
     *
     */
    public static boolean useNormalMove(Client client){
         return client.getToolViewController().useToolCard("Normal Move",false);
    }

}
