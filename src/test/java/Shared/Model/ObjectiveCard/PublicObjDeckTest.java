package Shared.Model.ObjectiveCard;

import Shared.Model.ObjectiveCard.PublicCards.*;
import Shared.Model.ObjectiveCard.PublicCards.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PublicObjDeckTest {
    private PublicObjDeck deck;
    private ArrayList<PublicObjective> publicDeck;

    @Before
    public void setUp() throws Exception {
        deck = new PublicObjDeck();

        publicDeck = new ArrayList<>();
        publicDeck.add(new DifferentColorsRow("Colori diversi - Riga", "Righe senza colori ripetuti", 6));
        publicDeck.add(new DifferentColorsColumn("Colori diversi - Colonna", "Colonne senza colori ripetuti", 5));
        publicDeck.add(new DifferentShadesRow("Sfumature diverse - Riga", "Righe senza sfumature ripetute", 5));
        publicDeck.add(new DifferentShadesColumn("Sfumature diverse - Colonna", "Colonne senza sfumature ripetute",4));
        publicDeck.add(new LightShades("Sfumature Chiare", "Set di 1 & 2 ovunque", 2));
        publicDeck.add(new MediumShades("Sfumature Medie", "Set di 3 & 4 ovunque", 2));
        publicDeck.add(new DarkShades("Sfumature Scure", "Set di 5 & 6 ovunque",2));
        publicDeck.add(new DifferentShades("Sfumature diverse", "Set di dadi di ogni valore ovunque", 5));
        publicDeck.add(new ColoredDiagonals("Diagonali Colorate", "Numero di dadi dello stesso colore diagonalmente adiacenti", 0));
        publicDeck.add(new ColorVariety("Varietà di Colore", "Set di dadi di ogni colore ovunque", 4));

        Assert.assertNotNull(this.deck);


        deck.buildPublic();

    }

    @Test
    public void buildPublic() {

       Assert.assertEquals(deck.getPublicDeck().size(), publicDeck.size());

       for(int i=0;i<deck.getPublicDeck().size();i++){

          Assert.assertTrue(publicDeck.get(i).equals(deck.getPublicDeck().get(i)));

        }
    }

    @Test
    public void getPublicDeck() {

        for(int i=0;i<deck.getPublicDeck().size();i++){
            Assert.assertTrue(publicDeck.get(i).equals(deck.getPublicDeck().get(i)));
            System.out.println(publicDeck.get(i).toString());
        }

    }

    @Test
    public void shuffle() {

        deck.shuffle();

        Assert.assertEquals(deck.getPublicDeck().size(), publicDeck.size());

        for (PublicObjective publicObjective: publicDeck) {
            deck.getPublicDeck().contains(publicObjective);
        }
    }

    @Test
    public void pick() {
        int cont=0;
        PublicObjective pickedCard =deck.pick();

        //pickedCard in publicDeck
        for(int i=0; i<publicDeck.size();i++){
            if(publicDeck.get(i).equals(pickedCard)){
                cont++;
            }
        }

        Assert.assertEquals(1,cont);

      //pickedCard NOT in deck
        Assert.assertEquals(false, deck.getPublicDeck().contains(pickedCard));

        //all cards of deck are in publicDeck
        cont =0;
        boolean isOk = true;
        for (int i=0; i<deck.getPublicDeck().size();i++){
            cont=0;
            for(int j=0;j<publicDeck.size();j++){
                if(deck.getPublicDeck().get(i).equals(publicDeck.get(j))){
                    cont++;
                }
            }
            if(cont!=1){
                isOk=false;
                break;
            }


        }

        Assert.assertTrue(isOk);


        //size of UUT + 1 missing equals publicDeck's size
        Assert.assertEquals(deck.getPublicDeck().size() + 1, publicDeck.size());
        PublicObjDeck deck2 = new PublicObjDeck();
        deck2.buildPublic();
        for(int i=0; i<deck2.getPublicDeck().size(); i++){
            deck2.getPublicDeck().set(i, null);
        }

        Assert.assertNull(deck2.pick());

    }
}