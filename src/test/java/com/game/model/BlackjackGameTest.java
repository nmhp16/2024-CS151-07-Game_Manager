package com.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.game.model.Blackjack.BlackjackGame;
import com.game.model.Blackjack.Card;
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
}

/****** 1a1f586d-46e3-4843-a031-828e9cc53058 *******/