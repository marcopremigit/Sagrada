package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;

public class TaglierinaManuale implements ToolCardStrategyInterface {
    private int taglierinaManualeX1;
    private int taglierinaManualeY1;
    private int taglierinaManualeX2;
    private int taglierinaManualeY2;
    private int taglierinaManualeX3;
    private int taglierinaManualeY3;
    private Client client;
    /**
     * TaglierinaManuale constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public TaglierinaManuale(Client client){
        this.client = client;
    }
    /**
     *Use TaglierinaManuale card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    @Override
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Taglierina Manuale", "B");
            }else{
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                GUIStaticMethods.changeButtonDraftPool("dfTaglierinaManuale", client);
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Taglierina Manuale")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Taglerina Manuale")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }
        if(isOk){
            if(!client.getHandler().getMainController().isUsingCLI()){
                if(!client.getHandler().getMainController().isSinglePlayer()) {

                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il primo dado da spostare");
                    GUIStaticMethods.changeButtonGrid("taglierinaManuale1", client);
                }
            }else {
                try {
                    CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    int X1 = CLIScheme.selectPositionRow(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(X1==-1){
                        return false;
                    }
                    int Y1 = CLIScheme.selectPositionColumn(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(Y1==-1){
                        return false;
                    }
                    int X2 = CLIScheme.insertPositionRow(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
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
                    int Y4 = CLIScheme.insertPositionColumn(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                    if(Y4==-1){
                        return false;
                    }
                    if(client.getHandler().getMainController().isMyTurn()){
                        client.getHandler().getToolController().useTaglierinaManuale(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), X1, Y1, X2, Y2, X3, Y3, X4, Y4);
                        return true;
                    }else {
                        return false;
                    }
                }catch (CannotPlaceDiceException | IllegalRoundException | WrongInputException | TimeExceededException e){
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
    public void setTaglierinaManuale1 (int X, int Y){
        taglierinaManualeY1 =  Y;
        taglierinaManualeX1 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove spostare il primo dado");
        GUIStaticMethods.changeButtonGrid("taglierinaManuale2", client);
    }
    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setTaglierinaManuale2 (int X, int Y){
        taglierinaManualeY2 =  Y;
        taglierinaManualeX2 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il secondo dado da spostare");
        GUIStaticMethods.changeButtonGrid("taglierinaManuale3", client);
    }
    /**
     * @param X position of the dice on the scheme
     * @param Y position of the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setTaglierinaManuale3 (int X, int Y){
        taglierinaManualeY3 =  Y;
        taglierinaManualeX3 = X;
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona dove spostare il secondo dado");
        GUIStaticMethods.changeButtonGrid("taglierinaManuale4", client);

    }
    /**
     * @param X position where the player wants to place the dice on the scheme
     * @param Y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setTaglierinaManuale4 (int X, int Y) {
        try{
            client.getHandler().getToolController().useTaglierinaManuale(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), taglierinaManualeX1, taglierinaManualeY1, taglierinaManualeX2, taglierinaManualeY2, taglierinaManualeX3,taglierinaManualeY3, X, Y);
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        }catch (WrongInputException|CannotPlaceDiceException|IllegalRoundException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");
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
    public void setDftaglerinamanuale(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("B")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            GUIStaticMethods.setButtonDraftPoolNull(client);client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il primo dado da spostare");
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il primo dado da spostare");
            GUIStaticMethods.changeButtonGrid("taglierinaManuale1", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
