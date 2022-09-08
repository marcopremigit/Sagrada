package Client.View.CLI.CLIHUDItems;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Client.View.CLI.CLI;

public class CLIPrivateCards extends CLI {
    /**
     * It shows the Private Card in multiplayer
     * @param privateObjective the private objective card
     * @author Marco Premi
     *
     */
    public static void showMultiPrivateCard(PrivateObjective privateObjective){
        System.out.println(privateObjective.toString());
    }
    /**
     * It shows the two Private Cards in singleplayer
     * @param privateObjective1 the first private objective card in single player
     * @param privateObjective2 the second private objective card in single player
     * @author Marco Premi
     *
     */
    public static void showSinglePrivateCard(PrivateObjective privateObjective1, PrivateObjective privateObjective2){
        System.out.println("1) " + privateObjective1.toString());
        System.out.println("2) " + privateObjective2.toString());
    }
}
