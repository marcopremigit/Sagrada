package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;

public class PennelloPerEglomise implements ToolCardStrategyInterface {
    private int pennelloPerEglomiseX1;
    private int pennelloPerEglomiseY1;
    private Client client;

    /**
     * PennelloPerEglomise constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public PennelloPerEglomise(Client client){
        this.client = client;
    }
    /**
     *Use PennelloPerEglomise card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Pennello per Eglomise", "B");
            }else{
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                GUIStaticMethods.changeButtonDraftPool("dfPennelloPerEglomise", client);
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Pennello per Eglomise")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Pennello per Eglomise")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }
        if(isOk){
            if(!client.getHandler().getMainController().isUsingCLI()){
                if(!client.getHandler().getMainController().isSinglePlayer()) {
                    if(!client.getHandler().getMainController().isSinglePlayer()) {

                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
                        GUIStaticMethods.changeButtonGrid("pennelloPerEglomise1", client);
                    }
                }
            }else{
                try{
                    CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    int X1 = CLIScheme.selectPositionRow(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(X1==-1){
                        return false;
                    }
                    int Y1 = CLIScheme.selectPositionColumn(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(Y1==-1){
                        return false;
                    }
                    int X2 = CLIScheme.insertPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(X2==-1){
                        return false;
                    }
                    int Y2 = CLIScheme.insertPositionColumn(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(Y2==-1){
                        return false;
                    }
                    if(client.getHandler().getMainController().isMyTurn()){
                        client.getHandler().getToolController().usePennelloPerEglomise(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()),X1, Y1, X2, Y2);
                        return true;
                    }else{
                        return false;
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
     * @param X position of the dice on the scheme
     * @param Y position of the dice on the scheme
     * @author Marco Premi
     *
     */

    public void setPennelloPerEglomise1(int X, int Y){
        pennelloPerEglomiseY1 = Y;
        pennelloPerEglomiseX1 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove mettere il dado sulla vetrata");
        GUIStaticMethods.changeButtonGrid("pennelloPerEglomise2", client);
    }
    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public  void setPennelloPerEglomise2 (int X, int Y) {
        try{
            client.getHandler().getToolController().usePennelloPerEglomise(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()),pennelloPerEglomiseX1,pennelloPerEglomiseY1, X, Y);
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        }catch (WrongInputException|CannotPlaceDiceException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        }
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updategrid(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
        GUIStaticMethods.setButtonFacadeNull(client);
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));

    }
    private int draftPoolGUI;
    /**
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setDfpennellopereglomise(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("B")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
            GUIStaticMethods.changeButtonGrid("pennelloPerEglomise1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
