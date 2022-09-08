package Client.View.CLI;


import Client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

public class CLISettingsMenu extends CLI {
    private Client client;

    CLISettingsMenu(Client client){
        this.client = client;
    }

    private static final int OPTION1 = 1;
    private static final int OPTION2 = 2;

    /**
     * shows settings setup
     * @author Fabrizio Siciliano
     * */
    void showCLI(){
        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                           MENU' IMPOSTAZIONI                             *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");

        //show options
        System.out.println(ansi().fg(getOptionColor()).a(OPTION1 + ") ").fg(getDefaultColor()).a("Crea uno schema nuovo"));


        System.out.println(ansi().fg(getOptionColor()).a(OPTION2 + ") ").fg(getDefaultColor()).a("Torna alla schermata principale"));

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                int chosenOption = Integer.parseInt(br.readLine());
                switch (chosenOption){
                    case OPTION1:
                        CLICustomCardsCreation customCardsCreation = new CLICustomCardsCreation(client);
                        customCardsCreation.createNewScheme();
                        break;
                    case OPTION2:
                        return;
                    default:
                        System.out.println(ansi().fg(getWarningColor()).a("Non è un'opzione valida").fg(getDefaultColor()));
                        break;
                }

                if(chosenOption == OPTION1)
                    break;
            }
        }catch (IOException i){  }
    }
}
