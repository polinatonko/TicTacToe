package org.example.tictactoe.logic;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Marker;
import org.example.tictactoe.domain.Move;

import java.util.Optional;
import java.util.Random;

/**
 * Random move strategy for the computer.
 */
public class RandomMoveStrategy implements MoveStrategy {
    private final Random random = new Random();

    /**
     * Generates a random move for the specified board
     *
     * @param board     the TicTacToe board to play on
     * @return an {@code Optional} describing the generated random move,
     * if the move is present, otherwise an empty {@code Optional}
     */
    @Override
    public Optional<Move> generateMove(Board board, Marker marker) {
        var moves = board.getAvailableMoves();
        int index = random.nextInt(moves.size());
        return moves.isEmpty() ? Optional.empty() : Optional.of(moves.get(index));
    }
}