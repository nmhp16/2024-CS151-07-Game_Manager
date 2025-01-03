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

    /**
     * Gets the suit of the card.
     * 
     * @return The suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Gets the rank of the card.
     * 
     * @return The rank of the card
     */
    public String getRank() {
        return rank;
    }

    /**
     * Gets the value of the card.
     * 
     * @return The value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a string representation of the card in the format "rank-suit".
     * For example, "A-Spades" for an Ace of Spades.
     *
     * @return A string representing the card's rank and suit.
     */
    @Override
    public String toString() {
        return rank + "-" + suit;
    }

    /**
     * Compares this card to another object for equality.
     * 
     * @param obj The object to compare to
     * @return True if the object is a Card and has the same rank and suit as this
     *         card.
     *         False otherwise.
     */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card) obj;
            return rank.equals(other.rank) && suit.equals(other.suit);
        }
        return false;
    }
}
