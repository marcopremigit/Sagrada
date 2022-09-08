package Shared.Model.ObjectiveCard;

import Shared.Color;

import java.util.ArrayList;
import java.util.Collections;

public class PrivateObjDeck {
    private ArrayList<PrivateObjective> privateDeck;

    public PrivateObjDeck(){
        this.privateDeck = new ArrayList<>();
    }

    public void buildPrivate (){
        this.privateDeck.add(0,new PrivateObjective("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", Color.RED));
        this.privateDeck.add(1,new PrivateObjective("Sfumature Gialle","Somma dei valori su tutti i dadi gialli", Color.YELLOW));
        this.privateDeck.add(2,new PrivateObjective("Sfumature Verdi", "Somma dei valori su tutti i dadi verdi",Color.GREEN));
        this.privateDeck.add(3,new PrivateObjective("Sfumature Blu", "Somma dei valori su tutti i dadi blu", Color.BLUE));
        this.privateDeck.add(4,new PrivateObjective("Sfumature Viola", "Somma dei valori su tutti i dadi viola", Color.PURPLE));
    }

    public void shuffle(){
        Collections.shuffle(this.privateDeck);
    }

    //returns top card of the deck (first position of the array)
    public PrivateObjective pick(){
        for(int i=0; i<this.privateDeck.size(); i++){
            PrivateObjective extracted = this.privateDeck.get(i);
            if(extracted!=null){
                this.privateDeck.remove(extracted);
                return extracted;
            }
        }
        return null;
    }

    public ArrayList<PrivateObjective> getPrivateDeck (){
        return privateDeck;
    }
}
