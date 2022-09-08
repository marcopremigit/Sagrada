package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;

public class RigaInSughero implements ToolCardStrategyInterface {
    private int rigaInSugheroDicePosition;
    private Client client;
    /**
     * RigaInSughero constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public RigaInSughero(Client client){
        this.client = client;
    }
    /**
     *Use RigaInSughero card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Riga in Sughero", "Y");
            }else{
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                GUIStaticMethods.changeButtonDraftPool("dfRiga", client);
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Riga in Sughero")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Riga in Sughero")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }
        if(!dicePlaced&&(isOk)){
            if(!client.getHandler().getMainController().isUsingCLI()){
                if(!client.getHandler().getMainController().isSinglePlayer()) {

                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado dalla riserva");
                    GUIStaticMethods.changeButtonDraftPool("rigaInSughero1", client);
                }
            }else{
                try {
                    Dice dice = CLIDraftPool.selectDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(), client);
                    if(dice!=null){
                        CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        int X = CLIScheme.insertPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        if(X==-1){
                            return false;
                        }
                        int Y = CLIScheme.insertPositionColumn(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        if(Y==-1){
                            return false;
                        }
                        if(client.getHandler().getMainController().isMyTurn()){
                            client.getHandler().getToolController().useRigaInSughero(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), dice, X, Y);
                            return true;
                        }else {
                            return false;
                        }
                    }
                }catch (WrongInputException | CannotPlaceDiceException | TimeExceededException e){
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
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setRigaInSughero1(String val){
        rigaInSugheroDicePosition = Integer.parseInt(val.substring(val.length()-1));
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove mettere dado sulla vetrata");
        GUIStaticMethods.changeButtonGrid("rigaInSughero2", client);
    }

    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setRigaInSughero2(int X, int Y){
        try {
            client.getHandler().getToolController().useRigaInSughero(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), client.getHandler().getTableController().getDraftPool().get(rigaInSugheroDicePosition), X, Y);
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getButtonMoves().setDiceMoved(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        }catch (WrongInputException|CannotPlaceDiceException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
        }
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updategrid(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIStaticMethods.setButtonFacadeNull(client);
        GUIStaticMethods.setButtonDraftPoolNull(client);
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
    }
    private int draftPoolGUI;
    /**
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setdfriga(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("Y")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado dalla riserva");
            GUIStaticMethods.changeButtonDraftPool("rigaInSughero1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }

}
