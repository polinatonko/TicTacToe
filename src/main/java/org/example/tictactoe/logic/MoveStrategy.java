package org.example.tictactoe.logic;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Marker;
import org.example.tictactoe.domain.Move;

import java.util.Optional;

/**
 * Move generator strategy for the computer.
 */
public interface MoveStrategy {
    /**
     * Generates a move for the specified board and current player
     *
     * @param board     the TicTacToe board to play on
     * @param player    the current player
     * @return an {@code Optional} describing the generated move for the current user,
     * if the move is present, otherwise an empty {@code Optional}
     */
    Optional<Move> generateMove(Board board, Marker player);
}
