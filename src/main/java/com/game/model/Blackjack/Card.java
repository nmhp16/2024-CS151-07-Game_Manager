package com.game.model.Blackjack;

public class Card {
    private String suit;
    private String rank;
    private int value;

    /**
     * Overloaded Constructor
     * 
     * @param suit  Card Suit ("Heart", "Spades", etc)
     * @param rank  Card Rank ("J", "Q", etc)
     * @param value Card Value
     */
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    // Getters and Setters
    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + "-" + suit;
    }
}
