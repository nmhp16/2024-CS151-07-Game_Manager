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
        if (calculateHandValue() < 16 && getHand().size() < 5) {
            Card card = deck.drawCard();
            addCard(card);
        }
    }
}
