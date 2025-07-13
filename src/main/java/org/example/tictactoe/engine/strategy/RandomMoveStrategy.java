package org.example.tictactoe.engine.strategy;

import java.util.Optional;
import java.util.Random;

import org.example.tictactoe.engine.GameEngine;
import org.example.tictactoe.domain.Move;

/**
 * Implementation of {@link MoveStrategy} based on the random move strategy.
 */
public class RandomMoveStrategy implements MoveStrategy {
    
    private final Random random = new Random();

    /**
     * Generates a random move for the specified game state.
     *
     * @param game       the TicTacToe game to play on
     * @return an {@code Optional} describing the generated random move,
     * if the move is present, otherwise an empty {@code Optional}
     */
    @Override
    public Optional<Move> generateMove(GameEngine game) {
        var board = game.getBoard();
        var moves = board.getAvailableMoves();
        
        if (moves.isEmpty()) {
            return Optional.empty();
        }
        
        int index = random.nextInt(moves.size());
        return Optional.of(moves.get(index));
    }
}
