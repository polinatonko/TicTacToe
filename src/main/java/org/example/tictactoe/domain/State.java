package org.example.tictactoe.domain;

/**
 * Represents the current state of the Tic-Tac-Toe game.
 */
public enum State {

    /** The game is currently in progress and can accept moves. */
    IN_PROGRESS,

    /** The game has ended in a draw (no winner). */
    DRAW,

    /** Player X has won the game. */
    WIN_X,
    
    /** Player O has won the game. */
    WIN_O
}
