package org.example.tictactoe.domain;

/**
 * Represents a move in the Tic-Tac-Toe game.
 *
 * @param row the row position (0-based index)
 * @param col the column position (0-based index)
 */
public record Move(int row, int col) {}