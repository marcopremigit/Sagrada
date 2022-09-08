package Server.RMIInterfaceImplementation;

import Server.*;
import Server.ToolCards.*;
import Shared.Exceptions.*;
import Shared.RMIInterface.ToolControllerInterface;

import Shared.Model.Dice.Dice;
import Shared.Player;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Tools.ToolCard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ToolController extends UnicastRemoteObject implements ToolControllerInterface {

    public ToolController() throws RemoteException {

    }

    @Override
    public void setTenagliaARotelleUsed(String name, boolean used){
        for(Player player: MainServer.getLobby().getPlayers()){
            if(player.getName().equals(name)){
                player.setTenagliaARotelleUsed(used);
            }
        }
    }
    @Override
    public int getFavours(String name) {
        for(Player player: MainServer.getLobby().getPlayers()){
            if(player.getName().equals(name)){
                return player.getFavours();
            }
        }
        return -1;
    }

    @Override
    public void removeDiceFromDraftpool(int dicePos){
        MainServer.getLobby().getGame().getDraftPool().remove(dicePos);
    }

    @Override
    public void normalMove(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getScheme().getName().equals(scheme.getName())){
                try{
                    player.setScheme(PlaceDiceRestrictions.placeDice(scheme, dice, x, y));
                    ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
                    for (int i=0; i<draftPool.size();i++) {
                        if(draftPool.get(i).equals(dice)){
                            MainServer.getLobby().getGame().getDraftPool().remove(i);
                            break;
                        }
                    }
                }catch (CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    throw new CannotPlaceDiceException("Errore nel piazzamento");
                }
                break;
            }
        }

    }
    @Override
    public void useTool(String name, String toolName){
        Lobby lobby = MainServer.getLobby();
        for(ToolCard toolCard : lobby.getGame().getTools()){
            if(toolCard.getName().equals(toolName))
                toolCard.useCard();

        }
        if(toolName.equals("Tenaglia a Rotelle")) {
            for (Player player : lobby.getPlayers()) {
                if (player.getName().equals(name))
                    player.setTenagliaARotelleUsed(true);
            }
        }
    }

    @Override
    public ArrayList<ToolCard> getToolCards() {
        return MainServer.getLobby().getGame().getTools();
    }
    @Override
    public void useAlesatorePerLaminaDiRame(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException {
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Alesatore per lamina di rame")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            toolCard.useCard();
                            break;
                        }
                    }
                    player.setScheme(AlesatorePerLaminaDiRame.changeDicePosition(scheme, oldX, oldY, newX, newY));

                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if (e instanceof WrongInputException){
                        throw new WrongInputException("Input sbagliato");
                    }
                    if(e instanceof CannotPlaceDiceException    ){
                        throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                    }
                    break;
                }
            }
        }
    }
    @Override
    public void useDiluentePerPastaSaldaSwitch(int dicePosition, String playerName){
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getName().equals(playerName)){
                for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                    if (toolCard.getName().equals("Diluente per Pasta Salda")) {
                        if (toolCard.isUsed()){
                            player.setFavours(player.getFavours()-2);
                        }else{
                            player.setFavours(player.getFavours()-1);
                        }
                        toolCard.useCard();
                        break;
                    }
                }
                break;
            }
        }
        DiluentePerPastaSalda.switchDice(MainServer.getLobby().getGame().getDiceBag(), MainServer.getLobby().getGame().getDraftPool(),dicePosition);

    }
    @Override
    public void useDiluentePerPastaSaldaSetValue(Dice dice, int value){
        try{
            for(Dice selectedDice : MainServer.getLobby().getGame().getDraftPool()){
                if(selectedDice.equals(dice)){
                    DiluentePerPastaSalda.changeDiceTop(selectedDice, value);
                }
            }
        }catch (WrongInputException e){
        }

    }
    @Override
    public void useDiluentePerPastaSaldaPlace(Scheme scheme, Dice dice, int x, int y)throws CannotPlaceDiceException{
        try{
            normalMove(scheme, dice, x, y);
        }catch (CannotPlaceDiceException e){
            throw new CannotPlaceDiceException("Impossibile piazzare dado");
        }

    }
    @Override
    public void useLathekin(Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Lathekin")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            toolCard.useCard();
                            break;
                        }
                    }
                    player.setScheme(Lathekin.moveDices(scheme, firstDiceX, firstDiceY, firstX, firstY, secondDiceX, secondDiceY, secondX, secondY));
                }catch (CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    throw new CannotPlaceDiceException("Impossibile piazzare i dadi");
                }
                break;
            }
        }

    }
    @Override
    public void useMartelletto(boolean firstTurn, String playerName) throws CannotUseCardException {
        try{
            for(Player player : MainServer.getLobby().getPlayers()){
                if(player.getName().equals(playerName)){
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Martelletto")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            Martelletto.reRollDices(MainServer.getLobby().getGame().getDraftPool(), firstTurn);
        }catch (CannotUseCardException e){
            throw new CannotUseCardException("");
        }
    }
    @Override
    public void usePennelloPerEglomise(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Pennello per Eglomise")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            toolCard.useCard();
                            break;
                        }
                    }
                    player.setScheme(PennelloPerEglomise.changeDicePosition(scheme, oldX, oldY, newX, newY));
                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if(e instanceof CannotPlaceDiceException){
                        throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                    }
                    if(e instanceof WrongInputException){
                        throw new WrongInputException("Input sbagliato");
                    }
                    break;
                }
            }
        }

    }
    @Override
    public void usePennelloPerPastaSaldaRoll(Dice dice, String playerName){
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getName().equals(playerName)){
                for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                    if (toolCard.getName().equals("Pennello per pasta salda")) {
                        if (toolCard.isUsed()){
                            player.setFavours(player.getFavours()-2);
                        }else{
                            player.setFavours(player.getFavours()-1);
                        }
                        toolCard.useCard();
                        break;
                    }
                }
                break;
            }
        }
        for (Dice diceSelected : MainServer.getLobby().getGame().getDraftPool()){
            if(diceSelected.equals(dice)){
                PennelloPerPastaSalda.reRollDice(diceSelected);
                break;
            }
        }

    }
    @Override
    public void usePennelloPerPastaSaldaPlace(Scheme scheme,Dice dice, int x, int y)throws CannotPlaceDiceException{
        normalMove(scheme,dice,x,y);
    }
    @Override
    public void usePinzaSgrossatrice(Dice chosenDice, boolean increase, String playerName)throws WrongInputException{
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getName().equals(playerName)){
                for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                    if (toolCard.getName().equals("Pinza Sgrossatrice")) {
                        if (toolCard.isUsed()){
                            player.setFavours(player.getFavours()-2);
                        }else{
                            player.setFavours(player.getFavours()-1);
                        }
                        toolCard.useCard();
                        break;
                    }
                }
                break;
            }
        }
        ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
        for (int i=0; i<draftPool.size();i++) {
            if(draftPool.get(i).equals(chosenDice)){
                PinzaSgrossatrice.pickDiceTool(draftPool.get(i), increase);
                break;
            }
        }


    }
    @Override
    public void useRigaInSughero(Scheme scheme, Dice chosenDice, int x, int y) throws WrongInputException, CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getScheme().getName().equals(scheme.getName())){
                try {
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Riga in Sughero")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            toolCard.useCard();
                            break;
                        }
                    }
                    player.setScheme(RigaInSughero.setDice(scheme, chosenDice, x, y));
                    ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
                    for (int i=0; i<draftPool.size();i++) {
                        if(draftPool.get(i).equals(chosenDice)){
                            MainServer.getLobby().getGame().getDraftPool().remove(i);
                            break;
                        }
                    }
                    break;
                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if(e instanceof CannotPlaceDiceException){
                        throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                    }
                    if(e instanceof WrongInputException){
                        throw new WrongInputException("Input sbagliato");
                    }
                }

            }
        }

    }
    @Override
    public void useTaglierinaCircolare(Dice fromDraftPool, int round, int posInRound, String playerName) throws IllegalRoundException {
        ArrayList<Dice> draftPool = MainServer.getLobby().getGame().getDraftPool();
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getName().equals(playerName)){
                for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                    if (toolCard.getName().equals("Taglierina circolare")) {
                        if (toolCard.isUsed()){
                            player.setFavours(player.getFavours()-2);
                        }else{
                            player.setFavours(player.getFavours()-1);
                        }
                        toolCard.useCard();
                        break;
                    }
                }
                break;
            }
        }
        for(int i=0; i<draftPool.size();i++){
            if(draftPool.get(i).equals(fromDraftPool)){
                TaglierinaCircolare.switchDices(MainServer.getLobby().getGame().getDraftPool(), MainServer.getLobby().getGame().getTrace(),draftPool.get(i), round, posInRound);
                break;
            }
        }

    }
    @Override
    public void useTaglierinaManuale(Scheme scheme, int firstOldX, int firstOldY, int firstNewX, int firstNewY, int secondOldX, int secondOldY, int secondNewX, int secondNewY) throws WrongInputException, CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Taglierina Manuale")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            toolCard.useCard();
                        }
                    }
                    player.setScheme(TaglierinaManuale.moveDices(MainServer.getLobby().getGame().getTrace(),scheme, firstOldX, firstOldY, firstNewX, firstNewY, secondOldX, secondOldY, secondNewX, secondNewY));
                }catch (WrongInputException|CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    if(e instanceof WrongInputException){
                        throw new WrongInputException("");
                    }
                    if(e instanceof CannotPlaceDiceException){
                        throw new CannotPlaceDiceException("");
                    }
                    break;
                }
            }
        }

    }
    @Override
    public void useTamponeDiamantato(Dice dice, String playerName){
        for(Player player : MainServer.getLobby().getPlayers()){
            if(player.getName().equals(playerName)){
                for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                    if (toolCard.getName().equals("Tampone Diamantato")) {
                        if (toolCard.isUsed()){
                            player.setFavours(player.getFavours()-2);
                        }else{
                            player.setFavours(player.getFavours()-1);
                        }
                        toolCard.useCard();
                        break;
                    }
                }
                break;
            }
        }
        ArrayList<Dice> draftPool= MainServer.getLobby().getGame().getDraftPool();
        for (int i=0; i<draftPool.size();i++) {
            if(draftPool.get(i).equals(dice)){
                TamponeDiamantato.pickAndChangeTop(draftPool.get(i));
                break;
            }
        }

    }
    @Override
    public void useTenagliaARotelle(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException{
        for(Player player : MainServer.getLobby().getPlayers()) {
            if (player.getScheme().getName().equals(scheme.getName())) {
                try{
                    for (ToolCard toolCard : MainServer.getLobby().getGame().getTools()) {
                        if (toolCard.getName().equals("Tenaglia a Rotelle")) {
                            if (toolCard.isUsed()){
                                player.setFavours(player.getFavours()-2);
                            }else{
                                player.setFavours(player.getFavours()-1);
                            }
                            toolCard.useCard();
                        }
                    }
                    player.setScheme(TenagliaARotelle.placeDice(scheme, dice, x, y));
                }catch (CannotPlaceDiceException e){
                    player.setScheme(scheme);
                    throw new CannotPlaceDiceException("Impossibile piazzare il dado");
                }
                break;
            }
        }

    }
}
