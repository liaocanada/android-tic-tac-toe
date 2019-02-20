package com.comp1601.tictactoegame;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicTacToeGame implements Serializable {

    /* Constants */
    public static final int SIZE = 3;
    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    private Player[][] grid = new Player[SIZE][SIZE];
    private Player player;
    private Player computer;
    private Player turn;

    private int xScore;
    private int oScore;


    /**
     * Initializes a Tic Tac Toe game with two default Players
     */
    public TicTacToeGame() {
        this(new Player('X'), new Player('O'));
    }

    /**
     * Initializes a Tic Tac Toe game with two Players
     * @param player1 the player
     * @param player2 the computer
     */
    public TicTacToeGame(Player player1, Player player2) {
        player = player1;
        computer = player2;

        newGame();
    }

    /**
     * Clears all board state and gives the turn to the player with the highest ASCII value symbol
     */
    private void newGame() {
        // Set the turn to the player with the highest ASCII symbol
        turn = (player.getSymbol() == 'X') ? player : computer;

        // Set the grid to empty
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j] = null;

    }


    /** Attempts to place a Player's symbol at the specified location.
     * @param player the Player that clicked the tic-tac-toe spot
     * @param row the row of the tic-tac-toe spot
     * @param col the column of the tic-tac-toe spot
     * @return true if the move was valid and successful, false otherwise
     */
    public boolean play(Player player, int row, int col) {

        // Check if row or column are out of bounds
        if (row > SIZE || col > SIZE) {
            System.out.println("Selected row=" + row + " and col=" + col + " is out of bounds");
            return false;
        }
        // Check if it is the right Player's turn
        if (!player.equals(turn)) {
            System.out.println("It is the wrong player's turn");
            return false;
        }
        // Check if the specified grid cell is occupied
        if (grid[row][col] != null) {
            System.out.println("The button has already been clicked by " + grid[row][col]);
            return false;
        }

        // Occupy the grid cell with player
        grid[row][col] = player;
        // Give turn to opponent
        turn = getOtherPlayer(player);

        // Print state of game board
        Log.i(TAG, "Board State: \n" + this);
        System.out.println(this);

        return true;
    }


    /**
     * Checks if a player has won.
     * @return A WinnerReport containing details about the win, or null if no Player has won.
     */
    public WinnerReport getWinnerReport() {

        // Check rows
        for (int i = 0; i < grid.length; i++)
            if (grid[i][0] != null && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2])
                return new WinnerReport(grid[i][0], WinnerReport.ROW, i);

        // Check columns
        for (int j = 0; j < grid[0].length; j++)
            if (grid[0][j] != null && grid[0][j] == grid[1][j] && grid[1][j] == grid[2][j])
                return new WinnerReport(grid[0][j], WinnerReport.COLUMN, j);

        // Check / diagonal
        if (grid[2][0] != null && grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2])
            return new WinnerReport(grid[2][0], WinnerReport.DIAGONAL, WinnerReport.LEFT_DIAGONAL);

        // Check \ diagonal
        if (grid[0][0] != null && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
            return new WinnerReport(grid[0][0], WinnerReport.DIAGONAL, WinnerReport.RIGHT_DIAGONAL);


        // No winner
        return null;

    }


    public void handleWin(WinnerReport winnerReport) {

        // Add 1 to score
        if (winnerReport.getWinner().getSymbol() == 'X') {
            xScore++;
        } else oScore++;

        // Swap symbols
        swapSymbols();

        // Reset turns and board state
        newGame();

    }


    /**
     * @return whether or not the entire grid is filled (i.e. in a draw state)
     */
    public boolean isGridFilled() {
        boolean gridHasNull = Arrays.stream(grid).parallel()
                .flatMap(Arrays::stream)
                .anyMatch(Objects::isNull);
        return !gridHasNull;
    }

    public void handleDraw() {
        // Add 1 to both scores
        xScore++;
        oScore++;

        // Swap player and computer symbols
        swapSymbols();
        newGame();
    }


    private void swapSymbols() {
        char tempSymbol = player.getSymbol();
        player.setSymbol(computer.getSymbol());
        computer.setSymbol(tempSymbol);
    }

    private Player getOtherPlayer(Player thisPlayer) {
        if (thisPlayer.equals(player)) {
            return computer;
        } else {
            return player;
        }
    }


    /* Getters */
    public boolean isComputersTurn() {
        return turn.equals(computer);
    }

    public Player getTurn() {
        return turn;
    }

    public String getGridSymbol(int row, int col) {
        if (grid[row][col] == null) return "";
        return grid[row][col].getSymbolAsString();
    }

    public int getXScore() {
        return xScore;
    }

    public int getOScore() {
        return oScore;
    }

    @Override
    public String toString() {
        String gridAsString = "";

        for (Player[] gridRow : grid) {
            // Get the symbol of each player in a row, then collect it into a String
            // with | as the delimiter between each symbol
            String rowRepresentation = Arrays.stream(gridRow)
                    .map(player -> {
                        if (player == null) return " ";
                        else return player.getSymbolAsString();
                    })
                    .collect(Collectors.joining("|"));

            // Add this row to the String
            gridAsString += rowRepresentation + "\n";
        }

        return gridAsString;

    }

}
