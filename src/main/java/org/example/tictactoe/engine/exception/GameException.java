package org.example.tictactoe.engine.exception;

/**
 * Base exception class for all game-related exceptions.
 * <p>
 * Provides a common superclass for game-specific exceptions.
 */
public class GameException extends RuntimeException {

    /**
     * Constructs a new game exception with the specified message.
     *
     * @param message the detail message
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Constructs a new game exception with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
} 