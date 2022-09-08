package Client.View.CLI.CLIHUDItems;

import Client.Client;
import Client.View.CLI.CLI;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.RoundTrace.RoundTrace;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIRoundTrace extends CLI {
    /**
     * It shows the Round Trace
     * @param roundTrace the round trace
     * @author Marco Premi
     *
     */
    public static void showRoundTrace(RoundTrace roundTrace){
        ansi().fg(Ansi.Color.DEFAULT);
        for(int i=0; i<roundTrace.getTrace().size();i++){
            int j=i+1;
            System.out.println("Round: "+j);
            for(int k=0; k<roundTrace.getTrace().get(i).getCell().size();k++){
                System.out.println("Indice: "+k+" Dado: "+ansi().fg(getAnsiColor(roundTrace.getTrace().get(i).getCell().get(k).getColor())).a(getDiceUnicode(roundTrace.getTrace().get(i).getCell().get(k).getTop())).fg(Ansi.Color.DEFAULT));

            }
        }
    }
    /**
     *
     * @param roundTrace the roundtrace
     * @param client player's client
     * @return the selected round from the round trace
     * @author Marco Premi
     *
     */
    public static int selectRound(RoundTrace roundTrace, Client client) throws TimeExceededException {
        try{
            int round = -1;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            if(client.getHandler().getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserire round in cui prendere dado oppure -1 per uscire: ");
                        try{
                            while(!br.ready()){
                                Thread.sleep(100);
                            }
                            line = br.readLine();
                        } catch (InterruptedException e){
                            return -1;
                        }
                        round = Integer.parseInt(line)-1;
                        if(round==-1){
                            return -1;
                        }
                        if(round>=0&&round< roundTrace.getTrace().size()){
                            break;
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }

            if(client.getHandler().getMainController().isMyTurn()){
                return round;
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e ){}
        return -1;
    }
    /**
     *
     * @param roundTrace the roundtrace
     * @param client player's client
     * @return the selected index from the round trace
     * @author Marco Premi
     *
     */
    public static int selectIndex(RoundTrace roundTrace, Client client, int round) throws TimeExceededException{
        try{
            int index = -1;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            if(client.getHandler().getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserire indice in cui prendere dado oppure -1 per uscire: ");
                        try{
                            while(!br.ready()){
                                Thread.sleep(100);
                            }
                            line = br.readLine();
                        } catch (InterruptedException e){
                            return -1;
                        }
                        index = Integer.parseInt(line);
                        if(index==-1){
                            return -1;
                        }
                        if(index>=0&&index< roundTrace.getTrace().get(round).getCell().size()){
                            break;
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }

            if(client.getHandler().getMainController().isMyTurn()){
                return index;
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e ){}
        return -1;
}}
