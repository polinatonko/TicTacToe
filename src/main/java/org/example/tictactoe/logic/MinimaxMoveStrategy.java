package org.example.tictactoe.logic;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Marker;
import org.example.tictactoe.domain.Move;

import java.util.Optional;

/**
 * Move strategy for the computer based on the Minmax algorithm.
 */
public class MinimaxMoveStrategy implements MoveStrategy {
    private static final int N = 100;

    /**
     * Generates a move for the specified board and current player
     * using the MiniMax algorithm.
     *
     * @param board      the TicTacToe board to play on
     * @param player     the current player
     * @return an {@code Optional} describing the generated move,
     * if the move is present, otherwise an empty {@code Optional}
     */
    @Override
    public Optional<Move> generateMove(Board board, Marker player) {
        if (board.isFilled()) {
            return Optional.empty();
        }
        var result = minimax(board, player, 0, true);
        return Optional.ofNullable(result.move);
    }

    /**
     * Minimax algorithm used to generate moves.
     *
     * @param board        the TicTacToe board to play on
     * @param player       the current player
     * @param depth        the current search depth
     * @param isMaximizing flag indicates if this call is maximizing
     *
     * @return score value for the current position
     */
    private MinimaxResult minimax(Board board, Marker player, int depth, boolean isMaximizing) {
        switch (board.getState()) {
            case DRAW:
                return new MinimaxResult(0, null);
            case WIN_X:
                return new MinimaxResult(-N + depth, null);
            case WIN_O:
                return new MinimaxResult(N - depth, null);
            default:
        }

        int bestScore = isMaximizing ? -N : N;
        Move bestMove = null;
        for (var move: board.getAvailableMoves()) {
            board.tryMakeMove(move, player);
            int score = minimax(board, player.getOpposite(), depth + 1, !isMaximizing).score();
            if (isMaximizing && score > bestScore || !isMaximizing && score < bestScore) {
                bestScore = score;
                bestMove = move;
            }
            board.undoMove(move);
        }
        return new MinimaxResult(bestScore, bestMove);
    }

    /**
     * Represents a result of minimax algorithm.
     */
    private static record MinimaxResult(int score, Move move) {}
}