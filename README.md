# Tic-Tac-Toe Console Game

A Java console implementation of the classic *Tic-Tac-Toe* game with multiple game modes, AI opponent, and statistics tracking.

## Features

### Game Modes
- **Human vs Human (HvH)** - Classic two-player gameplay on the same device
- **Human vs Computer (HvC)** - Play against AI with configurable difficulty level

### AI Difficulty Levels
- **Easy** - Random move strategy for casual gameplay and beginners
- **Hard** - Minimax algorithm implementation for unbeatable opponent

## Local Setup

### Prerequisites

Ensure you have the following installed on your system:
- Java 17+
- Maven

### Getting started

1. Clone the repository:
    ```bash
    git clone https://github.com/polinatonko/TicTacToe.git
    cd TicTacToe
    ```

2. Build the project:
    ```bash
    mvn clean package
    ```

3. Run the executable jar:
    ```bash
    java -jar target/tic-tac-toe-1.0.0.jar
    ```