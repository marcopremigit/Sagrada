package Client.View.CLI.CLIHUDItems;

import Shared.Model.ObjectiveCard.PublicObjective;
import Client.View.CLI.CLI;

import java.util.ArrayList;

public class CLIPublicCards extends CLI {
    /**
     * It shows the Public Cards
     * @param cards Public Cards ArrayList
     * @author Marco Premi
     *
     */
    public static void showPublicCards(ArrayList<PublicObjective> cards){
        for(int i=0; i<cards.size(); i++){
            System.out.println(cards.get(i).toString());
        }
    }
}
