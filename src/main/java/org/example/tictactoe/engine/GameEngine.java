package org.example.tictactoe.engine;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Marker;
import org.example.tictactoe.domain.Level;
import org.example.tictactoe.domain.Move;
import org.example.tictactoe.domain.State;
import org.example.tictactoe.engine.exception.InvalidMoveException;
import org.example.tictactoe.engine.strategy.MoveStrategy;
import org.example.tictactoe.engine.strategy.MoveStrategyResolver;
import org.example.tictactoe.engine.validation.MoveValidator;

import static java.util.Objects.nonNull;

import static org.example.tictactoe.domain.Marker.X;
import static org.example.tictactoe.domain.State.IN_PROGRESS;
import static org.example.tictactoe.domain.State.WIN_X;
import static org.example.tictactoe.domain.State.WIN_O;
import static org.example.tictactoe.domain.State.DRAW;

/**
 * Represents the Tic-Tac-Toe game engine.
 */
public class GameEngine {

    private final Board board;
    private final MoveStrategyResolver moveStrategyResolver;
    private final MoveValidator moveValidator;

    private Marker currentPlayer;
    private MoveStrategy strategy;
    private State state;

    /**
     * Initializes the game.
     *
     * @param size      the size of the board
     * @param level     the level difficulty of the game (ignores if mode equals to HvH)
     */
    public GameEngine(int size, Level level) {
        this.board = new Board(size);
        this.currentPlayer = X;
        this.state = IN_PROGRESS;
        this.moveStrategyResolver = new MoveStrategyResolver();
        this.moveValidator = new MoveValidator(board);
        if (nonNull(level)) {
            this.strategy = moveStrategyResolver.resolve(level);
        }
    }

    /**
     * Initializes a game by copying the other game.
     *
     * @param other      the game to copy
     */
    public GameEngine(GameEngine other) {
        this.board = new Board(other.getBoard());
        this.currentPlayer = other.getCurrentPlayer();
        this.state = other.getState();
        this.strategy = other.strategy;
        this.moveStrategyResolver = new MoveStrategyResolver();
        this.moveValidator = new MoveValidator(board);
    }

    /**
     * Performs the player's move and then changes to the next player.
     *
     * @param move      the player's move
     * @throws InvalidMoveException if the move is invalid
     */
    public void makePlayerMove(Move move) throws InvalidMoveException {
        moveValidator.validate(move);
        makeMove(move);
    }

    /**
     * Generates and performs the computer's move, then changes the current player.
     *
     * @throws RuntimeException if the method was failed to generate a move
     */
    public void makeComputerMove() {
        var move = strategy.generateMove(this)
                .orElseThrow(() -> new RuntimeException("Can't calculate computer move"));
        makeMove(move);
    }

    /**
     * Returns {@code true} if game is in progress now.
     *
     * @return {@code true} if game is in progress now
     */
    public boolean isInProgress() {
        return state == IN_PROGRESS;
    }

    /**
     * Resets the state of the game. Used before starting the new game.
     */
    public void reset() {
        currentPlayer = X;
        state = IN_PROGRESS;
        board.reset();
    }

    /**
     * Returns the current state of the game.
     *
     * @return the state
     */
    public State getState() { 
        return state; 
    }

    /**
     * Returns the current player of the game.
     *
     * @return the current player
     */
    public Marker getCurrentPlayer() { 
        return currentPlayer; 
    }

    /**
     * Returns the board of the game.
     *
     * @return the board
     */
    public Board getBoard() { 
        return board; 
    }

    /**
     * Returns the size of the game board.
     *
     * @return the size of the game board
     */
    public int getBoardSize() { 
        return board.getSize(); 
    }

    private void makeMove(Move move) {
        board.makeMove(move, currentPlayer);
        updateState(move);
        currentPlayer = currentPlayer.getOpposite();
    }

    private void updateState(Move pos) {
        if (isWin(pos)) {
            state = (currentPlayer == X) ? WIN_X : WIN_O;
        } else if (board.isFilled()) {
            state = DRAW;
        }
    }

    private boolean isWin(Move pos) {
        int size = board.getSize();
        return board.isRowFilled(pos.row()) || board.isColumnFilled(pos.col())
                || (pos.row() == pos.col() && board.isMainDiagFilled())
                || (pos.row() == (size - pos.col() - 1) && board.isSideDiagFilled());
    }
} 
