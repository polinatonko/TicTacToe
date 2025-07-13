package org.example.tictactoe.engine.strategy;

import org.example.tictactoe.engine.GameEngine;
import org.example.tictactoe.domain.Move;
import org.example.tictactoe.engine.exception.InvalidMoveException;

import java.util.Optional;

/**
 * Implementation of {@link MoveStrategy} based on the Minimax algorithm.
 */
public class MinimaxMoveStrategy implements MoveStrategy {

    private static final int N = 100;

    /**
     * Generates a move for the specified game state using the MiniMax algorithm.
     *
     * @param game       the TicTacToe game to play on
     * @return an {@code Optional} describing the generated move, if the move is present
     */
    @Override
    public Optional<Move> generateMove(GameEngine game) {
        var board = game.getBoard();
        if (board.isFilled()) {
            return Optional.empty();
        }
        var result = minimax(game, 0, true);
        return Optional.ofNullable(result.move);
    }

    private MinimaxResult minimax(GameEngine game, int depth, boolean isMaximizing) {
        switch (game.getState()) {
            case DRAW:
                return new MinimaxResult(0);
            case WIN_X:
                return new MinimaxResult(-N + depth);
            case WIN_O:
                return new MinimaxResult(N - depth);
            default:
        }

        var bestResult = new MinimaxResult(isMaximizing);
        for (var move: game.getBoard().getAvailableMoves()) {
            GameEngine newGame = new GameEngine(game);

            try {
                newGame.makePlayerMove(move);
            } catch (InvalidMoveException ex) {
                throw new RuntimeException("Can't calculate valid move");
            }
            
            int score = minimax(newGame, depth + 1, !isMaximizing).score();
            if (isBetter(score, bestResult.score(), isMaximizing)) {
                bestResult = new MinimaxResult(score, move);
            }
        }
        return bestResult;
    }

    private static boolean isBetter(int score, int bestScore, boolean isMaximizing) {
        return isMaximizing ? score > bestScore : score < bestScore;
    }

    private static record MinimaxResult(int score, Move move) {

        public MinimaxResult(boolean isMaximizing) {
            this(isMaximizing ? -N : N, null);
        }

        public MinimaxResult(int score) {
            this(score, null);
        }
    }
}