package org.example.tictactoe.domain;

/**
 * Tic-Tac-Toe cell marker.
 */
public enum Marker {
    EMPTY('.'),
    X('X'),
    O('O');

    private final char symbol;

    Marker(char symbol) {
        this.symbol = symbol;
    }

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