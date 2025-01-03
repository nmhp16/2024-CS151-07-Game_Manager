package com.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.game.model.Blackjack.BlackjackGame;
import com.game.model.Blackjack.Card;
import com.game.model.Blackjack.Deck;
import com.game.model.Blackjack.Player;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    private BlackjackGame game;

    @BeforeEach
    void setUp() {
        game = new BlackjackGame();
    }

    @Test
    void testStartNewRound() {
        // Test the initialization of a new round
        game.startNewRound();
        assertEquals("You", game.getCurrentPlayer().getName());
        assertEquals(2, game.getHumanPlayer().getHand().size());
        assertEquals(2, game.getPlayer1().getHand().size());
        assertEquals(2, game.getPlayer2().getHand().size());
        assertEquals(2, game.getDealer().getHand().size());

        assertEquals("You", game.getCurrentPlayer().getName(), "The first player should be 'You'.");
        assertEquals(2, game.getHumanPlayer().getHand().size(), "Each player should start with 2 cards.");
        assertEquals(2, game.getPlayer1().getHand().size(), "Player 1 should start with 2 cards.");
        assertEquals(2, game.getPlayer2().getHand().size(), "Player 2 should start with 2 cards.");
        assertEquals(2, game.getDealer().getHand().size(), "Dealer should start with 2 cards.");
    }

    @Test
    void testNextTurn() {
        // Test turn progression logic
        game.nextTurn();
        assertEquals("Player 1", game.getCurrentPlayer().getName());
        assertEquals("Player 1", game.getCurrentPlayer().getName(), "After 'You' it should be 'Player 1'.");

        game.nextTurn();
        assertEquals("Player 2", game.getCurrentPlayer().getName());
        assertEquals("Player 2", game.getCurrentPlayer().getName(), "After 'Player 1' it should be 'Player 2'.");

        game.nextTurn();
        assertEquals("Dealer", game.getCurrentPlayer().getName());
        assertEquals("Dealer", game.getCurrentPlayer().getName(), "After 'Player 2' it should be 'Dealer'.");

        game.nextTurn();
        assertEquals("You", game.getCurrentPlayer().getName());
        assertEquals("You", game.getCurrentPlayer().getName(), "After 'Dealer' it should go back to 'You'.");
    }

    @Test
    void testSaveGameState() {
        // Test saving the game state
        game.startNewRound();
        String saveState = game.saveGameState();
        assertNotNull(saveState, "Save state should not be null.");
        assertFalse(saveState.isEmpty(), "Save state should not be empty.");
    }

    @Test
    void testLoadGameState() {
        // Test loading the game state
        game.startNewRound();
        String saveState = game.saveGameState();

        // Simulate loading the game state into a new instance
        BlackjackGame newGame = new BlackjackGame();
        assertTrue(newGame.loadGameState(saveState), "Game state should be loaded successfully.");
        assertEquals(game.getCurrentPlayer().getName(), newGame.getCurrentPlayer().getName());
        assertEquals(game.getCurrentPlayer().getName(), newGame.getCurrentPlayer().getName(),
                "The current player should be the same.");
    }

    @Test
    void testCalculateResultsPlayerWin() {
        // Test calculating results where player wins
        Player player = game.getHumanPlayer();
        player.setBet(100);

        // Simulate the dealer and player having certain hands for testing
        player.addCard(new Card("HEARTS", "7", 7));
        player.addCard(new Card("CLUBS", "9", 9));
        game.getDealer().addCard(new Card("DIAMONDS", "10", 10));
        game.getDealer().addCard(new Card("SPADES", "6", 6));

        String result = game.calculateResults(player);
        assertEquals("You Tie!", result);

    }

    @Test
    void testCalculateResultsDealerWin() {
        // Test calculating results where dealer wins
        Player player = game.getHumanPlayer();
        player.setBet(100);
        // Simulate the dealer and player having certain hands for testing
        player.addCard(new Card("HEARTS", "7", 7));
        player.addCard(new Card("CLUBS", "6", 6));
        game.getDealer().addCard(new Card("DIAMONDS", "10", 10));
        game.getDealer().addCard(new Card("SPADES", "9", 9));

        String result = game.calculateResults(player);
        assertEquals("You Lose!", result);
    }

    @Test
    void testLoadGameStateInvalidData() {
        // Test loading invalid game state
        String invalidSaveState = "invalid:data:here";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidTurn() {
        // Test loading game state with invalid turn
        String invalidSaveState = "turn:invalidTurn|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidPlayer() {
        // Test loading game state with invalid player
        String invalidSaveState = "player:invalidPlayer|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidBalance() {
        // Test loading game state with invalid balance
        String invalidSaveState = "balance:invalidBalance|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidBet() {
        // Test loading game state with invalid bet
        String invalidSaveState = "bet:invalidBet|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidHand() {
        // Test loading game state with invalid hand
        String invalidSaveState = "hand:invalidHand|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidDeck() {
        // Test loading game state with invalid deck
        String invalidSaveState = "deck:invalidDeck|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testLoadGameStateInvalidCards() {
        // Test loading game state with invalid cards
        String invalidSaveState = "cards:invalidCards|...";
        assertEquals(false, game.loadGameState(invalidSaveState));
    }

    @Test
    void testTakeTurn() {
        Deck deck = new Deck();
        Player player = game.getHumanPlayer();
        player.addCard(new Card("HEARTS", "7", 7));
        player.addCard(new Card("CLUBS", "6", 6));
        game.getDealer().addCard(new Card("DIAMONDS", "10", 10));
        game.getDealer().addCard(new Card("SPADES", "9", 9));

        player.takeTurn(deck);
        assertEquals(3, player.getHand().size());
        assertEquals(deck.getDeckSize(), 51);
    }

    @Test
    void testGetHumanPlayer() {
        Player player = game.getHumanPlayer();
        assertEquals("You", player.getName());
    }

    @Test
    void testGetPlayer1() {
        Player player = game.getPlayer1();
        assertEquals("Player 1", player.getName());
    }

    @Test
    void testGetPlayer2() {
        Player player = game.getPlayer2();
        assertEquals("Player 2", player.getName());
    }

    @Test
    void testGetDealer() {
        Player player = game.getDealer();
        assertEquals("Dealer", player.getName());
    }

    @Test
    void testGetCurrentPlayer() {
        game.nextTurn();
        Player player = game.getCurrentPlayer();
        assertEquals("Player 1", player.getName());
    }

    @Test
    void testGetDeck() {
        Deck deck = game.getDeck();
        assertEquals(52, deck.getDeckSize());
    }

    @Test
    void testDealerTakeTurn() {
        Deck deck = new Deck();
        Player player = game.getDealer();
        player.addCard(new Card("HEARTS", "7", 7));
        player.addCard(new Card("CLUBS", "6", 6));
        player.takeTurn(deck);
        assertEquals(3, player.getHand().size());
        assertEquals(deck.getDeckSize(), 51);
    }

    @Test
    void testSoft17TakeTurn() {
        Deck deck = new Deck();
        Player player = game.getDealer();
        player.addCard(new Card("HEARTS", "A", 11));
        player.addCard(new Card("CLUBS", "6", 6));
        player.takeTurn(deck);
        assertEquals(3, player.getHand().size());
        assertEquals(deck.getDeckSize(), 51);
    }

    @Test
    void testHard17TakeTurn() {
        Deck deck = new Deck();
        Player player = game.getDealer();
        player.addCard(new Card("HEARTS", "K", 10));
        player.addCard(new Card("CLUBS", "7", 7));
        player.takeTurn(deck);
        assertEquals(2, player.getHand().size());
        assertEquals(deck.getDeckSize(), 52);
    }

    @Test
    void testComputerTakeTurn() {
        Deck deck = new Deck();
        Player player = game.getPlayer1();
        player.addCard(new Card("HEARTS", "K", 10));
        player.addCard(new Card("CLUBS", "6", 6));
        player.takeTurn(deck);
        assertEquals(2, player.getHand().size());
        assertEquals(deck.getDeckSize(), 52);
    }
}
