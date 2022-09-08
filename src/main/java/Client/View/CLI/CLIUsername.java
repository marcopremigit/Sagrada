package Client.View.CLI;

import Client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIUsername extends CLI implements CLIInterface {

    private Client client;

    public CLIUsername(Client client){
        this.client = client;
    }

    /**
     * shows username insertion state
     * @author Fabrizio Siciliano
     * */
    public void showCLI() {
        System.out.println(ansi().eraseScreen().fg(getOptionColor()));
        System.out.println("****************************************************************************");
        System.out.println("*                                                                          *");
        System.out.println("*                      IMPOSTA IL TUO NOME UTENTE                          *");
        System.out.println("*                                                                          *");
        System.out.println("****************************************************************************\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String username;
        while(true) {
            try {
                username = br.readLine();
                System.out.println(ansi().fg(getAcceptedColor()).a("Hai scelto come username: ").fg(getDefaultColor()).a(username));
                client.getHandler().getMainController().insertUsername(username);
                if(!client.getHandler().getSetupController().login())
                    System.out.println(ansi().fg(getWarningColor()).a("Non va bene questo username, inseriscine un altro").fg(getDefaultColor()));
                else
                    break;
            } catch (IOException e) {
                System.err.println(ansi().fg(getWarningColor()).a("Non riesco a leggere il tuo nome, potresti ripetermelo?").fg(getDefaultColor()));
            }
        }
        System.out.println(ansi().fg(getAcceptedColor()).a("Login andato a buon fine").fg(getDefaultColor()));
    }
}
