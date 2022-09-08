package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

public class PlaceDiceRestrictions {

    public static Scheme placeDice(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException{
        if(cellIsRight(scheme,dice, x, y)){
            if(firstPlacing(scheme) && (x == 0 || x == scheme.getScheme().length || y == 0 || y == scheme.getScheme()[x].length)){
                scheme.getScheme()[x][y].setDado(dice);
            }
            else if(!firstPlacing(scheme) && aroundCheck(scheme, x, y) && checkColorOrNumber(scheme, dice, x, y)){
                scheme.getScheme()[x][y].setDado(dice);
            }
            else{

                throw new CannotPlaceDiceException("Non puoi piazzare il dado " + dice.toString() + " nella posizione " + x + " :: " + y);
            }

        }
        else {
            throw new CannotPlaceDiceException("La cella in posizione " + x + " :: " + y + " non va bene");
        }
        return scheme;
    }

    //checks if the scheme is empty
    private static boolean firstPlacing(Scheme scheme){
        for(int i=0; i<scheme.getScheme().length; i++){
            for(int j=0; j<scheme.getScheme()[i].length; j++){
                if(scheme.getScheme()[i][j].isOccupied()){
                    return false;
                }
            }
        }
        return true;
    }

    //checks if at least one nearby position to x,y is occupied
    public static boolean aroundCheck(Scheme scheme, int x, int y){
        for(int i=x-1; i<=x+1; i++){
            for(int j=y-1; j<=y+1; j++){
                if(i>=0 && i<=3){
                    if(j>=0 && j<=4){
                        if ((i != x && j != y)||i!=x||j!=y) {
                            if (scheme.getScheme()[i][j].isOccupied()) {
                                return true;
                            } else {
                                continue;
                            }
                        }
                    }
                    else{
                        continue;
                    }
                }
                else{
                    continue;
                }
            }
        }
        return false;
    }

    //checks if x-1, x+1, y-1, y+1 dices (if any) are equals to dice's color or top
    public static boolean checkColorOrNumber(Scheme scheme, Dice dice, int x, int y){
        if(x-1>=0){
            if(scheme.getScheme()[x-1][y].isOccupied()&&((scheme.getScheme()[x-1][y].getDado().getColor().equals(dice.getColor())||(scheme.getScheme()[x-1][y].getDado().getTop()==dice.getTop())))){
                return false;
            }
        }
        if(x+1<=3){
            if(scheme.getScheme()[x+1][y].isOccupied()&&((scheme.getScheme()[x+1][y].getDado().getColor().equals(dice.getColor())||(scheme.getScheme()[x+1][y].getDado().getTop()==dice.getTop())))){
                return false;
            }
        }
        if(y-1>=0){
            if(scheme.getScheme()[x][y-1].isOccupied()&&((scheme.getScheme()[x][y-1].getDado().getColor().equals(dice.getColor())||(scheme.getScheme()[x][y-1].getDado().getTop()==dice.getTop())))){
                return false;
            }
        }
        if(y+1<=4){
            return !scheme.getScheme()[x][y + 1].isOccupied() || ((!scheme.getScheme()[x][y + 1].getDado().getColor().equals(dice.getColor()) && (scheme.getScheme()[x][y + 1].getDado().getTop() != dice.getTop())));
        }
        return true;
    }

    public static boolean cellIsRight(Scheme scheme, Dice dice, int x, int y){
        try{
            return scheme.getScheme()[x][y].getColor().equals(dice.getColor()) || scheme.getScheme()[x][y].getNum() == dice.getTop() || (scheme.getScheme()[x][y].getColor().equals(Color.WHITE)&&scheme.getScheme()[x][y].getNum()==0);
        }catch (NullPointerException e){
            System.err.println("Non hai selezionato un dado");
        }
        return false;
    }
}
