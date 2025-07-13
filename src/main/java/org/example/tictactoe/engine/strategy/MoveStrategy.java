package org.example.tictactoe.engine.strategy;

import java.util.Optional;

import org.example.tictactoe.engine.GameEngine;
import org.example.tictactoe.domain.Move;

/**
 * Interface for move generator strategies.
 */
public interface MoveStrategy {
    
    /**
     * Generates a move for the specified game state.
     *
     * @param game       the TicTacToe game to play on
     * @return an {@code Optional} describing the generated move if it is present
     */
    Optional<Move> generateMove(GameEngine game);
}
