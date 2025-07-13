package org.example.tictactoe.domain;

/**
 * Represents the marker used in the Tic-Tac-Toe game.
 * Each marker has a corresponding character symbol for display purposes.
 */
public enum Marker {

    EMPTY('.'),

    X('X'),
    
    O('O');

    private final char symbol;

    /**
     * Constructs a marker with the specified symbol.
     *
     * @param symbol the character symbol representing this marker
     */
    Marker(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the opposite marker (X ↔ O). For {@link #EMPTY}, returns the same marker.
     *
     * @return the opposite marker, or this marker if it's {@link #EMPTY}
     */
    public Marker getOpposite() {
        return switch(this) {
            case X -> O;
            case O -> X;
            default -> this;
        };
    }

    @Override
    public String toString() { return String.valueOf(symbol); }
}