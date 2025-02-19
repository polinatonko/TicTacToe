package org.example.tictactoe.ui;

import org.example.tictactoe.logic.GameEngine;
import org.example.tictactoe.domain.Level;
import org.example.tictactoe.domain.Mode;
import org.example.tictactoe.domain.Move;
import org.example.tictactoe.domain.State;

import java.util.*;

/**
 * Tic-Tac-Toe console interface.
 */
public class TicTacToeConsole {
    private GameEngine game;
    private Map<State, Integer> stats;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Initializes the games' statistics map.
     */
    public TicTacToeConsole() {
        stats = new EnumMap<>(State.class);
        for (var state : State.values()) {
            stats.put(state, 0);
        }
    }

    /**
     * Starts the game cycle.
     */
    public void start() {
        printHelloMessage();

        Mode mode = getMode();
        int size = getBoardSize();
        Level level = (mode == Mode.HVC) ? getLevel() : null;
        game = new GameEngine(size, mode, level);

        boolean playGame = true;
        while (playGame) {
            switch (game.getMode()) {
                case HVH -> humanVersusHuman();
                case HVC -> humanVersusComputer();
            }

            stats.compute(game.getState(), (state, count) -> count + 1);
            printGameResult();
            playGame = retry();
        }

        printStatistics();
    }

    private void printHelloMessage() {
        System.out.println("+" + "-".repeat(30) + "+");
        System.out.printf("|%30s|\n", "Welcome to Tic-Tac-Toe Game!");
        System.out.println("+" + "-".repeat(30) + "+");
    }

    /**
     * Reads the game mode from the console
     * until a valid integer value is entered.
     *
     * @return the mode of the game
     */
    private Mode getMode() {
        System.out.println("Choose the game mode:\n1. HvH\n2. HvC");
        String mode;
        do {
            mode = scanner.nextLine().trim().toLowerCase();
        } while (!mode.equals("1") && !mode.equals("2"));

        return switch (mode) {
            case "1" -> Mode.HVH;
            case "2" -> Mode.HVC;
            default -> throw new RuntimeException("Unknown game mode!");
        };
    }

    /**
     * Reads the game difficulty level from the console
     * until a valid integer value is entered.
     *
     * @return the difficulty level of the game
     */
    private Level getLevel() {
        System.out.println("Choose the game level:\n1. Easy\n2. Hard");
        String level;
        do {
            level = scanner.nextLine().trim().toLowerCase();
        } while (!level.equals("1") && !level.equals("2"));

        return switch (level) {
            case "1" -> Level.EASY;
            case "2" -> Level.HARD;
            default -> throw new RuntimeException("Unknown level!");
        };
    }

    /**
     * Reads the board size from the console until
     * a valid integer value greater than 2 is entered.
     *
     * @return the size of the board
     */
    private int getBoardSize() {
        int size = -1;

        while (size < 3) {
            System.out.print("Enter the board size (positive integer > 2): ");
            try {
                size = scanner.nextInt();
                if (size < 3) {
                    System.out.println("Board size should be a positive integer greater than 2! Try again:");
                }
            } catch (InputMismatchException ex) {
                scanner.next();
                System.out.println("Board size should be an integer! Try again:");
            }
        }

        return size;
    }

    /**
     * Plays Human Versus Human mode.
     */
    private void humanVersusHuman() {
        while (game.inProgress()) {
            System.out.println(game);
            playerMove();
        }
    }

    /**
     * Plays Human Versus Computer mode.
     */
    private void humanVersusComputer() {
        while (game.inProgress()) {
            System.out.println(game);
            playerMove();
            if (game.inProgress()) {
                System.out.println(game);
                System.out.println("Computer move:");
                computerMove();
            }
        }
    }

    /**
     * Inputs player's move from the console
     * and then performs this move.
     */
    private void playerMove() {
        Move move = inputMove();
        while (!game.playerMove(move)) {
            System.out.println("Cell [" + move.row() + ", " + move.col() + "] isn't empty.");
            move = inputMove();
        }
    }

    /**
     * Reads the player's move from the console until it is entered in the correct format
     * (integers for row and column separated by comma) and within the correct bounds (1..size).
     *
     * @return the player's move
     */
    private Move inputMove() {
        var player = game.getCurrentPlayer();
        System.out.println("Enter player " + player +" move:");
        Move move = null;
        boolean isInputValid = false;
        while (!isInputValid) {
            try {
                move = new Move(scanner.nextInt(), scanner.nextInt());
                if (move.row() > 0 && move.row() <= game.getBoardSize()
                        && move.col() > 0 && move.col() <= game.getBoardSize()) {
                    isInputValid = true;
                } else {
                    System.out.println("Row and column should be in the [1.." + game.getBoardSize() + "] bounds!");
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("Invalid position! Try again:");
            }
            finally {
                scanner.nextLine();
            }
        }
        return move;
    }

    /**
     * Performs computer move.
     */
    private void computerMove() {
        game.computerMove();
    }

    private void printGameResult() {
        System.out.println(game);
        var message = switch (game.getState()) {
            case NOT_OVER -> "Game still in progress!";
            case DRAW -> "Draw!";
            case WIN_X -> "Winner - X!";
            case WIN_O -> "Winner - O!";
        };
        System.out.println(message);
    }

    /**
     * Resets the game state if the user wants to play the game again.
     *
     * @return {@code true} if the game have restarted.
     */
    private boolean retry() {
        if (inputTryAgain()) {
            game.reset();
            System.out.println("New game has just started!");
            return true;
        }
        return false;
    }

    /**
     * Asks the user if he wants to play another game.
     *
     * @return {@code true} if the user chose to start the game again.
     */
    private boolean inputTryAgain() {
        while (true) {
            System.out.println("Do you want to play one more game? (Y/N):");
            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Incorrect input! Please, try again:");
        }
    }

    /**
     * Prints statistics of the games in the current session:
     * the number of draws and wins for each player.
     */
    private void printStatistics() {
        String sep = "+" + "-".repeat(21) + "+";
        System.out.println(sep);
        System.out.printf("|%-21s|%n", "Games Statistics:");
        System.out.println(sep);

        String template = "|%-10s|%10s|%n";
        for (var state : State.values()) {
            if (state != State.NOT_OVER) {
                System.out.printf(template, state + ": ", stats.get(state));
                System.out.println(sep);
            }
        }
    }
}