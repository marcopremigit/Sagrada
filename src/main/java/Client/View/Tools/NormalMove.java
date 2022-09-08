package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Model.Dice.Dice;

public class NormalMove implements ToolCardStrategyInterface {
    private int draftpoolPosition;
    private Client client;
    /**
     * NormalMove constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public NormalMove(Client client){
        this.client = client;
    }

    /**
     *Use NormalMove with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        if(!client.getHandler().getMainController().isUsingCLI()){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado da prendere");
            GUIStaticMethods.changeButtonDraftPool("normalMove1", client);
        }else{
            try {
                CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                Dice dice = null;
                dice=CLIDraftPool.selectDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(), client);
                if(dice!=null){
                    int X = CLIScheme.insertPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(X==-1){
                        return false;
                    }
                    int Y = CLIScheme.insertPositionColumn(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(Y==-1){
                        return false;
                    }
                    if(client.getHandler().getMainController().isMyTurn()){
                        client.getHandler().getToolController().normalMove(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), dice, X, Y);
                        return true;
                    }else {
                        return false;
                    }
                }
             }catch (CannotPlaceDiceException | TimeExceededException e){
                return false;
            }

        }
        return false;
    }
    /**
     * @param val position of the dice on the draftpool
     * @author Marco Premi
     */
    public void setNormalMove1(String val){
        draftpoolPosition = Integer.parseInt(val.substring(val.length()-1));
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona la cella in cui mettere il dado");
        GUIStaticMethods.changeButtonGrid("normalMove2", client);
    }

    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setNormalMove2(int X, int Y){
        try {
            client.getHandler().getToolController().normalMove(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), client.getHandler().getTableController().getDraftPool().get(draftpoolPosition), X,Y);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Dado piazzato, seleziona prossima mossa!");
            client.getHandler().getMainController().getPlayerHUD().getButtonMoves().setDiceMoved(true);
        }catch (CannotPlaceDiceException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Impossibile completare, seleziona prossima mossa");
        }
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updategrid(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIStaticMethods.setButtonFacadeNull(client);
        GUIStaticMethods.setButtonDraftPoolNull(client);
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
    }
}
