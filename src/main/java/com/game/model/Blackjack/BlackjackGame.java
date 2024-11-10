package com.game.model.Blackjack;

import java.util.List;

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

        // Set bet for player
        humanPlayer.setBet(100); // User bet, taken from JavaFX input
        player1.setBet(50); // Default bet for player 1
        player2.setBet(30); // Default bet for player 2

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
     * Method to calculate round result
     */
    public void calculateResults() {
        int dealerValue = dealer.calculateHandValue();

        // List of players to calculate result
        List<Player> players = List.of(humanPlayer, player1, player2);

        for (Player player : players) {
            int playerValue = player.calculateHandValue();
            int bet = player.getBet();

            // Case 1: Player and Dealer both > 21
            if (dealerValue > 21 && playerValue > 21) {
                // Tie, lose and gain nothing
                player.adjustBalance(0);
            }

            // Case 2: Player <= 21, Dealer > 21
            else if (dealerValue > 21 && playerValue <= 21) {
                // Win, Player gain bet
                player.adjustBalance(bet);
            }

            // Case 3: Player > 21, Dealer <= 21
            else if (dealerValue <= 21 && playerValue > 21) {
                // Bust, Player lost bet
                player.adjustBalance(-bet);
            }

            // Case 4: Dealer <= 21 && Player <= 21
            else if (dealerValue <= 21 && playerValue <= 21) {
                // Player value > Dealer value
                if (playerValue > dealerValue) {
                    // Win, Player gain bet
                    player.adjustBalance(bet);
                }
                // PLayer value < Dealer value
                else if (playerValue < dealerValue) {
                    // Bust, Player lost bet
                    player.adjustBalance(-bet);
                }
                // Player value = Dealer value
                else {
                    // Tie, Gain nothing
                    player.adjustBalance(0);
                }
            }
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

        return saveState.toString();
    }

    /**
     * Method to load saveStateString to recover game state
     * 
     * @param saveStateString Saved game state string
     */
    public void loadGameState(String saveStateString) {
        String[] entries = saveStateString.split("\\|");

        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "turn":
                    turn = value;
                    break;
                case "humanCards":
                    humanPlayer.setHandFromString(value);
                    break;
                case "player1Cards":
                    player1.setHandFromString(value);
                    break;
                case "player2Cards":
                    player2.setHandFromString(value);
                    break;
                case "dealerCards":
                    dealer.setHandFromString(value);
                    break;
                case "humanBalance":
                    humanPlayer.setBalance(Integer.parseInt(value));
                    break;
                case "player1Balance":
                    player1.setBalance(Integer.parseInt(value));
                    break;
                case "player2Balance":
                    player2.setBalance(Integer.parseInt(value));
                    break;
                case "dealerBalance":
                    dealer.setBalance(Integer.parseInt(value));
                    break;
                case "humanBet":
                    humanPlayer.setBet(Integer.parseInt(value));
                    break;
                case "player1Bet":
                    player1.setBet(Integer.parseInt(value));
                    break;
                case "player2Bet":
                    player2.setBet(Integer.parseInt(value));
                    break;
            }
        }
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
