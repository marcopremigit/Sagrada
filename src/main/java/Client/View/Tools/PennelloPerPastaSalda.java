package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import org.fusesource.jansi.Ansi;

import static Client.View.CLI.CLI.getAnsiColor;
import static Client.View.CLI.CLI.getDiceUnicode;
import static org.fusesource.jansi.Ansi.ansi;

public class PennelloPerPastaSalda implements ToolCardStrategyInterface {
    private int pennelloPerPastaSalda;
    private Client client;
    /**
     * PennelloPerPastaSalda constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public PennelloPerPastaSalda(Client client){
        this.client = client;
    }

    /**
     *Use PennelloPerPastaSalda card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Pennello per pasta salda", "P");
            }else{
                if(!dicePlaced){
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                    GUIStaticMethods.changeButtonDraftPool("dfPennelloPerPastaSalda", client);
                }else{
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
                }
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Pennello per pasta salda")
                    &&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)
                    ||(!client.getHandler().getToolController().getToolCardUsed("Pennello per pasta salda")
                    &&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }
        if(!dicePlaced&&(isOk)){
                if(!client.getHandler().getMainController().isUsingCLI()){
                    if(!client.getHandler().getMainController().isSinglePlayer()) {

                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado dalla riserva");
                        GUIStaticMethods.changeButtonDraftPool("pennelloPerPastaSalda1", client);
                    }
                }else{
                    try{
                        int index = CLIDraftPool.selectIndexDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(),client);
                        client.getHandler().getToolController().usePennelloPerPastaSaldaRoll(client.getHandler().getTableController().getDraftPool().get(index),client.getHandler().getMainController().getPlayerName());
                        System.out.println(ansi().fg(Ansi.Color.BLUE).a(" ").fg(getAnsiColor(client.getHandler().getTableController().getDraftPool().get(index).getColor())).a(getDiceUnicode(client.getHandler().getTableController().getDraftPool().get(index).getTop())).fg(Ansi.Color.DEFAULT));

                    if(client.getHandler().getTableController().getDraftPool().get(index)!=null){
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
                            client.getHandler().getToolController().usePennelloPerPastaSaldaPlace(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()),client.getHandler().getTableController().getDraftPool().get(index), X, Y);
                            return true;
                        }else {
                            return false;
                        }
                    }
                }catch (TimeExceededException | CannotPlaceDiceException e){
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
    public void setPennelloPerPastaSalda1(String val){
        pennelloPerPastaSalda = Integer.parseInt(val.substring(val.length()-1));
        client.getHandler().getToolController().usePennelloPerPastaSaldaRoll(client.getHandler().getTableController().getDraftPool().get(pennelloPerPastaSalda),client.getHandler().getMainController().getPlayerName());
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIStaticMethods.changeButtonGrid("pennelloPerPastaSalda2", client);
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove mettere dado sulla vetrata");

    }

    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setPennelloPerPastaSalda2(int X, int Y){
        try{
            client.getHandler().getToolController().usePennelloPerPastaSaldaPlace(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()),client.getHandler().getTableController().getDraftPool().get(pennelloPerPastaSalda), X, Y);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, selezionare prossima mossa");
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getButtonMoves().setDiceMoved(true);
        }catch (CannotPlaceDiceException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, selezionare prossima mossa");
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
    public void setDfpennelloperpastasalda(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("P")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado dalla riserva");
            GUIStaticMethods.changeButtonDraftPool("pennelloPerPastaSalda1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
