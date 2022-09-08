package Server.ServerSocket.SocketCommandHandlers;

import Server.MainServer;
import Server.RMIInterfaceImplementation.ToolController;
import Server.ServerSocket.SocketClient;
import Shared.Exceptions.*;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Tools.ToolCard;
import org.fusesource.jansi.internal.Kernel32;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ToolCommandHandler {
    private SocketClient.SyncedOutput out;
    private ToolController toolController;
    public ToolCommandHandler(SocketClient.SyncedOutput out){
        this.toolController = MainServer.getToolController();
        this.out = out;
    }

    public void getFavors(String name){
        out.println("favours:" + name + ":" + toolController.getFavours(name));
    }

    public void getToolCards(){
        ArrayList<ToolCard> tools = toolController.getToolCards();
        JSONArray array = new JSONArray();
        for(ToolCard t : tools){
            array.put(ToolCard.fromCardToJSON(t));
        }
        JSONObject obj = new JSONObject();
        obj.put("tools", array);
        out.println(obj.toString());
    }
    public void removeDiceFromDraftPool(String dicePos){
        toolController.removeDiceFromDraftpool(Integer.parseInt(dicePos));
    }

    public void normalMove(String scheme, String dice, String x, String y){
        try {
            toolController.normalMove(Scheme.fromJSONToScheme(new JSONObject(scheme)), Dice.fromJSONToDice(new JSONObject(dice)), Integer.parseInt(x), Integer.parseInt(y));
            out.println("normalmove&true");
        } catch (CannotPlaceDiceException c){
            out.println("normalmove&CannotPlaceDiceException");
        }
    }

    public void useAlesatorePerLaminaDiRame(String scheme, String oldX, String oldY, String newX, String newY){
       try{
           toolController.useAlesatorePerLaminaDiRame(Scheme.fromJSONToScheme(new JSONObject(scheme)), Integer.parseInt(oldX), Integer.parseInt(oldY), Integer.parseInt(newX), Integer.parseInt(newY));
            out.println("alesatoreperlaminadirame&true");
       }
       catch (WrongInputException w){
           out.println("alesatoreperlaminadirame&WrongInputException");
       }
       catch (CannotPlaceDiceException c){
           out.println("alesatoreperlaminadirame&CannotPlaceDiceException");
       }
    }

    public void useDiluentePerPastaSaldaSwitch(String dicePosition, String name){
        toolController.useDiluentePerPastaSaldaSwitch(Integer.parseInt(dicePosition), name);
        out.println("alesatoreperpastasaldaswitch&true");
    }

    public void useDiluentePerPastaSaldaSetValue(String dice, String value){
        toolController.useDiluentePerPastaSaldaSetValue(Dice.fromJSONToDice(new JSONObject(dice)), Integer.parseInt(value));
        out.println("diluenteperpastasaldasetvalue&true");
    }

    public void useDiluentePerPastaSaldaPlace(String scheme, String dice, String x, String y){
        try{
            toolController.useDiluentePerPastaSaldaPlace(Scheme.fromJSONToScheme(new JSONObject(scheme)), Dice.fromJSONToDice(new JSONObject(dice)), Integer.parseInt(x),Integer.parseInt(y));
            out.println("diluenteperpastasaldaplace&true");
        }catch (CannotPlaceDiceException c){
            out.println("diluenteperpastasaldaplace&CannotPlaceDiceException");
        }
    }

    public void useLathekin(String scheme, String  firstDiceX, String firstDiceY, String firstX, String firstY, String secondDiceX, String secondDiceY, String secondX, String secondY){
        try {
            toolController.useLathekin(Scheme.fromJSONToScheme(new JSONObject(scheme)), Integer.parseInt(firstDiceX), Integer.parseInt(firstDiceY), Integer.parseInt(firstX), Integer.parseInt(firstY), Integer.parseInt(secondDiceX), Integer.parseInt(secondDiceY), Integer.parseInt(secondX), Integer.parseInt(secondY));
            out.println("lathekin&true");
        }catch (CannotPlaceDiceException e){
            out.println("lathekin&CannotPlaceDiceException");
        }
    }

    public void useMartelletto(String firstTurn, String name){
        try{
            toolController.useMartelletto(Boolean.valueOf(firstTurn), name);
            out.println("martelletto&true");
        }catch (CannotUseCardException c){
            out.println("martelletto&CannotUseCardException");
        }
    }

    public void usePennelloPerEglomise(String scheme, String oldX, String oldY, String newX, String newY){
        try{
            toolController.usePennelloPerEglomise(Scheme.fromJSONToScheme(new JSONObject(scheme)), Integer.parseInt(oldX), Integer.parseInt(oldY), Integer.parseInt(newX), Integer.parseInt(newY));
            out.println("pennellopereglomise&true");
        }
        catch (WrongInputException w){
            out.println("pennellopereglomise&WrongInputException");
        }
        catch (CannotPlaceDiceException c){
            out.println("pennellopereglomise&CannotPlaceDiceException");
        }
    }

    public void usePennelloPerPastaSaldaRoll(String dice, String name){
        toolController.usePennelloPerPastaSaldaRoll(Dice.fromJSONToDice(new JSONObject(dice)), name);
        out.println("pennelloperpastasaldaroll&true");
    }

    public void usePennelloPerPastaSaldaPlace(String scheme, String dice, String x, String y){
        try{
            toolController.useDiluentePerPastaSaldaPlace(Scheme.fromJSONToScheme(new JSONObject(scheme)), Dice.fromJSONToDice(new JSONObject(dice)), Integer.parseInt(x), Integer.parseInt(y));
            out.println("pennelloperpastasaldaplace&true");
        }catch (CannotPlaceDiceException e){
            out.println("pennelloperpastasaldaplace&CannotPlaceDiceException");
        }
    }

    public void usePinzaSgrossatrice(String chosenDice, String increase, String name){
        try{
            toolController.usePinzaSgrossatrice(Dice.fromJSONToDice(new JSONObject(chosenDice)), Boolean.valueOf(increase),name);
            out.println("pinzasgrossatrice&true");
        }catch (WrongInputException w){
            out.println("pinzasgrossatrice&WrongInputException");
        }
    }
    public void useRigaInSughero(String scheme, String chosenDice, String x, String y){
        try{
            toolController.useRigaInSughero(Scheme.fromJSONToScheme(new JSONObject(scheme)), Dice.fromJSONToDice(new JSONObject(chosenDice)), Integer.parseInt(x), Integer.parseInt(y));
            out.println("rigainsughero&true");
        }
        catch (WrongInputException w){
            out.println("rigainsughero&WrongInputException");
        }
        catch (CannotPlaceDiceException c){
            out.println("rigainsughero&CannotPlaceDiceException");
        }
    }
    public void useTaglierinaCircolare(String fromDraftPool, String round, String posInRound, String name){
        try{
            toolController.useTaglierinaCircolare(Dice.fromJSONToDice(new JSONObject(fromDraftPool)), Integer.parseInt(round), Integer.parseInt(posInRound), name);
            out.println("taglierinacircolare&true");
        }catch (IllegalRoundException i){
            out.println("taglierinacircolare&IllegalRoundException");
        }
    }
    public void useTaglierinaManuale(String scheme, String firstOldX, String firstOldY, String firstNewX, String firstNewY, String secondOldX, String secondOldY, String secondNewX, String secondNewY){
        try{
            toolController.useTaglierinaManuale(Scheme.fromJSONToScheme(new JSONObject(scheme)), Integer.parseInt(firstOldX), Integer.parseInt(firstOldY), Integer.parseInt(firstNewX), Integer.parseInt(firstNewY), Integer.parseInt(secondOldX), Integer.parseInt(secondOldY), Integer.parseInt(secondNewX), Integer.parseInt(secondNewY));
            out.println("taglierinamanuale&true");
        }
        catch (WrongInputException w){
            out.println("taglierinamanuale&WrongInputException");
        }
        catch (CannotPlaceDiceException c){
            out.println("taglierinamanuale&CannotPlaceDiceException");
        }
    }
    public void useTamponeDiamantato(String dice, String name){
        toolController.useTamponeDiamantato(Dice.fromJSONToDice(new JSONObject(dice)), name);
        out.println("tamponediamantato&true");
    }
    public void useTenagliaARotelle(String scheme, String dice, String x, String y){
        try{
            toolController.useTenagliaARotelle(Scheme.fromJSONToScheme(new JSONObject(scheme)), Dice.fromJSONToDice(new JSONObject(dice)), Integer.parseInt(x), Integer.parseInt(y));
            out.println("tenagliaarotelle&true");
        }catch (CannotPlaceDiceException c){
            out.println("tenagliaarotelle&CannotPlaceDiceException");
        }
    }
}
