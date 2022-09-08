package Client.View.CLI;

import Client.Client;
import Shared.Exceptions.LobbyFullException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIMainMenu extends CLI implements CLIInterface{
    private Client client;

    public CLIMainMenu(Client client){
        this.client = client;
    }

    private static final int SINGLEPLAYER = 1;
    private static final int MULTIPLAYER = 2;
    private static final int SETTINGS = 3;
    private static final int EXIT = 4;

    /**
     * shows main menu insertion status
     * @author Fabrizio Siciliano
     * */
    public void showCLI(){
        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("                    ____                            _       ");
        System.out.println("                   / ___|  __ _  __ _ _ __ __ _  __| | __ _ ");
        System.out.println("                   \\___ \\ / _` |/ _` | '__/ _` |/ _` |/ _` |");
        System.out.println("                    ___) | (_| | (_| | | | (_| | (_| | (_| |");
        System.out.println("                   |____/ \\__,_|\\__, |_|  \\__,_|\\__,_|\\__,_|");
        System.out.println("                                |___/                       ");
        System.out.println();

        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                  SCEGLI UNA VOCE DAL MENU PRINCIPALE                     *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");
        try {
            try {
                System.in.read(new byte[System.in.available()]);
            } catch (IOException e) {
                System.err.println("Errore in SystemIn read");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while(true) {
                System.out.println(ansi().fg(getOptionColor()).a(SINGLEPLAYER + ") ").fg(getDefaultColor()).a("SinglePlayer"));
                System.out.println(ansi().fg(getOptionColor()).a(MULTIPLAYER + ") ").fg(getDefaultColor()).a("MultiPlayer"));
                System.out.println(ansi().fg(getOptionColor()).a(SETTINGS + ") ").fg(getDefaultColor()).a("Settings"));
                System.out.println(ansi().fg(getOptionColor()).a(EXIT + ") ").fg(getDefaultColor()).a("Exit"));

                do {
                    line=br.readLine();
                }while (!line.equals("1") && !line.equals("2") && !line.equals("3") && !line.equals("4"));

                int optionChosen = Integer.parseInt(line);

                switch (optionChosen) {
                    case SINGLEPLAYER:
                        System.out.println(ansi().fg(getAcceptedColor()).a("Hai scelto di giocare in SinglePlayer").fg(getDefaultColor()));
                        client.getHandler().getMainController().insertSinglePlayer(true);
                        try {
                            if(!client.getHandler().getSetupController().joinGame(true)) {
                                System.out.println("Server occupato, riavvia il client più tardi!");
                                System.exit(42);
                            }
                        }catch (LobbyFullException l){
                            System.out.println(ansi().fg(getWarningColor()).a("La lobby è piena.\nChiudo il programma, ma ritenta più tardi!"));
                            System.exit(1);
                        }
                        break;
                    case MULTIPLAYER:
                        System.out.println(ansi().fg(getAcceptedColor()).a("Hai scelto di giocare in MultiPlayer").fg(getDefaultColor()));
                        client.getHandler().getMainController().insertSinglePlayer(false);
                        try {
                            if(!client.getHandler().getSetupController().joinGame(false)){
                                System.out.println("Server occupato, riavvia il client più tardi!");
                                System.exit(42);
                            }
                        } catch (LobbyFullException l){
                            System.out.println(ansi().fg(getWarningColor()).a("La lobby è piena.\nChiudo il programma, ma ritenta più tardi!"));
                            System.exit(1);
                        }
                        break;
                    case SETTINGS:
                        CLISettingsMenu settingsMenu = new CLISettingsMenu(client);
                        settingsMenu.showCLI();
                        break;
                    case EXIT:
                        System.exit(2);
                        break;
                    default:
                        System.out.println(ansi().fg(getWarningColor()).a("Non è un'opzione valida").fg(getDefaultColor()));
                        break;
                }
                if(optionChosen>=SINGLEPLAYER && optionChosen<=EXIT && optionChosen!=SETTINGS)
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
