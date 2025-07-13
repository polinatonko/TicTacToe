package org.example.tictactoe.ui;

import java.util.Map;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.Marker;
import org.example.tictactoe.domain.State;
import org.example.tictactoe.engine.GameEngine;

import static org.example.tictactoe.domain.State.DRAW;
import static org.example.tictactoe.domain.State.WIN_O;
import static org.example.tictactoe.domain.State.WIN_X;

/**
 * Helper class responsible for all console output operations in the Tic-Tac-Toe game.
 * <p>
 * It handles various types of output including welcome and game status messages, game board visualization, 
 * user prompts and input instructions, error messages and validation feedback, game statistics and results display.
 *
 * @see TicTacToeConsole
 */
public class TicTacToeConsolePrinter {

    public static final int WELCOME_MESSAGE_WIDTH = 30;
    public static final int STATISTICS_TABLE_WIDTH = 21;

    private TicTacToeConsolePrinter() {
        throw new IllegalStateException("TicTacToeConsolePrinter should not be instantiated!");
    }

    public static void printWelcomeMessage() {
        System.out.println("+" + "-".repeat(WELCOME_MESSAGE_WIDTH) + "+");
        System.out.printf("|%ds|\n".formatted(WELCOME_MESSAGE_WIDTH), "Welcome to Tic-Tac-Toe Game!");
        System.out.println("+" + "-".repeat(WELCOME_MESSAGE_WIDTH) + "+");
    }

    public static void printComputerMoveMessage() {
        System.out.print("Computer move: ");
    }

    public static void printNewGameMessage() {
        System.out.println("New game has just started!");
    }

    public static void printTryAgainMessage(String errorMessage) {
        System.out.println("%s! Try again:".formatted(errorMessage));
    }

    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printBoard(Board board) {
        var size = board.getSize();
        var sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("+-".repeat(size)).append("+\n");
            sb.append("|");
            for (int j = 0; j < size; j++) {
                sb.append(board.getMarkerAt(i, j)).append("|");
            }
            sb.append("\n");
        }
        sb.append("+-".repeat(size)).append("+");
        System.out.println(sb);
    }

    public static void printGameResult(GameEngine game) {
        printBoard(game.getBoard());
        var message = switch (game.getState()) {
            case IN_PROGRESS -> "Game still in progress!";
            case DRAW -> "Draw!";
            case WIN_X -> "Winner - X!";
            case WIN_O -> "Winner - O!";
        };
        System.out.println(message);
    }

    public static void printStatistics(Map<State, Integer> stats) {
        String sep = "+" + "-".repeat(STATISTICS_TABLE_WIDTH) + "+";
        System.out.println(sep);
        System.out.printf("| %-19s |\n", "Game Statistics");
        System.out.println(sep);
        System.out.printf("| %-7s | %-10s |\n", "Player", "Wins");
        System.out.println(sep);
        System.out.printf("| %-7s | %-10d |\n", "X", stats.get(WIN_X));
        System.out.printf("| %-7s | %-10d |\n", "O", stats.get(WIN_O));
        System.out.printf("| %-7s | %-10d |\n", "Draws", stats.get(DRAW));
        System.out.println(sep);
    }

    public static void askForMode() {
        System.out.println("Choose the game mode:\n1. HvH\n2. HvC");
    }

    public static void askForLevel() {
        System.out.println("Choose the game level:\n1. Easy\n2. Hard");
    }

    public static void askForBoardSize() {
        System.out.print("Enter the board size (positive integer > 2): ");
    }

    public static void askForPlayerMove(Marker player) {
        System.out.println("Enter player %s move: ".formatted(player));
    }

    public static void askToPlayAgain() {
        System.out.println("Do you want to play one more game? (Y/N):");
    }
} 
