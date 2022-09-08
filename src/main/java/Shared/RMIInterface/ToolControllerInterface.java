package Shared.RMIInterface;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.CannotUseCardException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Tools.ToolCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ToolControllerInterface extends Remote {
    /**
     * @return  ArrayList of Tool Cards
     * @author Marco Premi, Fabrizio Siciliano
     * */
    ArrayList<ToolCard> getToolCards() throws RemoteException;
    /**
     * Sets Tenaglia a Rotelle used by a selected player
     * @param name name of the selected player
     * @param used true if Tenaglia a Rotelle has been used
     * @author Marco Premi, Abu Hussnain Saeed
     * */
    void setTenagliaARotelleUsed(String name, boolean used) throws RemoteException;
    /**
     * @param name name of the selected player
     * @return  the favours of the selected player
     * @author Marco Premi, Fabrizio Siciliano
     * */
    int getFavours(String name)throws RemoteException;
    /**
     * Sets the selected tool used
     * @param name name of the selected player
     * @param toolName name of the used tool
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void useTool(String name, String toolName) throws  RemoteException;
    /**
     * @param dicePos position of the dice to remove from the draft pool
     * @return  the favours of the selected player
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void removeDiceFromDraftpool(int dicePos) throws  RemoteException;
    /**
     * Uses normal move
     * @param scheme selected scheme
     * @param dice dice to move
     * @param x  position in scheme where the player wants to place the dice
     * @param y  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @author Marco Premi
     * */
    void normalMove(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException, RemoteException;
    /**
     * Uses Alesatore per Lamina di rame
     * @param scheme selected scheme
     * @param oldX position in scheme where the player wants to take the dice
     * @param oldY position in scheme where the player wants to take the dice
     * @param newX  position in scheme where the player wants to place the dice
     * @param newY  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @throws WrongInputException if the input is wrong
     * @author Marco Premi
     * */
    void useAlesatorePerLaminaDiRame(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException, RemoteException;
    /**
     * Uses diluente per pasta salda switch
     * @param playerName player's name
     * @param dicePosition position of the dice to switch
     * @author Marco Premi
     * */
    void useDiluentePerPastaSaldaSwitch(int dicePosition, String playerName) throws RemoteException;
    /**
     * Uses diluente per pasta salda set value
     * @param dice the new extracted dice
     * @param value the new value of the new extracted dice
     * @author Marco Premi
     * */
    void useDiluentePerPastaSaldaSetValue(Dice dice, int value) throws RemoteException;
    /**
     * Uses diluente per pasta salda place
     * @param scheme selected scheme
     * @param dice dice to move
     * @param x  position in scheme where the player wants to place the dice
     * @param y  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @author Marco Premi
     * */
    void useDiluentePerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException, RemoteException;
    /**
     * Uses Lathekin
     * @param scheme selected scheme
     * @param firstDiceX position in scheme where the player wants to take the first dice
     * @param firstDiceY position in scheme where the player wants to take the first dice
     * @param firstX  position in scheme where the player wants to place the first dice
     * @param firstY  position in scheme where the player wants to place the first dice
     * @param secondDiceX position in scheme where the player wants to take the second dice
     * @param secondDiceY position in scheme where the player wants to take the second dice
     * @param secondX  position in scheme where the player wants to place the second dice
     * @param secondY  position in scheme where the player wants to place the second dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @author Marco Premi
     * */
    void useLathekin(Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws CannotPlaceDiceException, RemoteException;
    /**
     * Uses Martelletto
     * @param playerName player's name
     * @param firstTurn true if is the first turn
     * @throws CannotUseCardException if the player can't use the card
     * @author Marco Premi
     * */
    void useMartelletto(boolean firstTurn, String playerName) throws CannotUseCardException, RemoteException;
    /**
     * Uses Pennello per Eglomise
     * @param scheme selected scheme
     * @param oldX position in scheme where the player wants to take the dice
     * @param oldY position in scheme where the player wants to take the dice
     * @param newX  position in scheme where the player wants to place the dice
     * @param newY  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @throws WrongInputException if the input is wrong
     * @author Marco Premi
     * */
    void usePennelloPerEglomise(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException, RemoteException;
    /**
     * Uses diluente per pasta salda roll
     * @param name player's name
     * @param dice dice to roll
     * @author Marco Premi
     * */
    void usePennelloPerPastaSaldaRoll(Dice dice, String name) throws RemoteException;
    /**
     * Uses Pennello per pasta salda place
     * @param scheme selected scheme
     * @param dice dice to move
     * @param x  position in scheme where the player wants to place the dice
     * @param y  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @author Marco Premi
     * */
    void usePennelloPerPastaSaldaPlace(Scheme scheme,Dice dice, int x, int y) throws RemoteException,CannotPlaceDiceException;
    /**
     * Uses Pinza Sgrossatrice
     * @param chosenDice selected dice
     * @param increase true if the player wants to increase dice value, false otherwise
     * @param playerName player's name
     * @throws WrongInputException if the input is wrong
     * @author Marco Premi
     * */
    void usePinzaSgrossatrice(Dice chosenDice, boolean increase, String playerName)throws WrongInputException, RemoteException;
    /**
     * Uses Riga in sughero
     * @param scheme selected scheme
     * @param chosenDice dice to move
     * @param x  position in scheme where the player wants to place the dice
     * @param y  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @throws WrongInputException if the input is wrong
     * @author Marco Premi
     * */
    void useRigaInSughero(Scheme scheme, Dice chosenDice, int x, int y) throws WrongInputException, CannotPlaceDiceException, RemoteException;
    /**
     * Uses Taglierina Circolare
     * @param fromDraftPool selected dice
     * @param round round of the selected dice from the round trace
     * @param posInRound  position in round of the selected dice from the round trace
     * @param playerName player's name
     * @throws IllegalArgumentException if round is wrong
     * @author Marco Premi
     * */
    void useTaglierinaCircolare(Dice fromDraftPool, int round, int posInRound, String playerName) throws IllegalRoundException, RemoteException;
    /**
     * Uses Taglierina Manuale
     * @param scheme selected scheme
     * @param firstOldX position in scheme where the player wants to take the first dice
     * @param firstOldY position in scheme where the player wants to take the first dice
     * @param firstNewX  position in scheme where the player wants to place the first dice
     * @param firstNewY  position in scheme where the player wants to place the first dice
     * @param secondOldX position in scheme where the player wants to take the second dice
     * @param secondOldY position in scheme where the player wants to take the second dice
     * @param secondNewX  position in scheme where the player wants to place the second dice
     * @param secondNewY  position in scheme where the player wants to place the second dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @author Marco Premi
     * */
    void useTaglierinaManuale(Scheme scheme, int firstOldX, int firstOldY, int firstNewX, int firstNewY, int secondOldX, int secondOldY, int secondNewX, int secondNewY) throws IllegalRoundException, WrongInputException, CannotPlaceDiceException, RemoteException;
    /**
     * Uses Tampone Diamantato
     * @param dice selected dice
     * @param playerName player's name
     * @author Marco Premi
     * */
    void useTamponeDiamantato(Dice dice, String playerName) throws RemoteException;
    /**
     * Uses Tenaglia a Rotelle
     * @param scheme selected scheme
     * @param dice dice to move
     * @param x  position in scheme where the player wants to place the dice
     * @param y  position in scheme where the player wants to place the dice
     * @throws CannotPlaceDiceException if the player can't place the dice
     * @author Marco Premi
     * */
    void useTenagliaARotelle(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException, RemoteException;

}
