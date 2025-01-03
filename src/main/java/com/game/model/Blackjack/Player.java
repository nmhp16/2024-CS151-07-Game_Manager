package com.game.model.Blackjack;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected List<Card> hand;
    protected int balance;
    protected int bet;

    /**
     * Overloaded Constructor
     * 
     * @param name Player name
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.balance = 1000;
        this.bet = 0;
    }

    /**
     * Method to add card to player hand
     * 
     * @param card Card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Method to set player bet
     * 
     * @param betAmount Amount of bet
     */
    public void setBet(int betAmount) {
        this.bet = betAmount;

    }

    /**
     * Method to calculate hand value
     * 
     * @return Total hand value
     */
    public int calculateHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();

            if (card.getRank().equals("A")) {
                aceCount++;
            }

            // Replace value of ace by 1 instead of 11
            while (value > 21 && aceCount > 0) {
                value -= 10;
                aceCount--;
            }
        }
        return value;
    }

    // Abstract Method
    public abstract void takeTurn(Deck deck);

    /**
     * Method to convert player hand to string
     * 
     * @return String of player cards
     */
    public String getHandString() {
        StringBuilder handString = new StringBuilder();

        for (Card card : hand) {
            handString.append(card.toString()).append(",");
        }

        if (handString.length() > 0) {
            handString.deleteCharAt(handString.length() - 1);
        }
        return handString.toString();
    }

    /**
     * Method to set play hand using handString from saved state
     * 
     * @param handString String of card in hand
     * @return True if valid
     *         False otherwise
     */
    public boolean setHandFromString(String handString, Deck deck) {
        hand.clear(); // Clear current hand;

        String[] cardStrings = handString.split(","); // Split by comma to get each card

        for (String cardString : cardStrings) {
            // Trim extra spaces
            cardString = cardString.trim();

            // Extract rank and suit in format (rank-suit)
            String[] cardParts = cardString.split("-");

            // Check if have valid card
            if (cardParts.length == 2) {
                String rank = cardParts[0];
                String suit = cardParts[1];

                // Determine value based on rank
                int value = determineCardValue(rank);

                // Create new Card object
                Card card = new Card(suit, rank, value);
                hand.add(card);

                // Remove card from deck
                deck.removeCard(card);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to determine card value based on rank
     * 
     * @param rank Card rank
     * @return Card value
     */
    private int determineCardValue(String rank) {
        switch (rank) {
            case "A":
                return 11;
            case "K":
            case "Q":
            case "J":
                return 10;
            default:
                try {
                    return Integer.parseInt(rank); // For numbered card
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rank: " + rank);
                    return 0;
                }
        }
    }

    /**
     * Get the name of the player
     * 
     * @return String player name
     */
    public String getName() {
        return name;
    }

    /**
     * Get current player hand
     * 
     * @return List of Cards in current player hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Get current player balance
     * 
     * @return Player balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Get current bet amount
     * 
     * @return Bet amount
     */
    public int getBet() {
        return bet;
    }

    /**
     * Set player balance
     * 
     * @param balance New balance amount
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Method to reset the player's hand by clearing all cards from it.
     */
    public void resetHand() {
        hand.clear();
    }

    /**
     * Method to adjust balance
     * 
     * @param amount Amount to adjust
     */
    public void adjustBalance(int amount) {
        balance += amount;
    }
}
