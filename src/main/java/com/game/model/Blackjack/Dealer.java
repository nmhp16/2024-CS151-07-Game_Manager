package com.game.model.Blackjack;

public class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    @Override
    public void takeTurn(Deck deck) {
        while (calculateHandValue() < 17) {
            Card card = deck.drawCard(); // Draw card and remove it from deck
            addCard(card); // Add card to player hand
        }
    }
}
