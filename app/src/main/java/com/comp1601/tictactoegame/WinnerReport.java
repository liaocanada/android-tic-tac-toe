package com.comp1601.tictactoegame;

import android.util.Log;

public final class WinnerReport {

    public static final int LEFT_DIAGONAL = 2;

    public static final char ROW = 'r';
    public static final char COLUMN = 'c';
    public static final char DIAGONAL = 'x';
    public static final int RIGHT_DIAGONAL = 1;
    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    /**
     * The Player that won
     */
    private final Player winner;

    /**
     * The type of win
     * 'r' - row
     * 'c' - column
     * 'x' - diagonal
     */
    private final char winType;

    /**
     * The row/column/diagonal index of the win
     * For diagonals, 1 represents a right diagonal (\) and 2 represents a left diagonal (/)
     * E.g. winType 'r' and index '1' means the winner won on the middle row
     */
    private final int index;

    public WinnerReport(Player winner, char winType, int index) {
        Log.i(TAG, "WinnerReport: new report created with winner="
                + winner + ", winType=" + winType + ", index=" + index);
        if (winner == null) throw new IllegalArgumentException("Winner cannot be null");
        if (!(winType == ROW || winType == COLUMN || winType == DIAGONAL))
            throw new IllegalArgumentException("winType must be one of: 'r', 'c', or 'd'.");
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("index must between 0 and 2");

        this.winner = winner;
        this.winType = winType;
        this.index = index;
    }

    public Player getWinner() {
        return winner;
    }

    public char getWinType() {
        return winType;
    }

    public int getIndex() {
        return index;
    }
}
