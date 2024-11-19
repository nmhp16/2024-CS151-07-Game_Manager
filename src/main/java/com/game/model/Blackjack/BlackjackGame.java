package com.game.model.Blackjack;

import java.util.Base64;

public class BlackjackGame {
    private Deck deck;
    private Player humanPlayer;
    private Player dealer;
    private Player player1;
    private Player player2;
    private String turn;

    /**
     * Overloaded Constructor
     * 
     * @param username Username from JavaFX
     */
    public BlackjackGame() {
        this.deck = new Deck();
        this.humanPlayer = new HumanPlayer("You");
        this.player1 = new ComputerPlayer("Player 1");
        this.player2 = new ComputerPlayer("Player 2");
        this.dealer = new Dealer();
        this.turn = "You";
    }

    /**
     * Helper method to populate all player hands
     */
    private void initializeHands() {
        humanPlayer.addCard(deck.drawCard());
        player1.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        humanPlayer.addCard(deck.drawCard());
        player1.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

    }

    /**
     * Method to start new round
     */
    public void startNewRound() {
        humanPlayer.resetHand(); // Reset human hand
        dealer.resetHand(); // Reset dealer hand
        player1.resetHand(); // Reset player 1 hand
        player2.resetHand(); // Reset player 2 hand
        deck.resetDeck(); // Reset deck
        turn = "You";

        // Set bet for player
        humanPlayer.setBet(0); // User bet, taken from JavaFX input
        player1.setBet(50); // Default bet for player 1
        player2.setBet(100); // Default bet for player 2

        initializeHands(); // Give 2 cards to all players
    }

    /**
     * Method to get current player
     * 
     * @return String current turn player
     */
    public Player getCurrentPlayer() {
        switch (turn) {
            case "You":
                return humanPlayer;
            case "Player 1":
                return player1;
            case "Player 2":
                return player2;
            default:
                return dealer;
        }
    }

    /**
     * Method to decide next turn
     */
    public void nextTurn() {
        switch (turn) {
            case "You":
                turn = "Player 1";
                break;
            case "Player 1":
                turn = "Player 2";
                break;
            case "Player 2":
                turn = "Dealer";
                break;
            default:
                turn = "You";
                break;
        }
    }

    /**
     * Method to save current game state
     * 
     * @return String of game state
     */
    public String saveGameState() {
        StringBuilder saveState = new StringBuilder();

        saveState.append("turn:").append(turn).append("|");

        // Get player current hand
        saveState.append("humanCards:").append(humanPlayer.getHandString()).append("|");
        saveState.append("player1Cards:").append(player1.getHandString()).append("|");
        saveState.append("player2Cards:").append(player2.getHandString()).append("|");
        saveState.append("dealerCards:").append(dealer.getHandString()).append("|");

        // Get player current balance
        saveState.append("humanBalance:").append(humanPlayer.getBalance()).append("|");
        saveState.append("player1Balance:").append(player1.getBalance()).append("|");
        saveState.append("player2Balance:").append(player2.getBalance()).append("|");
        saveState.append("dealerBalance:").append(dealer.getBalance()).append("|");

        // Get player current bet
        saveState.append("humanBet:").append(humanPlayer.getBet()).append("|");
        saveState.append("player1Bet:").append(player1.getBet()).append("|");
        saveState.append("player2Bet:").append(player2.getBet());

        // Encode game state to Base64
        String encodedSaveState = Base64.getEncoder().encodeToString(saveState.toString().getBytes());

        return encodedSaveState;
    }

