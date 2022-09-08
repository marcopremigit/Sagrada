package Client.Connection.SocketClient;

import Client.Client;
import Client.Connection.ToolClientControllerInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.CannotUseCardException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Tools.ToolCard;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class SocketToolController implements ToolClientControllerInterface{
    private static final String FAVORS = "getfavors";
    private static final String TOOLCARDS = "gettools";
    private static final String NORMALMOVE = "movedice";
    private static final String ALESATOREPERLAMINADIRAME = "alesatoreperlaminadirame";
    private static final String DILUENTEPERPASTASALDASWITCH = "diluenteperpastasaldaswitch";
    private static final String DILUENTEPERPASTASALDASETVALUE = "diluenteperpastasaldasetvalue";
    private static final String DILUENTEPERPASTASALDAPLACE = "diluenteperpastasaldaplace";
    private static final String LATHEKIN = "lathekin";
    private static final String MARTELLETTO = "martelletto";
    private static final String PENNELLOPEREGLOMISE = "pennellopereglomise";
    private static final String PENNELLOPERPASTASALDAROLL = "pennelloperpastasaldaroll";
    private static final String PENNELLOPERPASTASALDPLACE = "pennelloperpastasaldaplace";
    private static final String PINZASGROSSATRICE = "pinzasgrossatrice";
    private static final String RIGAINSUGHERO = "rigainsughero";
    private static final String TAGLIERINACIRCOLARE = "taglierinacircolare";
    private static final String TAGLIERINAMANUALE = "taglierinamanuale";
    private static final String TAMPONEDIAMANTATO = "tamponediamanto";
    private static final String TENAGLIAAROTELLE = "tenagliaarotelle";
    private static final String REMOVEDICEDRAFTPOOL = "removedicedraftpool";


    private ClientBuffer buffer;
    private String regex;
    private PrintWriter print;

    public SocketToolController(ClientBuffer buffer, String regex, PrintWriter print){
        this.regex = regex;
        this.buffer = buffer;
        this.print=print;
    }

    @Override
    public int getFavours(String name) {
        print.println(FAVORS + regex + name);
        print.flush();
        String val = null;
        while(val==null){
            val = buffer.checkInBuffer("favours:"+name);
        }
        return Integer.valueOf((Arrays.asList(val.split(":"))).get(2));
    }

    @Override
    public ArrayList<ToolCard> getToolCards() {
        print.println(TOOLCARDS);
        print.flush();
        String val = null;
        while(val==null){
            val = buffer.checkInBuffer("{\"tools\"");
        }
        ArrayList<ToolCard> tools = new ArrayList<>();
        JSONObject obj = new JSONObject(val);
        for(Object object : obj.getJSONArray("tools")){
            tools.add(ToolCard.fromJSONToCard((JSONObject) object));
        }
        return tools;
    }

    @Override
    public boolean getToolCardUsed(String id) {
        for(ToolCard t : getToolCards()){
            if(t.getName().equals(id))
                return t.isUsed();
        }
        return false;
    }

    @Override
    public void setUsed(String name, String idCard) {

    }

    @Override
    public void setTenagliaARotelleUsed(String name, boolean used) {

    }

    @Override
    public void removeDiceFromDraftPool(int dicePos){
        print.println(REMOVEDICEDRAFTPOOL + regex + dicePos);
    }
    @Override
    public void normalMove(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        print.println(NORMALMOVE + regex + Scheme.fromSchemeToJSON(scheme).toString() + regex + Dice.fromDiceToJSON(dice).toString()+ regex + x + regex + y);
        String result = null;
        while(result == null){
            result = buffer.checkInBuffer("normalmove");
        }
        if(result.endsWith("CannotPlaceDiceException"))
            throw new CannotPlaceDiceException("Non sono riuscito a piazzare il dado");
    }

    @Override
    public void useAlesatorePerLaminaDiRame(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException {
        print.println(ALESATOREPERLAMINADIRAME + regex + Scheme.fromSchemeToJSON(scheme).toString() + regex + oldX + regex + oldY + regex + newX + regex +newY);
        String result = null;
        while(result == null){
            result = buffer.checkInBuffer("alesatoreperlaminadirame");
        }
        if(result.endsWith("WrongInputException"))
            throw new WrongInputException("Inpute sbagliato");
        if(result.endsWith("CannotPlaceDiceException"))
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
    }

    @Override
    public void useDiluentePerPastaSaldaSwitch(int dicePosition, String name) {
        print.println(DILUENTEPERPASTASALDASWITCH + regex + dicePosition + regex + name);
    }

    @Override
    public void useDiluentePerPastaSaldaSetValue(Dice dice, int value) {
        print.println(DILUENTEPERPASTASALDASETVALUE + regex + Dice.fromDiceToJSON(dice) + regex + value);
    }

    @Override
    public void useDiluentePerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        print.println(DILUENTEPERPASTASALDAPLACE + regex + Scheme.fromSchemeToJSON(scheme) + regex + Dice.fromDiceToJSON(dice)+ regex + x + regex + y);
        String result = null;
        while (result == null){
            result = buffer.checkInBuffer("diluenteperpastasaldaplace");
        }
        if (result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
        }
    }

    @Override
    public void useLathekin(Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws CannotPlaceDiceException {
        print.println(LATHEKIN + regex + Scheme.fromSchemeToJSON(scheme) + regex + firstDiceX + regex + firstDiceY + regex + firstX + regex +firstY + regex + secondDiceX +regex + secondDiceY +regex +secondX +regex +secondY);
        String result = null;
        while (result == null){
            result = buffer.checkInBuffer("lathekin");
        }
        if (result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
        }
    }

    @Override
    public void useMartelletto(boolean firstTurn, String name) throws CannotUseCardException {
        print.println(MARTELLETTO + regex + firstTurn +regex + name);
        String result = null;
        while (result == null){
            result = buffer.checkInBuffer("martelletto");
        }
        if (result.endsWith("CannotUseCardException")){
            throw new CannotUseCardException("Non posso usare la carta");
        }
    }

    @Override
    public void usePennelloPerEglomise(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException {
        print.println(PENNELLOPEREGLOMISE + regex + Scheme.fromSchemeToJSON(scheme) + regex + oldX +regex + oldY +regex+newX+regex+newY);
        String result = null;
        while(result == null){
            result = buffer.checkInBuffer("pennellopereglomise");
        }
        if (result.endsWith("WrongInputException")){
            throw new WrongInputException("Input sbagliato");
        }
        if(result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non puoi piazzare il dado");
        }
    }

    @Override
    public void usePennelloPerPastaSaldaRoll(Dice dice, String name) {
        print.println(PENNELLOPERPASTASALDAROLL +regex+Dice.fromDiceToJSON(dice)+regex+name);

    }

    @Override
    public void usePennelloPerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        print.println(PENNELLOPERPASTASALDPLACE +regex+Scheme.fromSchemeToJSON(scheme)+regex+Dice.fromDiceToJSON(dice)+regex+x+regex+y);
        String result = null;
        while(result == null){
            result = buffer.checkInBuffer("pennelloperpastasaldaplace");
        }
        if(result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
        }
    }

    @Override
    public void usePinzaSgrossatrice(Dice chosenDice, boolean increase, String name) throws WrongInputException {
        print.println(PINZASGROSSATRICE + regex + Dice.fromDiceToJSON(chosenDice)+regex+increase+regex+name);
        String result = null;
        while(result == null){
            result = buffer.checkInBuffer("pinzasgrossatrice");
        }
        if(result.endsWith("WrongInputException")){
            throw new WrongInputException("Input sbagliato");
        }
    }

    @Override
    public void useRigaInSughero(Scheme scheme, Dice chosenDice, int x, int y) throws WrongInputException, CannotPlaceDiceException {
        print.println(RIGAINSUGHERO +regex+ Scheme.fromSchemeToJSON(scheme)+regex+Dice.fromDiceToJSON(chosenDice)+regex+x+regex+y);
        String result = null;
        while(result==null){
            result = buffer.checkInBuffer("rigainsughero");
        }
        if(result.endsWith("WrongInputException")){
            throw new WrongInputException("Input sbagliato");
        }
        if(result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
        }
    }

    @Override
    public void useTaglierinaCircolare(Dice fromDraftPool, int round, int posInRound, String name) throws IllegalRoundException {
        print.println(TAGLIERINACIRCOLARE+regex+Dice.fromDiceToJSON(fromDraftPool)+regex+round+regex+posInRound+regex+name);
        String result = null;
        while(result == null){
            result = buffer.checkInBuffer("taglierinacircolare");
        }
        if(result.endsWith("IllegalRoundException")){
            throw new IllegalRoundException("Hai sbagliato a inserire il dado");
        }
    }

    @Override
    public void useTaglierinaManuale(Scheme scheme, int firstOldX, int firstOldY, int firstNewX, int firstNewY, int secondOldX, int secondOldY, int secondNewX, int secondNewY) throws IllegalRoundException, WrongInputException, CannotPlaceDiceException {
        print.println(TAGLIERINAMANUALE+regex+Scheme.fromSchemeToJSON(scheme)+regex+firstOldX+regex+firstOldY+regex+firstNewX+regex+firstNewY+regex+secondOldX+regex+secondOldY+regex+secondNewX+regex+secondNewY);
        String result = null;
        while(result==null){
            result=buffer.checkInBuffer("taglierinamanuale");
        }
        if(result.endsWith("IllegalRoundException")){
            throw new IllegalRoundException("Hai inserito il round sbagliato");
        }
        if(result.endsWith("WrongInputException")){
            throw new WrongInputException("Input sbagliato");
        }
        if(result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
        }
    }

    @Override
    public void useTamponeDiamantato(Dice dice, String name) {
        print.println(TAMPONEDIAMANTATO + regex + Dice.fromDiceToJSON(dice)+regex+name);
    }

    @Override
    public void useTenagliaARotelle(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        print.println(TENAGLIAAROTELLE + regex + Scheme.fromSchemeToJSON(scheme)+regex+Dice.fromDiceToJSON(dice)+regex+x+regex+y);
        String result = null;
        while(result==null){
            result = buffer.checkInBuffer("tenagliaarotelle");
        }
        if(result.endsWith("CannotPlaceDiceException")){
            throw new CannotPlaceDiceException("Non posso piazzare il dado");
        }

    }
}
