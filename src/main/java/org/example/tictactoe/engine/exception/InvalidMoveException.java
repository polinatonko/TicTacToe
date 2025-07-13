package org.example.tictactoe.engine.exception;

/**
 * Exception thrown when a move is invalid.
 */
public class InvalidMoveException extends Exception {
    
    /**
     * Constructs a new {@code InvalidMoveException} with the specified error message.
     *
     * @param errorMessage the error message
     */
    public InvalidMoveException(String errorMessage) {
        super(errorMessage);
    }
}
