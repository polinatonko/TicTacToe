package org.example.tictactoe.logic;

import org.example.tictactoe.domain.*;

import java.util.Optional;

/**
 * TicTacToe game engine.
 */
public class GameEngine {
    private Board board;
    private Marker currentPlayer;
    private Mode mode;
    private MoveStrategy strategy;

    /**
     * Initializes the game.
     *
     * @param size      the size of the board
     * @param mode      the mode of the game
     * @param level     the level difficulty of the game (ignores if mode equals to HvH)
     */
    public GameEngine(int size, Mode mode, Level level) {
        this.board = new Board(size);
        this.mode = mode;
        this.currentPlayer = Marker.X;
        this.strategy = getStrategy(level);
    }

    /**
     * Performs the player's move and then changes to the next player.
     *
     * @param move      the player's move
     * @return {@code true} if the move was performed
     */
    public boolean makePlayerMove(Move move) {
        boolean moveDone = board.tryMakeMove(move, currentPlayer);
        if (moveDone) {
            currentPlayer = currentPlayer.getOpposite();
        }
        return moveDone;
    }

    /**
     * Generates and performs the computer's move, then changes the current player.
     *
     * @return {@code true} if the move was performed
     * @throws RuntimeException if the method was failed to generate a move
     */
    public boolean makeComputerMove() {
        var move = strategy.generateMove(board, currentPlayer)
                .orElseThrow(() -> new RuntimeException("Can't calculate computer's move"));
        return makePlayerMove(move);
    }

    /**
     * Returns {@code true} if game is in progress now.
     *
     * @return {@code true} if game is in progress now
     */
    public boolean isInProgress() {
        return board.getState() == State.NOT_OVER;
    }

    /**
     * Resets the state of the game. Used before starting the new game.
     */
    public void reset() {
        currentPlayer = Marker.X;
        board.reset();
    }

    /**
     * Returns the current state of the game.
     *
     * @return the state
     */
    public State getState() { return board.getState(); }

    /**
     * Returns the mode of the game.
     *
     * @return the mode
     */
    public Mode getMode() { return mode; }

    /**
     * Returns the current player of the game.
     *
     * @return the current player
     */
    public Marker getCurrentPlayer() { return currentPlayer; }

    /**
     * Returns the size of the game board.
     *
     * @return the size of the game board
     */
    public int getBoardSize() { return board.getSize(); }

    @Override
    public String toString() {
        return board.toString();
    }

    /**
     * Returns strategy to generate computer moves based on the
     * provided difficulty level.
     *
     * param level     the difficulty level of the game
     * @return {@code MoveStrategy} for generating computer moves
     * based on the difficulty level
     */
    private MoveStrategy getStrategy(Level level) {
         return switch (level) {
            case EASY -> new RandomMoveStrategy();
            case HARD -> new MinimaxMoveStrategy();
        };
    }
}