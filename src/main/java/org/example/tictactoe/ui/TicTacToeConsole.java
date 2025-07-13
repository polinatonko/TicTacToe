package org.example.tictactoe.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.example.tictactoe.engine.GameEngine;
import org.example.tictactoe.domain.Level;
import org.example.tictactoe.domain.Mode;
import org.example.tictactoe.domain.Move;
import org.example.tictactoe.engine.exception.InvalidMoveException;
import org.example.tictactoe.service.GameStatisticsService;

import static org.example.tictactoe.domain.Mode.HVC;
import static org.example.tictactoe.domain.Mode.HVH;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printWelcomeMessage;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printGameResult;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printStatistics;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.askForMode;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.askForLevel;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.askForBoardSize;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printTryAgainMessage;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printBoard;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printError;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printComputerMoveMessage;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.askForPlayerMove;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.printNewGameMessage;
import static org.example.tictactoe.ui.TicTacToeConsolePrinter.askToPlayAgain;

/**
 * Represents the Tic-Tac-Toe console interface.
 */
public class TicTacToeConsole {

    private final GameStatisticsService gameStatisticsService;
    private final Scanner scanner = new Scanner(System.in);

    private GameEngine game;
    private Mode mode;

    /**
     * Initializes the game statistics map.
     */
    public TicTacToeConsole() {
        gameStatisticsService = new GameStatisticsService();
    }

    /**
     * Starts the game cycle.
     */
    public void start() {
        printWelcomeMessage();

        this.mode = getMode();
        var size = getBoardSize();
        Level level = (mode == HVC) ? getLevel() : null;
        game = new GameEngine(size, level);

        var playGame = true;
        while (playGame) {
            switch (mode) {
                case HVH -> playHumanVersusHuman();
                case HVC -> playHumanVersusComputer();
            }

            gameStatisticsService.recordGameOutcome(game.getState());
            printGameResult(game);
            playGame = retry();
        }

        printStatistics(gameStatisticsService.getStatistics());
    }

    private Mode getMode() {
        askForMode();
        String mode;
        do {
            mode = scanner.nextLine().trim().toLowerCase();
        } while (!mode.equals("1") && !mode.equals("2"));

        return switch (mode) {
            case "1" -> HVH;
            case "2" -> HVC;
            default -> throw new RuntimeException("Unknown game mode!");
        };
    }

    private Level getLevel() {
        askForLevel();
        String level;
        do {
            level = scanner.nextLine().trim().toLowerCase();
        } while (!level.equals("1") && !level.equals("2"));

        return switch (level) {
            case "1" -> Level.EASY;
            case "2" -> Level.HARD;
            default -> throw new RuntimeException("Unknown level (1/2 expected)!");
        };
    }

    private int getBoardSize() {
        int size = -1;

        while (size < 3) {
            askForBoardSize();
            try {
                size = scanner.nextInt();
                if (size < 3) {
                    printTryAgainMessage("Board size should be a positive integer greater than 2");
                }
            } catch (InputMismatchException ex) {
                scanner.next();
                printTryAgainMessage("Board size should be an integer");
            }
        }

        return size;
    }

    private void playHumanVersusHuman() {
        while (game.isInProgress()) {
            printBoard(game.getBoard());
            makePlayerMove();
        }
    }

    private void playHumanVersusComputer() {
        var board = game.getBoard();
        while (game.isInProgress()) {
            printBoard(board);
            makePlayerMove();
            if (game.isInProgress()) {
                printBoard(board);
                makeComputerMove();
            }
        }
    }

    private void makePlayerMove() {
        do {
            try {
                var move = inputMove();
                game.makePlayerMove(move);
                break;
            } catch (InvalidMoveException ex) {
                printError(ex.getMessage());
            }
        } while (true);
    }

    private void makeComputerMove() {
        printComputerMoveMessage();
        game.makeComputerMove();
    }

    private Move inputMove() {
        askForPlayerMove(game.getCurrentPlayer());
        while (true) {
            try {
                return new Move(scanner.nextInt() - 1, scanner.nextInt() - 1);
            }
            catch (InputMismatchException ex) {
                printTryAgainMessage("Invalid move position");
            } finally {
                scanner.nextLine();
            }
        }
    }
    
    private boolean retry() {
        if (inputTryAgain()) {
            game.reset();
            printNewGameMessage();
            return true;
        }
        return false;
    }

    private boolean inputTryAgain() {
        while (true) {
            askToPlayAgain();
            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            }
            printTryAgainMessage("Incorrect input (y/n expected)");
        }
    }
}
