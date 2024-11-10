package com.game.model.Blackjack;

public class ComputerPlayer extends Player {
    public ComputerPlayer(String name) {
        super(name);
    }

    @Override
    public void takeTurn(Deck deck) {
        Card card = deck.drawCard(); // Draw card and remove it from deck
        addCard(card); // Add card to player hand

    }
}
