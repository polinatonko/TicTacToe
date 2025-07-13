package org.example.tictactoe.engine.strategy;

import org.example.tictactoe.domain.Level;

/**
 * Class for resolving the move strategy based on the difficulty level.
 */
public class MoveStrategyResolver {
    
    /**
     * Resolves the move strategy based on the difficulty level.
     *
     * @param level the difficulty level
     * @return the move strategy
     */
    public MoveStrategy resolve(Level level) {
        return switch (level) {
            case EASY -> new RandomMoveStrategy();
            case HARD -> new MinimaxMoveStrategy();
        };
    }
}
