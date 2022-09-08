package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.GUI.GUIStaticMethods;
import Shared.Exceptions.TimeExceededException;

public class CheckSingleToolsCLI {

    /**
     *It checks if the player can use the tool in CLI
     * @param client the player client
     * @param  toolCardName name of the used card
     * @author Marco Premi
     * @return true if card can be used
     */
    public static boolean checkTools(Client client, String toolCardName, String color){
        try{
            if(!client.getHandler().getToolController().getToolCardUsed(toolCardName)){
                System.out.println("Che dado scegli per utilizzare la carta");
                int pos = CLIDraftPool.selectIndexDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(),client);
                if(client.getHandler().getTableController().getDraftPool().get(pos).getColor().toString().equals(color)){
                    client.getHandler().getToolController().removeDiceFromDraftPool(pos);
                    return true;
                }else{
                    System.out.println("Selezione errata");
                    return false;
                }
            }else{
                return false;
            }
        }catch (TimeExceededException t){}
        return false;
    }


}
