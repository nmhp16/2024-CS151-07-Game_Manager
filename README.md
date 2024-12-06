# Game Manager

## Overview
The **Game Manager** is a JavaFX-based application designed to demonstrate Object-Oriented Programming (OOP) principles, GUI design, and persistent storage techniques. It features two games: **Blackjack** and **Snake**. Users can log in, view high scores, play games, and save progress.

---

## Design

### Key Components

1. **Game Manager**:
   - Acts as the hub for user interactions, login, and game selection.
   - Manages high scores, user profiles, and persistent storage.

2. **Blackjack**:
   - A card game featuring one human player, three automated characters (two players + dealer), and betting mechanics.
   - Includes save/load support for game progress.

3. **Snake**:
   - A classic arcade game with snake movement, food collection, and collision-based game over logic.
   - Features a pause/resume function.

4. **High Scores**:
   - Tracks and displays the top 5 high scores for each game, stored in `high_scores.txt`.

5. **User Management**:
   - Enables user login and account creation, with credentials stored in `user_accounts.txt`.

6. **Game UI**:
   - Provides a user-friendly graphical interface for gameplay and navigation.

---

## Installation Instructions

1. **Clone the repository from GitHub:**  
   ```bash
   git clone https://github.com/nmhp16/2024-CS151-07-Game_Manager
   ```
2. **Navigate into the project directory**
  ```bash
   cd 2024-CS151-07-Game_Manager
   ```
3. **Compile the java files:**
  ```bash
   javac -d bin src/*.java
   ```
4. **Run the application:**
  ```bash
   java -cp bin src/GameManager
   ```

## Usage

### 1. User Login and Profile Management

- **Login**: 
  - When the application starts, you will be prompted to log in with your existing credentials or create a new account.
  - If you already have an account, simply enter your username and password.
  - If you're a new user, you can register by creating a username and password.
  - User credentials are securely saved to a `user_accounts.txt` file for persistent access across sessions.
  
- **Profile Management**: 
  - After logging in, you can update your profile details (username, password).
  - The system will store and retrieve these details for future logins.

### 2. Main Menu

- **Navigating the Main Menu**:
  - Upon login, you'll be presented with the Main Menu, where you can select one of the two available games:
    - **Blackjack**: Play a card game with automated characters (dealer and two players).
    - **Snake**: Play the classic Snake game with real-time snake movement.
  
- **Viewing High Scores**:
  - The Main Menu will display the top 5 high scores for both Blackjack and Snake games. These scores are stored in `high_scores.txt` and updated after each game.

### 3. Playing Blackjack

- **Game Flow**:
  - Blackjack is played with a human player and three automated characters (dealer and two other players).
  - The game will prompt you to place a bet before starting the round.
  - You will receive two cards and can choose to **Hit** (draw another card) or **Stand** (keep your current cards).
  - The goal is to get as close to 21 points as possible without exceeding it.
  - If you choose to hit and your hand exceeds 21, you will bust and lose the bet.
  
- **Game Controls**:
  - **Hit**: Draw another card.
  - **Stand**: Keep your current hand and end your turn.
  - **New Round**: Once a round is complete, the game will reset, and you can place a new bet for the next round.
  - **Save/Load**: You can save the game state to resume later using the "Save" option, or load a previously saved game state.

### 4. Playing Snake

- **Game Flow**:
  - The game is played by controlling a snake that grows longer as it eats food.
  - The snake is controlled using the **arrow keys** (Up, Down, Left, Right).
  - Every time the snake eats food, its length increases, and your score rises.
  - The game ends if the snake collides with the wall or itself.
  
- **Game Controls**:
  - **Arrow Keys**: Use the arrow keys to control the direction of the snake.
  - **Pause/Resume**: Press the **Escape** key to pause or resume the game during gameplay.
  - **Restart**: After the game is over, you can choose to restart and try to beat your high score.

### 5. High Scores

- **Tracking and Displaying High Scores**:
  - The top 5 high scores for each game (Blackjack and Snake) are displayed in the Main Menu.
  - After finishing a game, if your score is in the top 5, it will be added to the high score list.
  - High scores are stored persistently in the `high_scores.txt` file.
  
- **Clearing High Scores**:
  - You can clear or reset the high scores from the game settings, but be cautious as this will remove all recorded scores.

### 6. Toolbar Navigation

- **Toolbar**: 
  - The toolbar at the top of the application window includes a **Main Menu** button that allows you to return to the main menu at any time.
  - It is persistent across all game screens, so you can always navigate back easily.

---
## Contributions

This project was developed by the following team members:
- **Aaron Mundanilkunathil**:
- **Huu Tinh Nguyen**:
- **Aung Aung**:
- **Nguyen Pham**: