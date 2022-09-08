package Client.View.Tools;

import Client.Client;
import Client.View.ViewControllers.ToolCardStrategyInterface;
import Shared.Exceptions.TimeExceededException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Client.View.CLI.CLIHUDItems.CLIDraftPool;
import Client.View.GUI.GUIAlertBox;
import Client.View.GUI.GUIStaticMethods;

import java.util.Scanner;

public class PinzaSgrossatrice implements ToolCardStrategyInterface {
    private Client client;
    /**
     * PinzaSgrossatrice constructor
     * @param client the player client
     * @author Marco Premi
     *
     */
    public PinzaSgrossatrice(Client client){
        this.client = client;
    }
    private int pinzaSgrossatrice;
    /**
     *Use PinzaSgrossatrice card with GUI or CLI
     * @param dicePlaced it checks if the player has already placed a dice during the turn
     * @author Marco Premi
     * @return true if card is correctly used
     */
    public boolean useToolCard(boolean dicePlaced) {
        boolean isOk = false;
        if(client.getHandler().getMainController().isSinglePlayer()){
            if(client.getHandler().getMainController().isUsingCLI()){
                isOk = CheckSingleToolsCLI.checkTools(client, "Pinza Sgrossatrice", "P");
            }else{
                client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva dello stesso colore della carta che stai utilizzando");
                GUIStaticMethods.changeButtonDraftPool("dfPinza", client);
            }
        }else {
            if ((client.getHandler().getToolController().getToolCardUsed("Pinza Sgrossatrice")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=2)||(!client.getHandler().getToolController().getToolCardUsed("Pinza Sgrossatrice")&&client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName())>=1)) {
                isOk = true;
            }
        }
        if(isOk){
            if (!client.getHandler().getMainController().isUsingCLI()) {
                if(!client.getHandler().getMainController().isSinglePlayer()) {
                    client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva");
                    GUIStaticMethods.changeButtonDraftPool("pinzaSgrossatrice1", client);
                }
            } else {
                try {
                    Dice dice = CLIDraftPool.selectDiceFromDraftpool(client.getHandler().getTableController().getDraftPool(),client);
                    if(dice!=null){
                        Scanner br = new Scanner(System.in);
                        String line = null;
                        if(client.getHandler().getMainController().isMyTurn()){
                            do{
                                try {
                                    System.out.println("Inserisci 1 per aumentare o 0 per diminuire");
                                    line = br.nextLine();
                                }catch (NumberFormatException e){
                                    System.out.println("Valore inserito errato, riprova");
                                }
                            }while (line!=null);

                        }else {
                            return false;
                        }

                        while (!line.equals("0") && !line.equals("1")) {
                            System.out.println("Valore inserito errato, riprovare");
                            if(client.getHandler().getMainController().isMyTurn()){
                                line = br.nextLine();
                            }else {
                                return false;
                            }
                        }
                        boolean increase = false;
                        if (line != null && line.equals("1")) {
                            increase = true;
                        } else {
                            if (line != null && line.equals("0")) {
                                increase = false;
                            } else {
                            }
                        }
                        if(client.getHandler().getMainController().isMyTurn()){
                            client.getHandler().getToolController().usePinzaSgrossatrice(dice, increase,client.getHandler().getMainController().getPlayerName());
                            return true;
                        }else {
                            return false;
                        }
                    }
                } catch (WrongInputException | TimeExceededException e) {
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
    public void setPinzaSgrossatrice1(String val){
        pinzaSgrossatrice = Integer.parseInt(val.substring(val.length()-1));
        GUIAlertBox.showIncreaseOrDecrease(client);
    }
    /**
     * @param increase true if the player wants to increase the value of the dice, false otherwise
     * @author Marco Premi
     */
    public void setPinzaSgrossatrice2(boolean increase){
        try {
            client.getHandler().getToolController().usePinzaSgrossatrice(client.getHandler().getTableController().getDraftPool().get(pinzaSgrossatrice),increase, client.getHandler().getMainController().getPlayerName());
            client.getHandler().getMainController().getPlayerHUD().getTools().setCardUsed(true);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa completata, seleziona la tua prossima mossa");
        }catch (WrongInputException e){
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Mossa non completata, seleziona la tua prossima mossa");

        }
        client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
        GUIStaticMethods.setButtonDraftPoolNull(client);
        client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updateFavors(client.getHandler().getToolController().getFavours(client.getHandler().getMainController().getPlayerName()));
    }
    private int draftPoolGUI;
    /**
     * @param val position of the dice on the draftpool for single player use on GUI
     * @author Marco Premi
     */
    public void setDfpinza(String val){
        draftPoolGUI = Integer.parseInt(val.substring(val.length()-1));
        if(client.getHandler().getTableController().getDraftPool().get(draftPoolGUI).getColor().toString().equals("P")){
            client.getHandler().getToolController().removeDiceFromDraftPool(draftPoolGUI);
            client.getHandler().getMainController().getPlayerHUD().getDraftPool().updateDraftPoolBox(client.getHandler().getTableController().getDraftPool());
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Scegli un dado nella riserva");
            GUIStaticMethods.changeButtonDraftPool("pinzaSgrossatrice1",client);
        }else{
            GUIStaticMethods.setButtonDraftPoolNull(client);
            client.getHandler().getMainController().getPlayerHUD().getHintsScreen().updateScreen("Non puoi usare la carta");
        }
    }
}
