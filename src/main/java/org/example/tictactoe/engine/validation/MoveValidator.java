package org.example.tictactoe.engine.validation;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Marker;
import org.example.tictactoe.domain.Move;
import org.example.tictactoe.engine.exception.CellIsNotEmptyException;
import org.example.tictactoe.engine.exception.InvalidMoveException;
import org.example.tictactoe.engine.exception.InvalidMovePositionException;

/**
 * Validates moves in the Tic-Tac-Toe game.
 */
public class MoveValidator {

    private final Board board;

    /**
     * Constructs a new {@code MoveValidator} with the specified board.
     *
     * @param board the board to validate moves on
     */
    public MoveValidator(Board board) {
        this.board = board;
    }

    /**
     * Validates the specified move.
     *
     * @param move the move to validate
     * @throws InvalidMoveException if the move is invalid
     */
    public void validate(Move move) throws InvalidMoveException {
        if (isOutOfBounds(move)) {
            throw new InvalidMovePositionException(move);
        }
        if (!isCellEmpty(move)) {
            throw new CellIsNotEmptyException(move);
        }
    }

    private boolean isOutOfBounds(Move move) {
        int size = board.getSize();
        return move.row() < 0 || move.row() >= size ||
                move.col() < 0 || move.col() >= size;
    }

    private boolean isCellEmpty(Move move) {
        return board.getMarkerAt(move.row(), move.col()) == Marker.EMPTY;
    }
} 
