package com.game.model.Blackjack;

public class ComputerPlayer extends Player {
    public ComputerPlayer(String name) {
        super(name);
    }

    /**
     * Overridden takeTurn method for ComputerPlayer. This method is called
     * when the ComputerPlayer takes its turn. It draws a card from the deck and
     * adds it to the ComputerPlayer's hand.
     * 
     * @param deck the deck to draw the card from
     */
    @Override
    public void takeTurn(Deck deck) {
        Card card = deck.drawCard(); // Draw card and remove it from deck
        addCard(card); // Add card to player hand

    }
}
