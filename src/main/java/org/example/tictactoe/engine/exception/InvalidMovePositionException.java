package org.example.tictactoe.engine.exception;

import org.example.tictactoe.domain.Move;

/**
 * Exception thrown when a move is made on an invalid position.
 */
public class InvalidMovePositionException extends InvalidMoveException {

    /**
     * Constructs a new {@code InvalidMovePositionException} with the specified move.
     *
     * @param move the move that caused the exception
     */
    public InvalidMovePositionException(Move move) { 
        super("Invalid move [row = %d, col = %d]".formatted(move.row(), move.col())); 
    }
}
