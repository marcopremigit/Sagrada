package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;

public class Lathekin implements ToolCardStrategyInterface {
    private Client client;

    private int lathekinX1;
    private int lathekinY1;
    private int lathekinX2;
    private int lathekinY2;
    private int lathekinX3;
    private int lathekinY3;

    /**
     * Lathekin constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public Lathekin(Client client){
        this.client = client;
    }
    /**
     *Use Lathekin card with GUI or CLI
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
                    isOk = CheckSingleToolsCLI.checkTools(client, "Lathekin", "Y");
                }else{
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                    GUIStaticMethods.changeButtonDraftPool("dfLathekin", client);
                }
            }else {
                if ((client.getHandler().getToolController().getToolCardUsed("Lathekin")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Lathekin")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                    isOk = true;
                }
            }
            if(isOk) {
                if (!client.getHandler().getMainController().isUsingCLI()) {
                    if(!client.getHandler().getMainController().isSinglePlayer()) {
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da spostare per primo");
                        GUIStaticMethods.changeButtonGrid("lathekin1", client);
                    }
                } else {
                    try {
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
                        int X3 = CLIScheme.selectPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        if(X3==-1){
                            return false;
                        }
                        int Y3 = CLIScheme.selectPositionColumn(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        if(Y3==-1){
                            return false;
                        }
                        int X4 = CLIScheme.insertPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        if(X4==-1){
                            return false;
                        }
                        int Y4 = CLIScheme.insertPositionColumn(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                        if(Y4==-1){
                            return false;
                        }
                        if(client.getHandler().getMainController().isMyTurn()){
                            client.getHandler().getToolController().useLathekin(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), X1, Y1, X2, Y2, X3, Y3, X4, Y4);
                            return true;
                        }else {
                            return false;
                        }

                    } catch (CannotPlaceDiceException e) {
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
                    }                }
            }
        }catch (TimeExceededException e) {
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
    public void setLathekin1 (int X, int Y){
        lathekinY1 = Y;
        lathekinX1 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona la posizione dove spostare il dado");
        GUIStaticMethods.changeButtonGrid("lathekin2", client);
    }

    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setLathekin2 (int X, int Y){
        lathekinY2 =  Y;
        lathekinX2 = X;client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il secondo dado sulla vetrata da prendere");

        GUIStaticMethods.changeButtonGrid("lathekin3", client);
    }
    /**
     * @param X position of the dice on the scheme
     * @param Y position of the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setLathekin3 (int X, int Y){
        lathekinY3 =  Y;
        lathekinX3 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona la posizione dove spostare il dado");
        GUIStaticMethods.changeButtonGrid("lathekin4",client);
    }

    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setLathekin4 (int X, int Y) {
        try{
            client.getHandler().getToolController().useLathekin(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), lathekinX1,lathekinY1,lathekinX2,lathekinY2,lathekinX3, lathekinY3, X, Y);
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        }catch (CannotPlaceDiceException e){
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
    public void setDfLathekin(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("Y")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado sulla vetrata da spostare per primo");
            GUIStaticMethods.changeButtonGrid("lathekin1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