    /**
     * Method to load saveStateString to recover game state
     * 
     * @param saveStateString Saved game state string
     * @return True if valid
     *         False otherwise
     */
    public boolean loadGameState(String saveStateString) {
        try {
            // Decode Base64 encoded string
            String decodedState = new String(Base64.getDecoder().decode(saveStateString));

            String[] entries = decodedState.split("\\|");

            for (String entry : entries) {
                String[] keyValue = entry.split(":");

                if (keyValue.length != 2) {
                    return false; // Invalid key format
                }

                String key = keyValue[0];
                String value = keyValue[1];

                switch (key) {
                    case "turn":
                        if (!isValidTurn(value)) {
                            return false;
                        }
                        turn = value;
                        break;
                    case "humanCards":
                        if (!humanPlayer.setHandFromString(value)) {
                            return false;
                        }
                        break;
                    case "player1Cards":
                        if (!player1.setHandFromString(value)) {
                            return false;
                        }
                        break;
                    case "player2Cards":
                        if (!player2.setHandFromString(value)) {
                            return false;
                        }
                        break;
                    case "dealerCards":
                        if (!dealer.setHandFromString(value)) {
                            return false;
                        }
                        break;
                    case "humanBalance":
                        if (!isValidBalance(value)) {
                            return false;
                        }
                        humanPlayer.setBalance(Integer.parseInt(value));
                        break;
                    case "player1Balance":
                        if (!isValidBalance(value)) {
                            return false;
                        }
                        player1.setBalance(Integer.parseInt(value));
                        break;
                    case "player2Balance":
                        if (!isValidBalance(value)) {
                            return false;
                        }
                        player2.setBalance(Integer.parseInt(value));
                        break;
                    case "dealerBalance":
                        if (!isValidBalance(value)) {
                            return false;
                        }
                        dealer.setBalance(Integer.parseInt(value));
                        break;
                    case "humanBet":
                        if (!isValidBet(value)) {
                            return false;
                        }
                        humanPlayer.setBet(Integer.parseInt(value));
                        break;
                    case "player1Bet":
                        if (!isValidBet(value)) {
                            return false;
                        }
                        player1.setBet(Integer.parseInt(value));
                        break;
                    case "player2Bet":
                        if (!isValidBet(value)) {
                            return false;
                        }
                        player2.setBet(Integer.parseInt(value));
                        break;
                }
            }
            return true;

        } catch (Exception e) { // Decoding or parsing exceptions
            return false;
        }
    }

    /**
     * Helper method to check if saved state turn is valid
     * 
     * @param value Saved state string for turn
     * @return True if valid
     *         False otherwise
     */
    private boolean isValidTurn(String value) {
        if (value.equalsIgnoreCase("You") || value.equalsIgnoreCase("Player 1")
                || value.equalsIgnoreCase("Player 2")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper method to check if saved state balance valid
     * 
     * @param value Saved state string for balance
     * @return True if valid
     *         False otherwise
     */
    private boolean isValidBalance(String value) {
        try {
            int balance = Integer.parseInt(value);
            return balance >= 0; // Non-negative balance
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Helper method to check if saved state bet is valid
     * 
     * @param value Saved state string for bet
     * @return True if valid
     *         False otherwise
     */
    private boolean isValidBet(String value) {
        try {
            int bet = Integer.parseInt(value);
            return bet >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method to calculate player result
     * 
     * @param player Player to be calculated
     * @return String message result
     */
    public String calculateResults(Player player) {
        int dealerValue = dealer.calculateHandValue();
        int playerValue = player.calculateHandValue();
        int bet = player.getBet();
        String message = "";

        boolean playerBlackjack = playerValue == 21 && player.getHand().size() == 2;
        boolean dealerBlackjack = dealerValue == 21 && dealer.getHand().size() == 2;

        // Case 1: Player and Dealer both > 21
        if (dealerValue > 21 && playerValue > 21) {
            // Tie, lose and gain nothing
            player.adjustBalance(-bet);
            message = player.getName() + " Lose!";
        }

        // Case 2: Player <= 21, Dealer > 21
        else if (dealerValue > 21 && playerValue <= 21) {
            // Win, Player gain bet
            player.adjustBalance(bet);
            message = player.getName() + " Win!";
        }

        // Case 3: Player > 21, Dealer <= 21
        else if (dealerValue <= 21 && playerValue > 21) {
            // Bust, Player lost bet
            player.adjustBalance(-bet);
            message = player.getName() + " Lose!";
        }

        // Case 4: Dealer <= 21 && Player <= 21
        else if (dealerValue <= 21 && playerValue <= 21) {
            // Case 5: Player blackjack
            if (playerBlackjack && !dealerBlackjack) {
                player.adjustBalance(bet); // Player win
                message = player.getName() + " Win!";
            }
            // Case 6: Dealer blackjack
            else if (dealerBlackjack && !playerBlackjack) {
                player.adjustBalance(-bet);
                message = player.getName() + " Lose!";
            }
            // Case 7: Player value > Dealer value
            else if (playerValue > dealerValue) {
                // Win, Player gain bet
                player.adjustBalance(bet);
                message = player.getName() + " Win!";
            }
            // Case 8: PLayer value < Dealer value
            else if (playerValue < dealerValue) {
                // Bust, Player lost bet
                player.adjustBalance(-bet);
                message = player.getName() + " Lose!";
            }
            // Case 9: Player value = Dealer value
            else {
                // Tie, Gain nothing
                player.adjustBalance(0);
                message = player.getName() + " Tie!";
            }
        }
        return message;
    }

    // Getters
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Player getDealer() {
        return dealer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Deck getDeck() {
        return deck;
    }
}
