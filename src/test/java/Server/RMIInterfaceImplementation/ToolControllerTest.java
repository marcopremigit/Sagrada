package Server.RMIInterfaceImplementation;

import Client.Main;
import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Shared.Color;
import Shared.Exceptions.*;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.RoundTrace.RoundTraceCell;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import Shared.Model.Tools.ToolCard;
import Shared.Model.Tools.ToolDeck;
import Shared.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ToolControllerTest {
    ToolController toolController;
    Player player;
    Player player2;
    ArrayList<ToolCard> tools;
    Scheme scheme;
    Scheme scheme2;
    ArrayList<Dice> draftPool;
    @Before
    public void setUp(){
        try {
            toolController = new ToolController();
        }
        catch(RemoteException e){}
        SchemeCell[][] schemeCell = new SchemeCell[4][5];
        try {
            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCell[i][j]=new SchemeCell();
                }
            }
            schemeCell[0][3].setDado(new Dice(Color.GREEN));
            schemeCell[0][3].getDado().setTop(6);
            schemeCell[1][3].setDado(new Dice(Color.YELLOW));
            schemeCell[1][3].getDado().setTop(4);
            schemeCell[2][2].setDado(new Dice(Color.GREEN));
            schemeCell[2][2].getDado().setTop(5);
            schemeCell[2][1].setDado(new Dice(Color.BLUE));
            schemeCell[2][1].getDado().setTop(3);
        }
        catch (IllegalColorException e){/*never reached*/}
        scheme = new Scheme("test",5,schemeCell);
        scheme2 = new Scheme("test2",3,schemeCell);
        player = new Player("player");
        player2 = new Player("player2");
        player.setFavours(5);
        player2.setFavours(3);
        player.setScheme(scheme);
        player2.setScheme(scheme2);
        ArrayList<Player> players = new ArrayList<>();
        players.add(0,player2);
        players.add(1, player);
        MainServer.setLobby(new Lobby("partitaTest"));
        MainServer.getLobby().setPlayers(players);
        Game game = new Game();
        tools = new ArrayList<>();
        ToolDeck toolDeck = new ToolDeck();
        toolDeck.shuffle();
        for(int i = 0; i < 12; i++){
            tools.add(toolDeck.pick());
        }
        try {
            draftPool = new ArrayList<>();
            draftPool.add(0,new Dice(Color.GREEN));
            draftPool.get(0).setTop(2);
            game.setTools(tools);
            DiceBag diceBag = new DiceBag();
            Dice dice1 = new Dice(Color.GREEN);
            dice1.setTop(6);
            ArrayList<Dice> list = new ArrayList<>();
            list.add(dice1);
            game.setRoundTrace(list,0);
            game.setDraftPool(draftPool);
            game.setDiceBag(diceBag);
            MainServer.getLobby().setGame(game);

        }catch (IllegalColorException i){}
    }

    /**
     * check if we set correctly TenagliaARotelleUsed
     * @author Abu Hussnain Saeed
     */
    @Test
    public void setTenagliaARotelleUsed(){
        toolController.setTenagliaARotelleUsed(player.getName(),true);
        Assert.assertEquals(true,player.isTenagliaARotelleUsed());

        toolController.setTenagliaARotelleUsed(player.getName(),false);
        Assert.assertEquals(false,player.isTenagliaARotelleUsed());

    }

    /**
     * check if we get correctly the player favours
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getFavours(){
        Assert.assertEquals(5,toolController.getFavours(player.getName()));
        Assert.assertEquals(-1,toolController.getFavours("nessuno"));
    }

    /**
     * check if set correctly to true isUsed to the selected tool
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useTool(){
        for (ToolCard toolCard : tools ) {
            if(toolCard.getName().equals("Tenaglia a Rotelle")) {
                toolController.useTool(player.getName(),toolCard.getName());
                Assert.assertEquals(true, toolCard.isUsed());
                Assert.assertEquals(true, player.isTenagliaARotelleUsed());
                break;
            }
        }
    }

    /**
     * check if we get correctly the list of tools from the server
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getToolCards(){
        Assert.assertEquals(tools,toolController.getToolCards());
    }

    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useAlesatorePerLaminaDiRame(){

        //first test
        try{
            toolController.useAlesatorePerLaminaDiRame(scheme,1,3,1,2);
            Assert.assertEquals(Color.YELLOW,scheme.getScheme()[1][2].getDado().getColor());
            Assert.assertEquals(4,scheme.getScheme()[1][2].getDado().getTop());
            Assert.assertNull(scheme.getScheme()[1][3].getDado());
        }
        catch (WrongInputException w){}
        catch (CannotPlaceDiceException c){}

        //second test
        try{
            toolController.useAlesatorePerLaminaDiRame(scheme,1,3,1,2);
        }
        catch (WrongInputException w){
            Assert.assertEquals(scheme, MainServer.getLobby().getPlayers().get(1).getScheme());
        }
        catch (CannotPlaceDiceException c){}

        //third test
        try{
            toolController.useAlesatorePerLaminaDiRame(scheme,1,2,0,0);
        }
        catch (WrongInputException w){
            }
        catch (CannotPlaceDiceException c){
            Assert.assertEquals(scheme, MainServer.getLobby().getPlayers().get(1).getScheme());

        }

        //fourth test
        try{
            toolController.useAlesatorePerLaminaDiRame(scheme,2,1,2,3);
            Assert.assertEquals(Color.BLUE,scheme.getScheme()[2][3].getDado().getColor());
            Assert.assertEquals(3,scheme.getScheme()[2][3].getDado().getTop());
            Assert.assertNull(scheme.getScheme()[2][1].getDado());
        }
        catch (WrongInputException w){}
        catch (CannotPlaceDiceException c){}

    }

    /**
     * check the if we switch correctly between draftPool and diceBag
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useDiluentePerPastaSaldaSwitch(){
        //first test
        toolController.useDiluentePerPastaSaldaSwitch(0,player.getName());
        Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());

        //second test
        toolController.useDiluentePerPastaSaldaSwitch(0,player.getName());
        Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());

    }

    /**
     * check if we change correctly the top of the dice selected when we use this tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useDiluentePerPastaSaldaSetValue(){
        //firs test
        toolController.useDiluentePerPastaSaldaSetValue(MainServer.getLobby().getGame().getDraftPool().get(0),3);
        Assert.assertEquals(3,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
        Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getDraftPool().get(0).getColor());

        //second test
        toolController.useDiluentePerPastaSaldaSetValue(MainServer.getLobby().getGame().getDraftPool().get(0), 8);
        Assert.assertEquals(3,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
        Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getDraftPool().get(0).getColor());


    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useDiluentePerPastaSaldaPlace() {
        //first test
        try {
            toolController.useDiluentePerPastaSaldaPlace(scheme, MainServer.getLobby().getGame().getDraftPool().get(0), 0, 0);
        }
        catch(CannotPlaceDiceException c){
            Assert.assertNull(scheme.getScheme()[0][0].getDado());
        }

        //second test
        try {
            toolController.useDiluentePerPastaSaldaPlace(scheme, MainServer.getLobby().getGame().getDraftPool().get(0), 3, 1);
            Assert.assertEquals(Color.GREEN,scheme.getScheme()[3][1].getDado().getColor());
            Assert.assertEquals(2,scheme.getScheme()[3][1].getDado().getTop());
        }
        catch(CannotPlaceDiceException c){}

    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useLathekin(){
        //first test
        try {
            toolController.useLathekin(scheme, 2, 1, 0, 0, 2, 2, 0, 0);
        }catch (CannotPlaceDiceException c){
            Assert.assertEquals(Color.BLUE,scheme.getScheme()[2][1].getDado().getColor());
            Assert.assertEquals(3,scheme.getScheme()[2][1].getDado().getTop());
            Assert.assertEquals(Color.GREEN, scheme.getScheme()[2][2].getDado().getColor());
            Assert.assertEquals(5, scheme.getScheme()[2][2].getDado().getTop());
        }

        //second test
        try {
            toolController.useLathekin(scheme, 2, 1, 2, 3, 2, 2, 1, 4);
            Assert.assertEquals(Color.BLUE,scheme.getScheme()[2][3].getDado().getColor());
            Assert.assertEquals(3,scheme.getScheme()[2][3].getDado().getTop());
            Assert.assertEquals(Color.GREEN, scheme.getScheme()[1][4].getDado().getColor());
            Assert.assertEquals(5, scheme.getScheme()[1][4].getDado().getTop());

        }catch (CannotPlaceDiceException c){}


    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed, Marco Premi
     */
    @Test
    public void useMartelletto(){
        //first test
        try {
            toolController.useMartelletto(true, player.getName());
        }
        catch (CannotUseCardException c){
            Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getDraftPool().get(0).getColor());
            Assert.assertEquals(2,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
        }

        //second test
        try{
            toolController.useMartelletto(false, player.getName());
            Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());
        }
        catch (CannotUseCardException c){}

        //third test
        try{
            toolController.useTool(player.getName(), "Martelletto");
            toolController.useMartelletto(false, player.getName());
            Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());
        }catch (CannotUseCardException e){}
    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void usePennelloPerEglomise(){
        //first test
        try{
            toolController.usePennelloPerEglomise(scheme,0,0,1,1);
        }catch (WrongInputException w){
            Assert.assertEquals(scheme, MainServer.getLobby().getPlayers().get(1).getScheme());
        }
        catch (CannotPlaceDiceException c){}

        //second test
        try{
            toolController.usePennelloPerEglomise(scheme,2,1,0,0);
        }catch (WrongInputException w){ }
        catch (CannotPlaceDiceException c){
            Assert.assertEquals(Color.BLUE,scheme.getScheme()[2][1].getDado().getColor());
            Assert.assertEquals(3,scheme.getScheme()[2][1].getDado().getTop());
            Assert.assertNull(scheme.getScheme()[0][0].getDado());
        }

        //third test
        try{
            toolController.usePennelloPerEglomise(scheme,2,1,2,3);
            Assert.assertEquals(Color.BLUE,scheme.getScheme()[2][3].getDado().getColor());
            Assert.assertEquals(3,scheme.getScheme()[2][3].getDado().getTop());
            Assert.assertNull(scheme.getScheme()[2][1].getDado());

        }
        catch (WrongInputException w){}
        catch (CannotPlaceDiceException c){}

    }
    /**
     * check if we roll correctly the dice selected when we use this tool
     * @author Abu Hussnain Saeed
     */
    @Test
    public void usePennelloPerPastaSaldaRoll(){
        toolController.usePennelloPerPastaSaldaRoll(MainServer.getLobby().getGame().getDraftPool().get(0),player.getName());
        toolController.usePennelloPerPastaSaldaRoll(MainServer.getLobby().getGame().getDraftPool().get(0),player.getName());
        Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());
    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void usePennelloPerPastaSaldaPlace(){
        try {
            toolController.usePennelloPerPastaSaldaPlace(scheme, MainServer.getLobby().getGame().getDraftPool().get(0), 3, 1);
            Assert.assertEquals(Color.GREEN, scheme.getScheme()[3][1].getDado().getColor());
            Assert.assertEquals(2,scheme.getScheme()[3][1].getDado().getTop());
        }
        catch (CannotPlaceDiceException c){}

    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed, Marco Premi
     */
    @Test
    public void usePinzaSgrossatrice(){
        //first test
        try {
            toolController.usePinzaSgrossatrice(MainServer.getLobby().getGame().getDraftPool().get(0), true, player.getName());
            Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getDraftPool().get(0).getColor());
            Assert.assertEquals(3,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
        }
        catch (WrongInputException w){}

        //second test
        try {
            toolController.useTool(player.getName(), "Pinza Sgrossatrice");
            toolController.usePinzaSgrossatrice(MainServer.getLobby().getGame().getDraftPool().get(0), true, player.getName());
            Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getDraftPool().get(0).getColor());
        }
        catch (WrongInputException w){}

    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useRigaInSughero(){
        //first test
        try{
            toolController.useRigaInSughero(scheme,MainServer.getLobby().getGame().getDraftPool().get(0),2,1);
        }
        catch (WrongInputException w){
            Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());
            Assert.assertEquals(Color.BLUE,scheme.getScheme()[2][1].getDado().getColor());
            Assert.assertEquals(3,scheme.getScheme()[2][1].getDado().getTop());
        }
        catch (CannotPlaceDiceException c){}

        //second test
        try{
            toolController.useRigaInSughero(scheme,MainServer.getLobby().getGame().getDraftPool().get(0),2,3);
        }
        catch (WrongInputException w){ }
        catch (CannotPlaceDiceException c){
            Assert.assertEquals(1,MainServer.getLobby().getGame().getDraftPool().size());
            Assert.assertNull(scheme.getScheme()[2][3].getDado());
        }

        //third test
        try{
            toolController.useRigaInSughero(scheme,MainServer.getLobby().getGame().getDraftPool().get(0),0,0);
            Assert.assertEquals(0,MainServer.getLobby().getGame().getDraftPool().size());
            Assert.assertEquals(Color.GREEN,scheme.getScheme()[0][0].getDado().getColor());
            Assert.assertEquals(2,scheme.getScheme()[0][0].getDado().getTop());

        }
        catch (WrongInputException w){}
        catch (CannotPlaceDiceException c){}



    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useTaglierinaCircolare(){
        try{
            Dice choose = MainServer.getLobby().getGame().getDraftPool().get(0);
            //first test
            toolController.useTaglierinaCircolare(choose,0,0,player.getName());
            Assert.assertEquals(choose,MainServer.getLobby().getGame().getTrace().getPool(0).get(0));
            Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getDraftPool().get(0).getColor());
            Assert.assertEquals(6,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
            Assert.assertEquals(4,player.getFavours());

            //second test
            toolController.useTaglierinaCircolare(MainServer.getLobby().getGame().getDraftPool().get(0),0,0,player.getName());
            Assert.assertEquals(2,player.getFavours());
            Assert.assertEquals(choose,MainServer.getLobby().getGame().getDraftPool().get(0));
            Assert.assertEquals(Color.GREEN,MainServer.getLobby().getGame().getTrace().getPool(0).get(0).getColor());
            Assert.assertEquals(6,MainServer.getLobby().getGame().getTrace().getPool(0).get(0).getTop());
        }catch (IllegalRoundException i){}
    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useTaglierinaManuale() {
        //first test
        try {
            toolController.useTaglierinaManuale(scheme, 2, 1, 0, 0, 1, 3, 1, 1);
        } catch (WrongInputException w) {
            Assert.assertEquals(Color.BLUE, scheme.getScheme()[2][1].getDado().getColor());
            Assert.assertEquals(3, scheme.getScheme()[2][1].getDado().getTop());
            Assert.assertEquals(Color.YELLOW, scheme.getScheme()[1][3].getDado().getColor());
            Assert.assertEquals(4, scheme.getScheme()[1][3].getDado().getTop());
        } catch (CannotPlaceDiceException c) {
        }

        //second test
        try {
            toolController.useTaglierinaManuale(scheme, 2, 2, 0, 0, 0, 3, 3, 4);
        } catch (WrongInputException w) {
        } catch (CannotPlaceDiceException c) {

            Assert.assertEquals(Color.GREEN, scheme.getScheme()[2][2].getDado().getColor());
            Assert.assertEquals(5, scheme.getScheme()[2][2].getDado().getTop());
            Assert.assertEquals(Color.GREEN, scheme.getScheme()[0][3].getDado().getColor());
            Assert.assertEquals(6, scheme.getScheme()[0][3].getDado().getTop());
        }

        //third test
        try {
            toolController.useTaglierinaManuale(scheme, 2, 2, 2, 3, 0, 3, 1, 4);
            Assert.assertEquals(Color.GREEN, scheme.getScheme()[2][3].getDado().getColor());
            Assert.assertEquals(5, scheme.getScheme()[2][3].getDado().getTop());
            Assert.assertEquals(Color.GREEN, scheme.getScheme()[1][4].getDado().getColor());
            Assert.assertEquals(6, scheme.getScheme()[1][4].getDado().getTop());
            Assert.assertNull(scheme.getScheme()[2][2].getDado());
            Assert.assertNull(scheme.getScheme()[0][3].getDado());
        } catch (WrongInputException w) {
        } catch (CannotPlaceDiceException c) {

        }
    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useTamponeDiamantato(){

        Dice dice = MainServer.getLobby().getGame().getDraftPool().get(0);
        //first test
        toolController.useTamponeDiamantato(dice,player.getName());
        Assert.assertEquals(5,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
        Assert.assertEquals(dice.getColor(),MainServer.getLobby().getGame().getDraftPool().get(0).getColor());
        Assert.assertEquals(4,player.getFavours());

        //second test
        toolController.useTamponeDiamantato(dice,player.getName());
        Assert.assertEquals(2,MainServer.getLobby().getGame().getDraftPool().get(0).getTop());
        Assert.assertEquals(dice.getColor(),MainServer.getLobby().getGame().getDraftPool().get(0).getColor());
        Assert.assertEquals(2,player.getFavours());


    }
    /**
     * check the correct function of tool card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void useTenagliaARotelle(){
        Dice dice = MainServer.getLobby().getGame().getDraftPool().get(0);
        //first test
        try{
            toolController.useTenagliaARotelle(scheme,dice,0,0);
        }catch (CannotPlaceDiceException c){
            Assert.assertNull(scheme.getScheme()[0][0].getDado());
            Assert.assertEquals(4,player.getFavours());
        }

        //second test
        try{
            toolController.useTenagliaARotelle(scheme,dice,3,1);
            Assert.assertEquals(dice,scheme.getScheme()[3][1].getDado());
            Assert.assertEquals(2,player.getFavours());
        }catch (CannotPlaceDiceException c){ }
    }

    @Test
    public void removeDiceFromDraftpool(){
        toolController.removeDiceFromDraftpool(0);
        Assert.assertTrue(draftPool.size()==0);
    }

}

