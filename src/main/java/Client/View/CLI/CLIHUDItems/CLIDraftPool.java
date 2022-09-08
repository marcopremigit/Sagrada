package Client.View.CLI.CLIHUDItems;

import Client.Client;
import Client.View.CLI.CLI;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIDraftPool extends CLI{

    /**
     * It shows the draftpool
     * @param draftPool draftpool ArrayList
     * @author Marco Premi
     *
     */
    public static void showDraftPool(ArrayList<Dice> draftPool){
        for(int i=0; i<draftPool.size(); i++){
            System.out.println(ansi().fg(Ansi.Color.BLUE).a(i + ") ").fg(getAnsiColor(draftPool.get(i).getColor())).a(getDiceUnicode(draftPool.get(i).getTop())).fg(Ansi.Color.DEFAULT));
        }
    }
    /**
     *
     * @param draftPool draftpool ArrayList
     * @param client player's client
     * @return the selected dice from the draftpool
     * @author Marco Premi
     *
     */
    public static Dice selectDiceFromDraftpool(ArrayList<Dice> draftPool, Client client)throws TimeExceededException {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            int index=-1;


            showDraftPool(draftPool);
            while (true) {
                if(client.getHandler().getMainController().isMyTurn()){
                    do{
                        try{
                            System.out.println(ansi().fg(Ansi.Color.BLUE).a("Inserire indice dado da prendere oppure " + draftPool.size() +" per uscire dalla selezione").fg(Ansi.Color.DEFAULT));
                            try{
                                while(!br.ready()){
                                    Thread.sleep(100);
                                }
                                line = br.readLine();
                            } catch (InterruptedException e){
                                return null;
                            }
                            index = Integer.parseInt(line);
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Input non valido, riprova");
                        }
                    }while(true);
                }else {
                    throw new TimeExceededException();
                }

                if (index == draftPool.size()) {
                    return null;
                } else if (index >= 0 && index<draftPool.size()) {
                    System.out.println(ansi().fg(Ansi.Color.GREEN).a("Hai scelto il dado: ").fg(Ansi.Color.DEFAULT).a(draftPool.get(index).toString()));
                    break;
                } else {
                    System.out.println(ansi().fg(Ansi.Color.RED).a("Numero indice dado sbagliato").fg(Ansi.Color.DEFAULT));
                }
            }

            if(client.getHandler().getMainController().isMyTurn()){
                return draftPool.get(index);
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e){}
        return null;
    }

    /**
     *
     * @param draftPool draftpool ArrayList
     * @param client player's client
     * @return the index of the selected dice from the draftpool
     * @author Marco Premi
     *
     */
    public static int selectIndexDiceFromDraftpool(ArrayList<Dice> draftPool, Client client)throws TimeExceededException{
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            int index=-1;


            showDraftPool(draftPool);
            while (true) {
                if(client.getHandler().getMainController().isMyTurn()){
                    do{
                        try{
                            System.out.println(ansi().fg(Ansi.Color.BLUE).a("Inserire indice dado da prendere oppure " + draftPool.size() +" per uscire dalla selezione").fg(Ansi.Color.DEFAULT));
                            try{
                                while(!br.ready()){
                                    Thread.sleep(100);
                                }
                                line = br.readLine();
                            } catch (InterruptedException e){
                                return -1;
                            }
                            index = Integer.parseInt(line);
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Input non valido, riprova");
                        }
                    }while(true);
                }else {
                    throw new TimeExceededException();
                }

                if (index == draftPool.size()) {
                    return -1;
                } else if (index >= 0 && index<draftPool.size()) {
                    System.out.println(ansi().fg(Ansi.Color.GREEN).a("Hai scelto il dado: ").fg(Ansi.Color.DEFAULT).a(draftPool.get(index).toString()));
                    break;
                } else {
                    System.out.println(ansi().fg(Ansi.Color.RED).a("Numero indice dado sbagliato").fg(Ansi.Color.DEFAULT));
                }
            }

            if(client.getHandler().getMainController().isMyTurn()){
                return index;
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e){}
        return -1;
    }}

