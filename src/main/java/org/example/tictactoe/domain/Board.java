package org.example.tictactoe.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tic-Tac-Toe Board.
 */
public class Board {
    private final int size;
    private int emptyCellsCount;
    private State state;
    private Marker[][] cells;
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 3;

    /**
     * Initializes a board.
     * MAX_SIZE value will be used for the board size if the size param greater than MAX_SIZE.
     * MIN_SIZE will be used if the size param less than MIN_SIZE.
     *
     * @param size     size of the board side
     */
    public Board(int size) {
        this.size = Math.max(3, Math.min(size, MAX_SIZE));
        this.cells = new Marker[size][size];
        reset();
    }

    /**
     * Makes the player's move and updates the state of the game (board) accordingly.
     *
     * @param move       player's move
     * @param player     current player
     * @return {@code true} if the move was valid and was therefore made
     */
    public boolean move(Move move, Marker player) {
        if (validateMove(move)) {
            cells[move.row() - 1][move.col() - 1] = player;
            emptyCellsCount--;
            updateState(move, player);
            return true;
        }
        return false;
    }

    /**
     * Resets the state of the board.
     * Used before starting a new game.
     */
    public void reset() {
        this.state = State.NOT_OVER;
        this.emptyCellsCount = this.size * this.size;
        for (int i = 0; i < size; i++) {
            Arrays.fill(cells[i], Marker.EMPTY);
        }
    }

    /**
     * Returns {@code true} if there are no empty cells in the board.
     *
     * @return {@code true} if there are no empty cells in the board
     */
    public boolean isFilled() {
        return emptyCellsCount == 0;
    }

    /**
     * Returns the current state of the game (board).
     *
     * @return the current state of the game (board)
     */
    public State getState() {
        return state;
    }

    /**
     * Checks all cells of the board and returns
     * the list of all available moves.
     *
     * @return the list of all available moves
     */
    public List<Move> getAvailableMoves() {
        List<Move> availableMoves = new ArrayList<>();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if (cells[row][col] == Marker.EMPTY) {
                    availableMoves.add(new Move(row + 1, col + 1));
                }
            }
        }
        return availableMoves;
    }

    /**
     * Returns the board to the state before that move.
     *
     * @param move       the move to undo
     */
    public void undoMove(Move move) {
        cells[move.row() - 1][move.col() - 1] = Marker.EMPTY;
        emptyCellsCount++;
        state = State.NOT_OVER;
    }

    /**
     * Returns the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var row: cells) {
            sb.append("+-".repeat(size)).append("+\n");
            sb.append("|").append(Arrays.stream(row).map(Marker::toString).collect(Collectors.joining("|"))).append("|\n");
        }
        sb.append("+-".repeat(size)).append("+");
        return sb.toString();
    }

    /**
     * Validates the player's move (checks row and column values of the move
     * and state of the cell on the board).
     *
     * @param move      the move to validate
     * @return {@code true} if the move is valid
     * @throws IllegalArgumentException if the move is out of board's bounds
     */
    private boolean validateMove(Move move) {
        if (isOutOfBounds(move.row()) || isOutOfBounds(move.col())) {
            throw new IllegalArgumentException("Both row and col should be in [1, " + size +"] interval.");
        }
        return cells[move.row() - 1][move.col() - 1] == Marker.EMPTY;
    }

    /**
     * Returns @{code true} if the value is in the [1..size] bounds.
     *
     * @param value     the value to check
     * @return {@code true} if the value is in the [1..size] bounds
     */
    private boolean isOutOfBounds(int value) {
        return value < 1 || value > size;
    }

    /**
     * Updates the state of the game (board) after the player's move.
     * Checks whether the conditions for a draw or a win for the current player are met.
     *
     * @param pos       the move of the current player
     * @param player    the current player
     */
    private void updateState(Move pos, Marker player) {
        if (isRowFilled(pos.row() - 1) || isColumnFilled(pos.col() - 1)
                || (pos.row() == pos.col() && isMainDiagFilled())
                || (pos.row() == (size - pos.col() + 1) && isSideDiagFilled())) {
            state = (player == Marker.X) ? State.WIN_X : State.WIN_O;
        } else if (emptyCellsCount == 0) {
            state = State.DRAW;
        }
    }

    /**
     * Returns {@code true} if the specified row of the board is filled with the same value.
     *
     * @param row       the row number
     * @return {@code true} if the row is filled with the same value
     */
    private boolean isRowFilled(int row) {
        return Arrays.stream(cells[row])
                .allMatch(cell -> cell == cells[row][0]);
    }

    /**
     * Returns {@code true} if the specified column of the board is filled with the same value.
     *
     * @param col       the column number
     * @return {@code true} if the column is filled with the same value
     */
    private boolean isColumnFilled(int col) {
        return IntStream.range(0, size)
                .mapToObj(i -> cells[i][col])
                .allMatch(cell -> cell == cells[0][col]);
    }

    /**
     * Returns {@code true} if the main diagonal of the board is filled with the same value.
     *
     * @return {@code true} if the main diagonal is filled with the same value
     */
    private boolean isMainDiagFilled() {
        return IntStream.range(0, size)
                .mapToObj(i -> cells[i][i])
                .allMatch(cell -> cell == cells[0][0]);
    }

    /**
     * Returns {@code true} if the side diagonal of the board is filled with the same value.
     *
     * @return {@code true} if the side diagonal is filled with the same value
     */
    private boolean isSideDiagFilled() {
        return IntStream.range(0, size)
                .mapToObj(i -> cells[i][size - 1 - i])
                .allMatch(cell -> cell == cells[0][size - 1]);
    }
}