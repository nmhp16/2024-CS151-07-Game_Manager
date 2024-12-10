package com.game.model.Blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    /**
     * Default Constructor
     */
    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        randomizeDeck();
    }

    /**
     * Method to randomize deck
     */
    public void randomizeDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Method to draw 1 card and remove it from deck
     * 
     * @return Card drew
     */
    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }

    /**
     * Method to reset deck
     */
    public void resetDeck() {
        cards.clear();
        initializeDeck();
        randomizeDeck();
    }

    /**
     * Method to initialize deck by populating it
     */
    public void initializeDeck() {
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        int[] values = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11 };

        // Populate the deck with cards
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
    }

    /**
     * Removes a card from the deck if it exists in the deck. If the card is not
     * found in the deck, nothing happens.
     * 
     * @param card The card to remove from the deck
     */
    public void removeCard(Card card) {
        // Loop through the deck and remove the matching card
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals(card)) {
                cards.remove(i);
                break;
            }
        }
    }

    /**
     * Get the number of cards in the deck
     * 
     * @return Number of cards in the deck
     */
    public int getDeckSize() {
        return cards.size();
    }
}
