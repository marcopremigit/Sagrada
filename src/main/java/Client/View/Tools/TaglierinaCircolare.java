package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIRoundTrace;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;

public class TaglierinaCircolare implements ToolCardStrategyInterface {
    private int taglierinaCircolareX;
    private int taglierinaCircolareY;
    private int taglierinaCircolareDraftPool;
    private Client client;
    /**
     * TaglierinaCircolare constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public TaglierinaCircolare(Client client){
        this.client = client;
    }
    /**
     *Use TaglierinaCircolare card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Taglierina circolare", "G");
            }else{
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");

                GUIStaticMethods.changeButtonDraftPool("dfTaglierinaCircolare", client);
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Taglierina circolare")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Taglierina circolare")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }

        if(isOk){
            if(!client.getHandler().getMainController().isUsingCLI()){
                if(!client.getHandler().getMainController().isSinglePlayer()) {
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("taglierinaCircolare1", client);
                }
            }else{
                try {
                    Dice dice = CLIDraftPool.selectDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(), client);
                    if (dice != null) {
                        CLIRoundTrace.showRoundTrace(client.getHandler().getTableController().getRoundTrace());
                        int round = CLIRoundTrace.selectRound(client.getHandler().getTableController().getRoundTrace(), client);
                        if (round == -1) {
                            return false;
                        }
                        int index = CLIRoundTrace.selectIndex(client.getHandler().getTableController().getRoundTrace(), client, round);
                        if (index == -1) {
                            return false;
                        }
                        try {
                            if (client.getHandler().getMainController().isMyTurn()) {
                                client.getHandler().getToolController().useTaglierinaCircolare(dice, round - 1, index,client.getHandler().getMainController().getPlayerName());
                                return true;
                            } else {
                                return false;
                            }
                        } catch (IllegalRoundException e) {
                            return false;
                        }
                    }
                } catch (TimeExceededException e) {
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
    public void setTaglierinaCircolare1(String val){
        taglierinaCircolareDraftPool =Integer.parseInt(val.substring(val.length()-1));
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dal tracciato dei round");
        GUIAlertBox.showAllRounds("taglierinaCircolare2", client);
    }
    /**
     * @param val position of the dice on the roundtracce
     * @author Marco Premi
     */
    public void setTaglierinaCircolare2(String val){
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        try{
            taglierinaCircolareY=  Integer.parseInt(val.substring(val.length()-1));
            taglierinaCircolareX = Integer.parseInt(val.substring(val.length()-2,val.length()-1));
            client.getHandler().getToolController().useTaglierinaCircolare(client.getHandler().getTableController().getDraftPool().get(taglierinaCircolareDraftPool), taglierinaCircolareX, taglierinaCircolareY,client.getHandler().getMainController().getPlayerName());
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Azione completata, scegliere nuova azione");
        }catch (IllegalRoundException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Impossibile completare, scegliere nuova azione");
        }
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updategrid(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        client.getHandler().getMainController().getPlayerHUD().getRoundTrace().updateRoundTrace(client.getHandler().getTableController().getRoundTrace());
        GUIStaticMethods.setButtonFacadeNull(client);
        GUIStaticMethods.setButtonDraftPoolNull(client);
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
    }
    private int draftPoolGUI;
    /**
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setDftaglierinacircolare(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("G")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona un dado dalla riserva");
            GUIStaticMethods.changeButtonDraftPool("taglierinaCircolare1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }

}
