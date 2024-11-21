package com.game.model.Blackjack;

public class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    /**
     * Overridden takeTurn method for Dealer. This method is called
     * when the Dealer takes their turn. It draws a card from the deck and
     * adds it to the player's hand.
     * 
     * @param deck the deck to draw the card from
     */

    @Override
    public void takeTurn(Deck deck) {
        Card card = deck.drawCard(); // Draw card and remove it from deck
        addCard(card); // Add card to player hand

    }
}
