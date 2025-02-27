package org.example.tictactoe.logic;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Level;
import org.example.tictactoe.domain.Move;
import org.example.tictactoe.domain.Marker;

import java.util.Optional;
import java.util.Random;

/**
 * Move generator for the computer.
 */
class AI {
    private static Random random = new Random();
    private static Move bestMove;
    private static final int N = 100;

    /**
     * Generates a move for the specified board and current player based on the
     * provided difficulty level.
     *
     * @param board     the TicTacToe board to play on
     * @param player    the current player
     * @param level     the difficulty level of the game
     * @return an {@code Optional} describing the generated move for the current user,
     * if the move is present, otherwise an empty {@code Optional}
     */
    public static Optional<Move> generateMove(Board board, Marker player, Level level) {
        return switch (level) {
            case EASY -> generateRandomMove(board);
            case HARD -> calculateBestMove(board, player);
        };
    }

    /**
     * Generates a random move for the specified board
     *
     * @param board     the TicTacToe board to play on
     * @return an {@code Optional} describing the generated random move,
     * if the move is present, otherwise an empty {@code Optional}
     */
    private static Optional<Move> generateRandomMove(Board board) {
        var moves = board.getAvailableMoves();
        int index = random.nextInt(moves.size());
        return moves.isEmpty() ? Optional.empty() : Optional.of(moves.get(index));
    }

    /**
     * Generates a move for the specified board and current player
     * using the MiniMax algorithm.
     *
     * @param board      the TicTacToe board to play on
     * @param player     the current player
     * @return an {@code Optional} describing the generated move,
     * if the move is present, otherwise an empty {@code Optional}
     */
    private static Optional<Move> calculateBestMove(Board board, Marker player) {
        if (board.isFilled()) {
            return Optional.empty();
        }
        minimax(board, player, 0, true);
        return Optional.of(bestMove);
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
    private static int minimax(Board board, Marker player, int depth, boolean isMaximizing) {
        switch (board.getState()) {
            case DRAW:
                return 0;
            case WIN_X:
                return (-N + depth);
            case WIN_O:
                return (N - depth);
            default:
        }

        int bestScore = isMaximizing ? -N : N;
        for (var move: board.getAvailableMoves()) {
            board.tryMakeMove(move, player);
            var score = minimax(board, player.getOpposite(), depth + 1, !isMaximizing);
            if (isMaximizing && score > bestScore || !isMaximizing && score < bestScore) {
                bestScore = score;
                bestMove = move;
            }
            board.undoMove(move);
        }
        return bestScore;
    }
}