package Shared.Model.ObjectiveCard;

import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PrivateObjDeckTest {

    private PrivateObjDeck deck;
    private ArrayList<PrivateObjective> privateDeck;
    @Before
    public void setUp() throws Exception {
        this.deck = new PrivateObjDeck();
        Assert.assertNotNull(this.deck);
        deck.buildPrivate();

        privateDeck = new ArrayList<>();
        this.privateDeck.add(0,new PrivateObjective("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", Color.RED));
        this.privateDeck.add(1,new PrivateObjective("Sfumature Gialle","Somma dei valori su tutti i dadi gialli", Color.YELLOW));
        this.privateDeck.add(2,new PrivateObjective("Sfumature Verdi", "Somma dei valori su tutti i dadi verdi",Color.GREEN));
        this.privateDeck.add(3,new PrivateObjective("Sfumature Blu", "Somma dei valori su tutti i dadi blu", Color.BLUE));
        this.privateDeck.add(4,new PrivateObjective("Sfumature Viola", "Somma dei valori su tutti i dadi viola", Color.PURPLE));
    }

    @Test
    public void buildPrivate() {
        Assert.assertEquals(deck.getPrivateDeck().size(), privateDeck.size());

        for(int i=0; i<deck.getPrivateDeck().size(); i++){
            Assert.assertTrue(privateDeck.get(i).equals(deck.getPrivateDeck().get(i)));
        }
    }

    @Test
    public void shuffle() {
        deck.shuffle();

        Assert.assertEquals(deck.getPrivateDeck().size(), privateDeck.size());

        for(PrivateObjective privateObjective : privateDeck){
            deck.getPrivateDeck().contains(privateObjective);
        }
    }

    @Test
    public void pick() {
        int cont=0;
        PrivateObjective pickedCard =deck.pick();

        //pickedCard in publicDeck
        for(int i=0; i<privateDeck.size();i++){
            if(privateDeck.get(i).equals(pickedCard)){
                cont++;
            }
        }

        Assert.assertEquals(1,cont);

        //pickedCard NOT in deck
        Assert.assertEquals(false, deck.getPrivateDeck().contains(pickedCard));

        //all cards of deck are in publicDeck
        cont =0;
        boolean isOk = true;
        for (int i=0; i<deck.getPrivateDeck().size();i++){
            cont=0;
            for(int j=0;j<privateDeck.size();j++){
                if(deck.getPrivateDeck().get(i).equals(privateDeck.get(j))){
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
        Assert.assertEquals(deck.getPrivateDeck().size() + 1, privateDeck.size());

        PrivateObjDeck deck2 = new PrivateObjDeck();
        for(int i=0; i<deck2.getPrivateDeck().size(); i++){
            deck2.getPrivateDeck().set(i, null);
        }

        Assert.assertNull(deck2.pick());
    }

    @Test
    public void getPrivateDeck() {
        for(int i=0; i<deck.getPrivateDeck().size(); i++){
            Assert.assertTrue(privateDeck.get(i).equals(deck.getPrivateDeck().get(i)));
        }
    }
}