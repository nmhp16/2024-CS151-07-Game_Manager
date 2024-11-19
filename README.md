# Game Manager



# CS 151 Project 2 - Game Manager with Blackjack and Snake Game
**Instructor:** Telvin Zhong  
**Due Date:** Midnight on December 14, 2024  

## Overview
In this project, we will create a JavaFX-based Game Manager application to manage two playable games: Blackjack and Snake. This project will focus on enhancing OOP (Object-Oriented Programming) skills, GUI design, and persistent storage. The Game Manager will allow user login, track high scores, and persist user data across sessions. This project is to be completed in groups of 3-4 students.

## Features

### Game Manager (20 Points)
- **Login (5 points):**  
  - Allows users to either log in or create an account.
  - User credentials are stored in `user_accounts.txt` and persist across sessions.

- **High Scores (5 points):**  
  - Tracks high scores for both games and stores them in `high_scores.txt`.
  - Scores consist of `username` and `score` with a default starting score of 1000.

- **Main Menu/Launcher (5 points):**  
  - Displays the top 5 high scores for each game.
  - Provides options to start either Blackjack or Snake.

- **Toolbar (5 points):**  
  - Persistent toolbar with a "Main Menu" button to navigate back to the main menu.

### Game 1: Blackjack (30 Points)
- **Game Functionality (12 points):**  
  - 1 human player and 3 automated characters (2 players + 1 dealer).
  - Implements betting, hitting, standing, and busting.
  - Game rounds reset with new player balances maintained.

- **Save State Support (8 points):**  
  - Users can save and load game states with a `saveStateString` containing all relevant game data.

- **Graphical User Interface (10 points):**  
  - Displays player cards, balances, and turn indicators.
  - Interactive "Hit" and "Stand" buttons for human player actions.

### Game 2: Snake Game (30 Points)
- **Game Functionality (15 points):**  
  - Snake movement controlled by arrow keys.
  - Food spawns randomly and increases the snake's length and score.
  - Game over triggered by wall or self-collision, with final score and restart options displayed.

- **Pause (2 points):**  
  - Player can pause/resume with the Escape key.

- **Graphical User Interface (15 points):**  
  - Game grid with clear borders and visible score counter.
  - Snake segments grow visually, and food is distinct on the grid.

### Object-Oriented Programming (10 Points)
- **Abstraction, Polymorphism, and Inheritance:**  
  - Classes should handle distinct game components with clear responsibility.
  - Use polymorphism and inheritance where applicable (e.g., Player vs. Dealer in Blackjack).
  - Encapsulation through methods like `moveSnake()` and `growSnake()`.

### Individual Contribution and Github Repository (10 Points)
- **GitHub Repository:**  
  - Make the repo private and invite instructors.
  - README includes `Overview`, `Design`, `Installation Instructions`, `Usage`, and `Contributions`.
  - Evident contribution from each team member via commits.

## Extra Credit
- **3 points:** UML Diagram for one game.
- **4 points:** JUnit tests for game logic and component interactions.
- **3 points:** Encryption for usernames, passwords, and save states.

## Grading
- **Total:** 100 points + 10 points Extra Credit.

## Hints
- **GameController Class:**  
  - Consider using a `GameController` class with an infinite loop to handle game logic and abstract common game steps.
  
## Submission
- Submit via GitHub and ensure all contributions are made before the due date.
