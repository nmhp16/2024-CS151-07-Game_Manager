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
        if ((calculateHandValue() < 17 && getHand().size() < 5) || isSoftHand()) {
            Card card = deck.drawCard();
            addCard(card);
        }
    }

    /**
     * Helper method to determine if current dealer has a soft hand
     * 
     * A soft hand is when the first card is an Ace and the total hand value is 17
     * (Ace + 6). If the dealer has a soft hand, they will be asked to hit again
     * otherwise they will stand.
     * 
     * @return True if soft hand, false otherwise
     */
    private boolean isSoftHand() {
        return calculateHandValue() == 17 && getHand().size() == 2
                && (getHand().get(0).getRank().equals("A") || getHand().get(1).getRank().equals("A"));
    }
}
