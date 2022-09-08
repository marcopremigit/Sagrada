package Shared.Model.Tools;

import Shared.Color;

import java.util.ArrayList;
import java.util.Collections;

public class ToolDeck {
    private ArrayList<ToolCard> mazzo;

    public ToolDeck(){
        /*builds deck*/
        this.mazzo = new ArrayList<ToolCard>(12);
        this.mazzo.add(0,new ToolCard("Pinza Sgrossatrice", "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", Color.PURPLE));
        this.mazzo.add(1,new ToolCard("Pennello per Eglomise", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore. Devi rispettare tutte le altre restrizioni di piazzamento",  Color.BLUE));
        this.mazzo.add(2,new ToolCard("Alesatore per lamina di rame", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. Devi rispettare tutte le altre restrizioni di piazzamento", Color.RED));
        this.mazzo.add(3,new ToolCard("Lathekin","Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento",Color.YELLOW));
        this.mazzo.add(4,new ToolCard("Taglierina circolare", "Dopo aver scelto un dado, scambia quel dado con un dado sul tracciato dei round", Color.GREEN));
        this.mazzo.add(5,new ToolCard("Pennello per pasta salda", "Dopo aver scelto un dado, tira nuovamente quel dado. Se non puoi piazzarlo, riponilo nella riserva", Color.PURPLE));
        this.mazzo.add(6,new ToolCard("Martelletto", "Tira nuovamente tutti i dadi della Riserva. Questa carta può essere usata solo durante il secondo turno, prima di scegliere il secondo dado", Color.BLUE));
        this.mazzo.add(7,new ToolCard("Tenaglia a Rotelle", "Dopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turno in questo round", Color.RED));
        this.mazzo.add(8,new ToolCard("Riga in Sughero", "Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado. Devi rispettare tutte le restrizioni di piazzamento",  Color.YELLOW));
        this.mazzo.add(9,new ToolCard("Tampone Diamantato", "Dopo aver scelto un dado, giralo sulla faccia opposta. 6 diventa 1, 5 diventa 2, 4 diventa 3, ecc...", Color.GREEN));
        this.mazzo.add(10,new ToolCard("Diluente per Pasta Salda", "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Saccetto. Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento", Color.PURPLE));
        this.mazzo.add(11,new ToolCard("Taglierina Manuale", "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round. Devi rispettare tutte le restrizioni di piazzamento", Color.BLUE));
    }

    public ArrayList<ToolCard> getMazzo() {
        return mazzo;
    }

    //shuffle ToolDeck
    public void shuffle(){
        Collections.shuffle(this.mazzo);
    }

    //returns top card of the deck (first position of the array)
    public ToolCard pick(){
        ToolCard extracted = this.mazzo.get(0);
        this.mazzo.remove(extracted);
        return extracted;
    }
}