package org.example.tictactoe.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.tictactoe.domain.Marker.EMPTY;
import static java.util.stream.Collectors.toList;

/**
 * Represents the Tic-Tac-Toe game board.
 */
public class Board {

    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 3;

    private final Marker[][] cells;
    private final int size;

    private int emptyCellsCount;
    
    /**
     * Initializes a board.
     * <p>
     * {@link #MAX_SIZE} value will be used for the board size if the size param greater than {@link #MAX_SIZE}.
     * {@link #MIN_SIZE} will be used if the size param less than {@link #MIN_SIZE}.
     *
     * @param size     size of the board side
     */
    public Board(int size) {
        this.size = Math.max(MIN_SIZE, Math.min(size, MAX_SIZE));
        cells = new Marker[size][size];
        reset();
    }

    /**
     * Initializes a board by copying the other board.
     *
     * @param other     the board to copy
     */
    public Board(Board other) {
        size = other.size;
        emptyCellsCount = other.emptyCellsCount;
        cells = new Marker[size][size];
        for (int i = 0; i < size; i++) {
            cells[i] = Arrays.copyOf(other.cells[i], size);
        }
    }

    /**
     * Returns the marker at the specified position.
     *
     * @param row       the row number
     * @param col       the column number
     * @return the marker at the specified position
     */
    public Marker getMarkerAt(int row, int col) {
        return cells[row][col];
    }

    /**
     * Makes the player's move.
     *
     * @param move       player's move
     * @param player     current player
     */
    public void makeMove(Move move, Marker player) {
        cells[move.row()][move.col()] = player;
        emptyCellsCount--;
    }

    /**
     * Resets the state of the board.
     */
    public void reset() {
        emptyCellsCount = size * size;
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
     * Returns the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks all cells of the board and returns the list of all available moves.
     *
     * @return the list of all available moves
     */
    public List<Move> getAvailableMoves() {
        return IntStream.range(0, size)
            .boxed()
            .flatMap(row -> IntStream.range(0, size)
                .filter(col -> cells[row][col] == EMPTY)
                .mapToObj(col -> new Move(row, col)))
            .collect(toList());
    }

    /**
     * Returns {@code true} if the specified row of the board is filled with the same value.
     *
     * @param row       the row number
     * @return {@code true} if the row is filled with the same value
     */
    public boolean isRowFilled(int row) {
        return Arrays.stream(cells[row])
                .allMatch(cell -> cell == cells[row][0]);
    }

    /**
     * Returns {@code true} if the specified column of the board is filled with the same value.
     *
     * @param col       the column number
     * @return {@code true} if the column is filled with the same value
     */
    public boolean isColumnFilled(int col) {
        return IntStream.range(0, size)
                .mapToObj(i -> cells[i][col])
                .allMatch(cell -> cell == cells[0][col]);
    }

    /**
     * Returns {@code true} if the main diagonal of the board is filled with the same value.
     *
     * @return {@code true} if the main diagonal is filled with the same value
     */
    public boolean isMainDiagFilled() {
        return IntStream.range(0, size)
                .mapToObj(i -> cells[i][i])
                .allMatch(cell -> cell == cells[0][0]);
    }

    /**
     * Returns {@code true} if the side diagonal of the board is filled with the same value.
     *
     * @return {@code true} if the side diagonal is filled with the same value
     */
    public boolean isSideDiagFilled() {
        return IntStream.range(0, size)
                .mapToObj(i -> cells[i][size - 1 - i])
                .allMatch(cell -> cell == cells[0][size - 1]);
    }
}
