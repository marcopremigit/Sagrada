package Client.Connection.RMIClient;

import Client.Connection.ToolClientControllerInterface;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.CannotUseCardException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Tools.ToolCard;
import Shared.RMIInterface.ToolControllerInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIToolClientController implements ToolClientControllerInterface {
    private ToolControllerInterface toolController;

    public RMIToolClientController(ToolControllerInterface toolController){
        this.toolController = toolController;
    }

    @Override
    public ArrayList<ToolCard> getToolCards() {
        try {
            return toolController.getToolCards();
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public int getFavours(String name) {
        try {
            return toolController.getFavours(name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
            return 0;
        }
    }

    @Override
    public boolean getToolCardUsed(String id){
        try {
            for (ToolCard toolCard : toolController.getToolCards()) {
                if (toolCard.getName().equals(id)) {
                    return toolCard.isUsed();
                }
            }
        } catch (RemoteException e) {
            RMIClient.handleRMIDisconnection();
        }
        return false;
    }

    @Override
    public void setTenagliaARotelleUsed(String name, boolean used){
        try{
            toolController.setTenagliaARotelleUsed(name, used);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void setUsed(String name, String idCard){
        try {
            toolController.useTool(name, idCard);
        } catch (RemoteException e){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void removeDiceFromDraftPool(int dicePos){
        try{
            toolController.removeDiceFromDraftpool(dicePos);
        }catch (RemoteException r){
            RMIClient.handleRMIDisconnection();

        }
    }
    @Override
    public void normalMove(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        try {
            toolController.normalMove(scheme, dice, x, y);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useAlesatorePerLaminaDiRame(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException {
        try {
            toolController.useAlesatorePerLaminaDiRame(scheme, oldX, oldY, newX, newY);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useDiluentePerPastaSaldaSwitch(int dicePosition, String name) {
        try {
            toolController.useDiluentePerPastaSaldaSwitch(dicePosition, name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useDiluentePerPastaSaldaSetValue(Dice dice, int value) {
        try {
            toolController.useDiluentePerPastaSaldaSetValue(dice, value);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useDiluentePerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        try {
            toolController.usePennelloPerPastaSaldaPlace(scheme, dice, x, y);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useLathekin(Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws CannotPlaceDiceException {
        try{
        toolController.useLathekin(scheme,firstDiceX,firstDiceY,firstX,firstY,secondDiceX,secondDiceY,secondX,secondY);
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useMartelletto(boolean firstTurn, String name) throws CannotUseCardException {
        try {
            toolController.useMartelletto(firstTurn, name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void usePennelloPerEglomise(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException {
        try {
            toolController.usePennelloPerEglomise(scheme, oldX, oldY, newX, newY);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void usePennelloPerPastaSaldaRoll(Dice dice, String name) {
        try {
            toolController.usePennelloPerPastaSaldaRoll(dice, name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void usePennelloPerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        try {
            toolController.usePennelloPerPastaSaldaPlace(scheme, dice, x, y);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void usePinzaSgrossatrice(Dice chosenDice, boolean increase, String name) throws WrongInputException {
        try {
            toolController.usePinzaSgrossatrice(chosenDice, increase, name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useRigaInSughero(Scheme scheme, Dice chosenDice, int x, int y) throws WrongInputException, CannotPlaceDiceException {
        try {
            toolController.useRigaInSughero(scheme, chosenDice, x, y);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useTaglierinaCircolare(Dice fromDraftPool, int round, int posInRound, String name) throws IllegalRoundException {
        try {
            toolController.useTaglierinaCircolare(fromDraftPool, round, posInRound, name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useTaglierinaManuale(Scheme scheme, int firstOldX, int firstOldY, int firstNewX, int firstNewY, int secondOldX, int secondOldY, int secondNewX, int secondNewY) throws IllegalRoundException, WrongInputException, CannotPlaceDiceException {
        try{
        toolController.useTaglierinaManuale(scheme, firstOldX, firstOldY, firstNewX, firstNewY, secondOldX, secondOldY, secondNewX, secondNewY);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useTamponeDiamantato(Dice dice, String name) {
        try {
            toolController.useTamponeDiamantato(dice, name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void useTenagliaARotelle(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException {
        try {
            toolController.useTenagliaARotelle(scheme, dice, x, y);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }
}
