package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;

public class TamponeDiamantato implements ToolCardStrategyInterface {
    private int tamponeDiamantato;
    private Client client;
    /**
     * AlesatorePerLaminaDiRame constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public TamponeDiamantato(Client client){
        this.client = client;
    }
    /**
     *Use AlesatorePerLaminaDiRame card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Tampone Diamantato", "G");
            }else{
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                GUIStaticMethods.changeButtonDraftPool("dfTampone", client);
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Tampone Diamantato")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Tampone Diamantato")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }
        if(isOk){
            if(!client.getHandler().getMainController().isUsingCLI()){
                if(!client.getHandler().getMainController().isSinglePlayer()) {

                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("tamponeDiamantato", client);
                }
            }else{
                try {
                    Dice dice = CLIDraftPool.selectDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(), client);
                    if(dice!=null){
                        if(client.getHandler().getMainController().isMyTurn()){
                            client.getHandler().getToolController().useTamponeDiamantato(dice, client.getHandler().getMainController().getPlayerName());
                            return true;
                        }else {
                            return false;
                        }
                    }
                }catch (TimeExceededException e){
                    return false;
                }
            }
        }else{
            if(client.getHandler().getMainController().isUsingCLI()){
                System.out.println("Non puoi usare la carta");
            }
            if(!client.getHandler().getMainController().isUsingCLI()){
                if(!client.getHandler().getMainController().isSinglePlayer()) {
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
                }
            }
        }

    return false;
    }
    /**
     * @param val position of the dice on the draftpool
     * @author Marco Premi
     */
    public void setTamponeDiamantato(String val){
        tamponeDiamantato = Integer.parseInt(val.substring(val.length()-1));
        client.getHandler().getToolController().useTamponeDiamantato(client.getHandler().getTableController().getDraftPool().get(tamponeDiamantato), client.getHandler().getMainController().getPlayerName());
        client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la prossima mossa");
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIStaticMethods.setButtonDraftPoolNull(client);
        GUIStaticMethods.setButtonDraftPoolNull(client);
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
    }
    private int draftPoolGUI;
    /**
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setDfTampone(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("G")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dalla riserva");
            GUIStaticMethods.changeButtonDraftPool("tamponeDiamantato", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
