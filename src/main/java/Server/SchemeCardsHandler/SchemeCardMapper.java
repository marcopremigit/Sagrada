package Server.SchemeCardsHandler;

import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.Schemes.SchemeCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class  SchemeCardMapper {
    private static SchemeCardMapper instance;

    private SchemeCardHandler SCH;

    /**
     * Singleton constructor.
     * @author Fabrizio Siciliano
     * */
    private SchemeCardMapper(){
        this.SCH = new SchemeCardHandler();
    }

    /**
     * Singleton getter.
     * @author Fabrizio Siciliano
     * */
    public static SchemeCardMapper getCardMapper() {
        if(instance==null)
            instance = new SchemeCardMapper();
        return instance;
    }

    /**
     * reads all cards in CustomCardsMap.txt
     * @return list of cards from XML package
     * @author Fabrizio Siciliano,Marco Premi
     * */
    public ArrayList<SchemeCard> readAllCards(){
        InputStream in = getClass().getResourceAsStream("/CustomCards/SchemeCardMap.txt");
        ArrayList<SchemeCard> result = new ArrayList<>();
        try(BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in))){
            for (String currentSchemeFile = bufferReader.readLine(); currentSchemeFile != null; currentSchemeFile = bufferReader.readLine()) {
                result.add(SCH.readCustomCard(currentSchemeFile));
            }
            ArrayList<SchemeCard> c = SCH.readCards();
            if(c!=null)
                result.addAll(SCH.readCards());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * saves new custom card's XML
     * @param frontName first card's name to be saved
     * @param frontFavors first card's favors' value to be saved
     * @param frontCellsNumber first ordered list of numbers of cells to be saved
     * @param frontCellsColor first ordered list of colors of cells to be saved
     * @param rearName second card's name to be saved
     * @param rearFavors second card's favors' value to be saved
     * @param rearCellsNumber second ordered list of numbers of cells to be saved
     * @param rearCellsColor second ordered list of colors of cells to be saved
     * @see SchemeCardHandler#buildCustomCard(String, String, String[], String[], String, String, String[], String[], String[], String[]) (String, String, String[], String[], String, String, String[], String[], String[])
     * @author Fabrizio Siciliano, Abu Hussnain Saeed
     * */
    //checks if card doesn't already exists, if so inserts name in map and creates the new card
    public void saveNewCard(String frontName, String frontFavors, String[] frontCellsNumber, String[] frontCellsColor, String rearName, String rearFavors, String[] rearCellsNumber, String[] rearCellsColor, String[] col, String[] rows)throws CannotSaveCardException {
        SCH.buildCustomCard(frontName,frontFavors, frontCellsNumber, frontCellsColor, rearName, rearFavors, rearCellsNumber, rearCellsColor, col, rows);
    }
}
