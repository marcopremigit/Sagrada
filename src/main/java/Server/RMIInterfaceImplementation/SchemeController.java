package Server.RMIInterfaceImplementation;

import Server.SchemeCardsHandler.SchemeCardMapper;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Player;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Server.Lobby;
import Server.MainServer;
import Shared.RMIInterface.SchemeInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SchemeController extends UnicastRemoteObject implements SchemeInterface {
    public SchemeController() throws RemoteException {

    }

    @Override
    public SchemeCard getSchemeCard() throws NullPointerException{
        SchemeCard schemeCard = MainServer.getLobby().getGame().getSchemeCards().get(0);
        MainServer.getLobby().getGame().getSchemeCards().remove(0);
        return schemeCard;
    }

    @Override
    public void setScheme(String name, String schemeName) {
        Lobby lobby = MainServer.getLobby();
        ArrayList<SchemeCard> schemeCards = lobby.getGame().getSchemeCardDeck();
        Scheme scheme=null;
        for (SchemeCard schemeCard : schemeCards) {
            if(schemeCard.getFront().getName().equals(schemeName)){
                scheme = schemeCard.getFront();
                break;
            }
            if(schemeCard.getRear().getName().equals(schemeName)){
                scheme = schemeCard.getRear();
                break;
            }

        }
        for (Player player : lobby.getPlayers()) {
            if (player.getName().equals(name)) {
                player.setScheme(scheme);
                player.setFavours(scheme.getFavors());
                break;
            }
        }
    }

    @Override
    public Scheme getScheme(String name){
        Scheme scheme = null;
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name)) {
                scheme = player.getScheme();
                break;
            }
        }
        return scheme;
    }

    @Override
    public int getPlayerFavours(String name){
        int favours = 0;
        Lobby lobby = MainServer.getLobby();
        for(Player player : lobby.getPlayers()){
            if(player.getName().equals(name)){
                favours =  player.getFavours();
                break;
            }

        }
        return favours;
    }

    @Override
    public void saveCardOnServer(String[] names, String[] favors, String[] scheme1, String[] scheme2, String[] col, String[] rows) throws CannotSaveCardException {
        //split each string in two parts
        String[] scheme1Numbers = new String[scheme1.length];
        String[] scheme1Colors = new String[scheme1.length];
        String[] scheme2Numbers = new String[scheme2.length];
        String[] scheme2Colors = new String[scheme2.length];

        for(int i=0; i<scheme1.length; i++){
            scheme1Colors[i] = scheme1[i].charAt(0) + "";
            scheme1Numbers[i] = scheme1[i].charAt(1) + "";
        }

        for(int i=0; i<scheme2.length; i++){
            scheme2Colors[i] = scheme2[i].charAt(0) + "";
            scheme2Numbers[i] = scheme2[i].charAt(1) + "";
        }

        SchemeCardMapper.getCardMapper().saveNewCard(names[0], favors[0], scheme1Numbers, scheme1Colors, names[1], favors[1], scheme2Numbers, scheme2Colors, col, rows);
    }
}
