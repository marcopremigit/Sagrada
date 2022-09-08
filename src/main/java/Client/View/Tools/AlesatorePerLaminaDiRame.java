package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;

public class AlesatorePerLaminaDiRame implements ToolCardStrategyInterface {
    private Client client;

    private int alesatorePerLaminaDiRameX1;
    private int alesatorePerLaminaDiRameY1;

    /**
     * AlesatorePerLaminaDiRame constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public AlesatorePerLaminaDiRame(Client client){
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
        try{
            if(client.getHandler().getMainController().isSinglePlayer()){
                if(client.getHandler().getMainController().isUsingCLI()){
                    isOk = CheckSingleToolsCLI.checkTools(client, "Alesatore per lamina di rame", "R");
                }else{
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                    GUIStaticMethods.changeButtonDraftPool("dfAlesatore", client);
                }
            }else {
                if ((client.getHandler().getToolController().getToolCardUsed("Alesatore per lamina di rame") && client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()) >= 2) || (!client.getHandler().getToolController().getToolCardUsed("Alesatore per lamina di rame") && client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()) >= 1)) {
                    isOk = true;
                }
            }
            if(isOk){
                if(!client.getHandler().getMainController().isUsingCLI()){
                    if(!client.getHandler().getMainController().isSinglePlayer()){
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
                        GUIStaticMethods.changeButtonGrid("alesatorePerLaminaDiRame1", client);
                    }
                }else{
                    try{

                        CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        int X1 = CLIScheme.selectPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
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
                            client.getHandler().getToolController().useAlesatorePerLaminaDiRame(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()),X1, Y1, X2, Y2);
                            return true;
                        }else {
                            return false;
                        }

                    }catch (WrongInputException | CannotPlaceDiceException e){
                        return false;
                    }
                }
            }
            else{
                if(client.getHandler().getMainController().isUsingCLI()){
                    System.out.println("Non puoi usare la carta");
                }
                if(!client.getHandler().getMainController().isUsingCLI()){
                    if(!client.getHandler().getMainController().isSinglePlayer()) {
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
                    }
                }
            }
        }catch (TimeExceededException e){
            return false;
        }
     return false;
    }

    /**
     * @param X position of the dice on the scheme
     * @param Y position of the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setAlesatorePerLaminaDiRame1(int X, int Y){
        alesatorePerLaminaDiRameY1 = Y;
        alesatorePerLaminaDiRameX1 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove spostare il dado");
        GUIStaticMethods.changeButtonGrid("alesatorePerLaminaDiRame2", client);
    }

    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setAlesatorePerLaminaDiRame2 (int X, int Y){
        try {
            client.getHandler().getToolController().useAlesatorePerLaminaDiRame(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), alesatorePerLaminaDiRameX1, alesatorePerLaminaDiRameY1, X, Y);
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        } catch (WrongInputException | CannotPlaceDiceException e) {
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
    public void setDraftPoolPositionAlesatore(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("R")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da prendere");
            GUIStaticMethods.changeButtonGrid("alesatorePerLaminaDiRame1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
