package com.game.model.Blackjack;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Overridden takeTurn method for HumanPlayer. This method is called
     * when the player takes their turn. It draws a card from the deck and
     * adds it to the player's hand.
     * 
     * @param deck the deck to draw the card from
     */
    @Override
    public void takeTurn(Deck deck) {
        Card card = deck.drawCard();
        addCard(card);
    }
}
