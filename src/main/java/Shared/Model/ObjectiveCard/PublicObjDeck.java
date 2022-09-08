package Shared.Model.ObjectiveCard;

import Shared.Model.ObjectiveCard.PublicCards.*;
import Shared.Model.ObjectiveCard.PublicCards.*;

import java.util.ArrayList;
import java.util.Collections;

public class PublicObjDeck {
    private  ArrayList<PublicObjective> publicDeck;

    public PublicObjDeck(){
        this.publicDeck = new ArrayList<>();
    }

    public void buildPublic() {
        this.publicDeck.add(new DifferentColorsRow("Colori diversi - Riga", "Righe senza colori ripetuti", 6));
        this.publicDeck.add(new DifferentColorsColumn("Colori diversi - Colonna", "Colonne senza colori ripetuti", 5));
        this.publicDeck.add(new DifferentShadesRow("Sfumature diverse - Riga", "Righe senza sfumature ripetute", 5));
        this.publicDeck.add(new DifferentShadesColumn("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute",4));
        this.publicDeck.add(new LightShades("Sfumature Chiare", "Set di 1 & 2 ovunque", 2));
        this.publicDeck.add(new MediumShades("Sfumature Medie", "Set di 3 & 4 ovunque", 2));
        this.publicDeck.add(new DarkShades("Sfumature Scure", "Set di 5 & 6 ovunque",2));
        this.publicDeck.add(new DifferentShades("Sfumature diverse", "Set di dadi di ogni valore ovunque", 5));
        this.publicDeck.add(new ColoredDiagonals("Diagonali Colorate", "Numero di dadi dello stesso colore diagonalmente adiacenti", 0));
        this.publicDeck.add(new ColorVariety("Varietà di Colore", "Set di dadi di ogni colore ovunque", 4));
    }

    public ArrayList<PublicObjective> getPublicDeck(){
        return publicDeck;
    }

    //shuffle ToolDeck, could be avoided?
    public void shuffle(){
        Collections.shuffle(this.publicDeck);
    }

    //returns top card of the deck (first position of the array)
    public PublicObjective pick(){
        for(int i=0; i<this.publicDeck.size(); i++){
            PublicObjective extracted = this.publicDeck.get(i);
            if(extracted!=null){
                this.publicDeck.remove(extracted);
                return extracted;
            }
        }
        return null;
    }
}
