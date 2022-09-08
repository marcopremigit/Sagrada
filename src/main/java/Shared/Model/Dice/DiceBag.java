package Shared.Model.Dice;

import Shared.Exceptions.IllegalColorException;
import Shared.Color;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {
    private ArrayList<Dice> dices;
    private int dicesLeft;


    /**
     * @author Fabrizio Siciliano
     * */
    public DiceBag(){
        this.dices = new ArrayList<>();
        for(int i=0; i<90; i++){
            try {
                if (i < 18) {
                    dices.add(new Dice(Color.GREEN));
                }
                if (i >= 18 && i < 36) {
                    dices.add(new Dice(Color.BLUE));
                }
                if (i >= 36 && i < 54) {
                    dices.add(new Dice(Color.RED));
                }
                if (i >= 54 && i < 72) {
                    dices.add(new Dice(Color.PURPLE));
                }
                if (i >= 72) {
                    dices.add(new Dice(Color.YELLOW));
                }
            }
            catch(IllegalColorException e){/*Do nothing, will never be used*/}
            this.dicesLeft ++;
        }
    }

    /**
     * @return Dices in the bag
     * @author Fabrizio Siciliano
     * */
    public ArrayList<Dice> getDices() {
        return dices;
    }

    /**
     * @return number of dices left in the bag
     * @author Fabrizio Siciliano
     * */
    public int getDicesLeft() {
        return this.dicesLeft;
    }

    /**
     * @return a single dice extracted randomly and removes it from the bag
     * @author Fabrizio Siciliano
     * */
    public Dice extract(){
        Dice extracted;
        Random random = new Random();
        int index = random.nextInt(this.dicesLeft);
        extracted = this.dices.get(index);
        this.dices.remove(index);
        this.dicesLeft--;
        return extracted;
    }


    /**
     * @param n how many dices to extract randomly
     * @see #extract()
     * @author Hussnain Saeed
     * */
    public ArrayList<Dice> extractNDices(int n){
        ArrayList<Dice> dices = new ArrayList<>();
        for(int i = 0; i<n; i++){
            dices.add(extract());
        }
        return  dices;
    }


    /**
     * @return visual rappresentation of the bag
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Dice dado : dices){
            builder.append(dado.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * @param toAdd is the dice to add
     * @author Fabrizio Siciliano
     * */
    public void addDice(Dice toAdd){
        dices.add(toAdd);
        dicesLeft++;
    }
}
