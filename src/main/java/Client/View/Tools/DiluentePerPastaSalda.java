package Client.View.Tools;

import Client.Client;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.CLI.CLIHUDItems.CLIScheme;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIStaticMethods;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.TimeExceededException;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static Client.View.CLI.CLI.getAnsiColor;
import static Client.View.CLI.CLI.getDiceUnicode;
import static org.fusesource.jansi.Ansi.ansi;

public class DiluentePerPastaSalda implements ToolCardStrategyInterface {
    private Client client;

    private int diluentePerPastaSalda;
    /**
     * DiluentePerPastaSalda constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public DiluentePerPastaSalda(Client client){
        this.client = client;
    }
    /**
     *Use DiluentePerPastaSalda card with GUI or CLI
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
                    isOk = CheckSingleToolsCLI.checkTools(client, "Diluente per Pasta Salda", "P");
                }else{
                    if(!dicePlaced){
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                        GUIStaticMethods.changeButtonDraftPool("dfDiluente", client);
                    }else{
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
                    }
                }
            }else {
                if (((client.getHandler().getToolController().getToolCardUsed("Diluente per Pasta Salda")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Diluente per Pasta Salda")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1))) {
                    isOk = true;
                }
            }
            if(!dicePlaced&&isOk){
                if(!client.getHandler().getMainController().isUsingCLI()){
                    if(!client.getHandler().getMainController().isSinglePlayer()) {
                        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado dalla riserva da prendere");
                        GUIStaticMethods.changeButtonDraftPool("diluentePerPastaSalda", client);
                    }
                }else{
                   try{
                       Scanner br = new Scanner(System.in);
                       int index = CLIDraftPool.selectIndexDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(), client);
                       client.getHandler().getToolController().useDiluentePerPastaSaldaSwitch(index,client.getHandler().getMainController().getPlayerName());
                       System.out.println(ansi().fg(Ansi.Color.BLUE).a(" ").fg(getAnsiColor(client.getHandler().getTableController().getDraftPool().get(index).getColor())).a(getDiceUnicode(client.getHandler().getTableController().getDraftPool().get(index).getTop())).fg(Ansi.Color.DEFAULT));
                       System.out.println("Selezionare il valore del dado: valora tra 1 e 6");
                       int value = -1;
                       value = br.nextInt();
                       while (value<1||value>6){
                           System.out.println("Valore inserito non valido, inserire nuovo valore");
                           value=br.nextInt();
                       }
                       client.getHandler().getToolController().useDiluentePerPastaSaldaSetValue(client.getHandler().getTableController().getDraftPool().get(index), value);
                       System.out.println(ansi().fg(Ansi.Color.BLUE).a(" ").fg(getAnsiColor(client.getHandler().getTableController().getDraftPool().get(index).getColor())).a(getDiceUnicode(client.getHandler().getTableController().getDraftPool().get(index).getTop())).fg(Ansi.Color.DEFAULT));
                       CLIScheme.showScheme(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));

                       int X = CLIScheme.insertPositionRow(client, client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                       if(X==-1){
                           return false;
                       }
                       int Y = CLIScheme.insertPositionColumn(client,client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
                       if(Y==-1){
                           return false;
                       }
                       if(client.getHandler().getMainController().isMyTurn()){
                           client.getHandler().getToolController().useDiluentePerPastaSaldaPlace(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()), client.getHandler().getTableController().getDraftPool().get(index), X, Y);
                           return true;
                       }else {
                           return false;
                       }
                   }catch (CannotPlaceDiceException e){
                       System.out.println("Impossibile piazzare il dado");
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
            return true;
        }catch(TimeExceededException e){
            return false;
        }

    }
    /**
     * @param val position of the dice on the draftpool
     * @author Marco Premi
     */
    public void setDiluentePerPastaSalda(String val) {
        diluentePerPastaSalda = Integer.parseInt(val.substring(val.length()-1));
        client.getHandler().getToolController().useDiluentePerPastaSaldaSwitch(diluentePerPastaSalda,client.getHandler().getMainController().getPlayerName());
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIAlertBox.setDiceValue(client);

    }
    /**
     * @param value the new value of the new dice
     * @author Marco Premi
     */
    public void setDiluentePerPastaSalda2(int value){
        client.getHandler().getToolController().useDiluentePerPastaSaldaSetValue(client.getHandler().getTableController().getDraftPool().get(diluentePerPastaSalda),value);
        client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegliere dove mettere dado sulla vetrata");
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIStaticMethods.changeButtonGrid("diluentePerPastaSalda2", client);

    }
    /**
     * @param x position where the player wants to place the dice on the scheme
     * @param y position where the player wants to place the dice on the scheme
     * @author Marco Premi
     *
     */
    public void setDiluentePerPastaSalda3(int x, int y){
        try{
            client.getHandler().getToolController().useDiluentePerPastaSaldaPlace(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()),client.getHandler().getTableController().getDraftPool().get(diluentePerPastaSalda), x, y );
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getButtonMoves().setDiceMoved(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, selezionare prossima mossa");

        }catch (CannotPlaceDiceException e2){
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
    public void setDfDiluente(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("P")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Seleziona il dado dalla riserva da prendere");
            GUIStaticMethods.changeButtonDraftPool("diluentePerPastaSalda", client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }

}
