package Client.View.Tools;

import Client.Client;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotUseCardException;

public class Martelletto implements ToolCardStrategyInterface {
    private Client client;
    /**
     * Martelletto constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public Martelletto(Client client){
        this.client = client;
    }
    /**
     *Use Martelletto card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        try{
            if(client.getHandler().getMainController().isSinglePlayer()){
                if(client.getHandler().getMainController().isUsingCLI()){
                    isOk = CheckSingleToolsCLI.checkTools(client, "Martelletto", "B");
                }else{
                    if(!dicePlaced){
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                        GUIStaticMethods.changeButtonDraftPool("dfMartelletto", client);
                    }else{
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
                    }

                }
            }else {
                if ((client.getHandler().getToolController().getToolCardUsed("Martelletto")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Martelletto")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                    isOk = true;
                }
            }
            if(!dicePlaced&&(isOk)) {

                if (client.getHandler().getMainController().isMyTurn()) {
                    client.getHandler().getToolController().useMartelletto(client.getHandler().getTurnController().isFirstTurn(),client.getHandler().getMainController().getPlayerName());
                    if (!client.getHandler().getMainController().isUsingCLI()) {
                        client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
                        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
                        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
                    }
                    return true;
                } else {
                    return false;
                }
            }else{
                if(client.getHandler().getMainController().isUsingCLI()){
                    System.out.println("Non puoi utilizzare la carta");
                }
                if(!client.getHandler().getMainController().isUsingCLI()){
                    if(!client.getHandler().getMainController().isSinglePlayer()) {
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
                    }                }
            }
        }catch (CannotUseCardException e){
            if(!client.getHandler().getMainController().isUsingCLI()){
                client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
            }
            return false;
        }
        return false;
    }
    private int draftPoolGUI;
    /**
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setDfMartelletto(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("B")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            GUIStaticMethods.setButtonDraftPoolNull(client);
            try{
                client.getHandler().getToolController().useMartelletto(client.getHandler().getTurnController().isFirstTurn(),client.getHandler().getMainController().getPlayerName());
            }catch (CannotUseCardException c){
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
            }
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
            client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
