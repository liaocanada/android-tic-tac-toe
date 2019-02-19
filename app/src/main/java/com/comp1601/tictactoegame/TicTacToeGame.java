package com.comp1601.tictactoegame;

import android.util.Log;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicTacToeGame {

    public static final int SIZE = 3;
    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);
    private Player[] players = new Player[2];
    private Player[][] grid = new Player[SIZE][SIZE];

    private Player startingPlayer;
    private Player turn;

    /**
     * Initializes a Tic Tac Toe game with two Players
     *
     * @param first  the Player that starts first
     * @param second the Player that goes second
     */
    public TicTacToeGame(Player first, Player second) {
        players[0] = first;
        players[1] = second;

        startingPlayer = players[0];
        turn = players[0];

        resetBoard();

    }


    /**
     * @param player
     * @param row
     * @param col
     * @return true if the move was valid and successful, false otherwise
     */
    public boolean play(Player player, int row, int col) {

        // Check if row or column are out of bounds
        if (row > SIZE || col > SIZE) return false;
        // Check if it is the right Player's turn
        if (!player.equals(turn)) return false;
        // Check if the specified grid cell is occupied
        if (grid[row][col] != null) return false;

        // Occupy the grid cell with player
        grid[row][col] = player;
        // Give turn to opponent
        turn = otherPlayer(player);

        // Print state of game board
        Log.i(TAG, "Board State: \n" + this);

        return true;
    }


    /**
     * Checks if a player has won.
     *
     * @return A WinnerReport containing details about the win, or null if neither Player has won.
     */
    public WinnerReport getWinner() {

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
        winnerReport.getWinner().incrementScore();

        // Swap players so that the other player starts
        startingPlayer = otherPlayer(startingPlayer);
        turn = startingPlayer;

        // Reset board state
        resetBoard();

    }

    public void handleDraw() {

        // Add 1 to both scores
        for (Player player : players) player.incrementScore();

        // Swap players so that the other player starts
        startingPlayer = otherPlayer(startingPlayer);
        turn = startingPlayer;

        resetBoard();
    }


    /**
     * @return whether or not the entire grid is filled
     */
    public boolean isGridFilled() {
        return !Arrays.stream(grid).parallel()
                .flatMap(Arrays::stream)
                .anyMatch(Objects::isNull);
    }

    private void resetBoard() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = null;
            }
    }


    private Player otherPlayer(Player thisPlayer) {
        if (players[0].equals(thisPlayer)) {
            return players[1];
        } else {
            return players[0];
        }
    }

    public Player getTurn() {
        return turn;
    }

    public Player getPlayer(int index) {
        return players[index];
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
