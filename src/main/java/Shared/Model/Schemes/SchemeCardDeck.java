package Shared.Model.Schemes;

import Server.SchemeCardsHandler.SchemeCardMapper;

import java.util.ArrayList;
import java.util.Collections;

public class SchemeCardDeck {
    private ArrayList<SchemeCard> deck;

    /**
     * @author Fabrizio Siciliano
     * */
    public SchemeCardDeck(){
        this.deck = new ArrayList<>();
    }

    /**
     * @return list of cards in deck
     * @author Fabrizio Siciliano
     * */
    public ArrayList<SchemeCard> getDeck() {
        return deck;
    }

    /**
     * shuffles deck
     * @author Fabrizio Siciliano
     * */
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    /**
     * picks top card of the deck and removes it
     * @author Fabrizio Siciliano
     * */
    public SchemeCard pick(){
        SchemeCard extracted = this.deck.get(0);
        this.deck.remove(extracted);
        return extracted;
    }

    /**
     * builds deck
     * @see SchemeCardMapper#readAllCards()
     * @author Fabrizio Siciliano
     * */
    public void buildDeck(){
        SchemeCardMapper SCM = SchemeCardMapper.getCardMapper();
        this.deck = SCM.readAllCards();
    }

    /**
     * @return visual rappresentation of the deck
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (SchemeCard card: this.deck) {
            builder.append(card.getFront().toString());
            builder.append("\n");
            builder.append(card.getRear().toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}
