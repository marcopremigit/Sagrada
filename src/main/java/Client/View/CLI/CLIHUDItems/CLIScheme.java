package Client.View.CLI.CLIHUDItems;

import Client.Client;
import Client.View.CLI.CLI;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Schemes.Scheme;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIScheme extends CLI{

    /**
     * It shows player's favours
     * @param name player's name, favours of the players
     * @author Marco Premi
     *
     */
    public static void showFavorsAndName(String name, int favours){
        System.out.println("Facciata del giocatore: "+name+" con "+ favours + " favori");
    }
    /**
     * It shows player's scheme
     * @param scheme player's scheme
     * @author Marco Premi
     *
     */
    public static void showScheme(Scheme scheme){
        for (int i = 0; i < scheme.getScheme().length; i++) {
            if (i == 0) {
                System.out.print("  ");
                for (int k = 0; k < scheme.getScheme()[i].length; k++) {
                    System.out.print(k + " ");
                }
                System.out.println();
            }
            for (int j = 0; j < scheme.getScheme()[i].length; j++) {
                if (j == 0) {
                    System.out.print(i + " ");
                }

                if(!scheme.getScheme()[i][j].isOccupied()){

                    System.out.print(ansi().fg(getAnsiColor(scheme.getScheme()[i][j].getColor())).a(scheme.getScheme()[i][j].toString()).fg(Ansi.Color.DEFAULT).a(" "));
                }
                else{
                    System.out.print(ansi().fg(getAnsiColor(scheme.getScheme()[i][j].getDado().getColor())).a(getDiceUnicode(scheme.getScheme()[i][j].getDado().getTop())).fg(Ansi.Color.DEFAULT).a(" "));
                    //System.out.print(ansi().fg(getAnsiColor(scheme.getScheme()[i][j].getDado().getColor())).a(scheme.getScheme()[i][j].getDado().getTop()).fg(Ansi.Color.DEFAULT).a(" "));
                }

            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * @param client player's client
     * @param scheme player's scheme
     * @return : the selected row position of the scheme
     * @author Marco Premi
     *
     */
    public static int selectPositionRow(Client client, Scheme scheme)throws TimeExceededException{
        try{
            int row = -1;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            if(client.getHandler().getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserire riga in cui prendere dado oppure -1 per uscire: ");
                        try{
                            while(!br.ready()){
                                Thread.sleep(100);
                            }
                            line = br.readLine();
                        } catch (InterruptedException e){
                            return -1;
                        }
                        row = Integer.parseInt(line);
                        if(row==-1){
                            return -1;
                        }
                        if(row>=0&&row<scheme.getScheme()[0].length){
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
                return row;
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e ){}
        return -1;
    }
    /**
     * @param client player's client
     * @param scheme player's scheme
     * @return : the selected column position of the scheme
     * @author Marco Premi
     *
     */
    public static int selectPositionColumn(Client client, Scheme scheme)throws TimeExceededException{
        int column = -1;
        Scanner br = new Scanner(System.in);
            if(client.getHandler().getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserire colonna in cui prendere dado oppure -1 per uscire: ");
                        column = Integer.parseInt(br.nextLine());
                        while(column<-1||column>=scheme.getScheme()[0].length){
                            System.out.println("Inserire colonna in cui prendere dado oppure -1 per uscire: ");
                            column = Integer.parseInt(br.nextLine());
                        }
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Input non valido, riprova");
                    }
                }while (true);

            else {
                throw new TimeExceededException();
            }
        if(client.getHandler().getMainController().isMyTurn()){
            return column;
        }else {
            throw new TimeExceededException();
        }


    }
    /**
     * @param client player's client
     * @param scheme player's scheme
     * @return : -1 if the row input is't valid or the player wants to exit
     * @author Marco Premi
     *
     */
    public static  int insertPositionRow(Client client, Scheme scheme)throws TimeExceededException{
        try{
            int row = -1;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            if(client.getHandler().getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserire riga in cui inserire dado oppure -1 per uscire: ");
                        try{
                            while(!br.ready()){
                                Thread.sleep(100);
                            }
                            line = br.readLine();
                        } catch (InterruptedException e){
                            return -1;
                        }
                        row = Integer.parseInt(line);
                        if(row==-1){
                            return -1;
                        }
                        if(row>=0&&row<scheme.getScheme().length){
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
                return row;
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e ){}
        return -1;
    }
    /**
     * @param client player's client
     * @param scheme player's scheme
     * @return : -1 if the column input is't valid or the player wants to exit
     * @author Marco Premi
     *
     */
    public static int insertPositionColumn(Client client, Scheme scheme)throws TimeExceededException{
        try{
            int column = -1;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            if(client.getHandler().getMainController().isMyTurn())
                do{
                    try {
                        System.out.println("Inserire colonna in cui inserire dado oppure -1 per uscire: ");
                        try{
                            while(!br.ready()){
                                Thread.sleep(100);
                            }
                            line = br.readLine();
                        } catch (InterruptedException e){
                            return -1;
                        }
                        column = Integer.parseInt(line);
                        if(column==-1){
                            return -1;
                        }
                        if(column>=0&&column<scheme.getScheme()[0].length){
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
                return column;
            }else {
                throw new TimeExceededException();
            }
        }catch (IOException e ){}
        return -1;
}}
