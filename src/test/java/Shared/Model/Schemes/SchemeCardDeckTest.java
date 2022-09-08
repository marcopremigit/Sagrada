package Shared.Model.Schemes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class SchemeCardDeckTest {

    private SchemeCardDeck deck;

    @Before
    public void setUp() throws Exception {
        this.deck = new SchemeCardDeck();
        this.deck.buildDeck();
        System.out.println(this.deck.toString());
    }

    @Test
    public void getDeck() {
        Assert.assertNotNull(this.deck.getDeck());
    }

    @Test
    public void shuffle() {
        int beforeShuffling = this.deck.getDeck().size();
        ArrayList<SchemeCard> newDeck = (ArrayList<SchemeCard>) this.deck.getDeck().clone();
        this.deck.shuffle();

        Assert.assertEquals(beforeShuffling, this.deck.getDeck().size());
        Assert.assertTrue(this.deck.getDeck().containsAll(newDeck));
        Assert.assertTrue(newDeck.containsAll(this.deck.getDeck()));

    }

    @Test
    public void pick() {
        int beforePicking = this.deck.getDeck().size();
        ArrayList<SchemeCard> newDeck = (ArrayList<SchemeCard>) this.deck.getDeck().clone();

        SchemeCard newCard = this.deck.pick();

        Assert.assertEquals(beforePicking, this.deck.getDeck().size() + 1);
        Assert.assertTrue(newDeck.contains(newCard));
        Assert.assertFalse(this.deck.getDeck().contains(newCard));

        Assert.assertTrue(newDeck.containsAll(this.deck.getDeck()));
    }

    @Test
    public void buildDeck() {
        int cardRead = 0;
        File file = new File("./src/main/java/Shared.Model/Schemes/SchemeCardMap.txt");
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            for (String read = buffer.readLine(); read != null ; read = buffer.readLine()){
                cardRead ++;
            }

            Assert.assertTrue(cardRead == this.deck.getDeck().size());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}