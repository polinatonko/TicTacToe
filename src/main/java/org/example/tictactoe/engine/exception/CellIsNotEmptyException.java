package org.example.tictactoe.engine.exception;

import org.example.tictactoe.domain.Move;

/**
 * Exception thrown when a move is made on a cell that is not empty.
 */
public class CellIsNotEmptyException extends InvalidMoveException {

    /**
     * Constructs a new {@code CellIsNotEmptyException} with the specified move.
     *
     * @param move the move that caused the exception
     */
    public CellIsNotEmptyException(Move move) {
        super("Cell is not empty [row = %d, col = %d]".formatted(move.row(), move.col()));
    }
}
