package com.comp1601.tictactoegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    /* Views */
    private TextView userConsole;
    private TextView player1Score;
    private TextView player2Score;
    private Button[][] buttonGrid = new Button[TicTacToeGame.SIZE][TicTacToeGame.SIZE];

    private boolean isSinglePlayerMode = true;

    private Player player1 = new Player('X');
    private Player player2 = new Player('O');
    private TicTacToeGame game = new TicTacToeGame(player1, player2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        /* Initialize views */
        userConsole = findViewById(R.id.userConsole);
        player1Score = findViewById(R.id.player1ScoreLabel);
        player2Score = findViewById(R.id.player2ScoreLabel);
        buttonGrid[0][0] = findViewById(R.id.button00); // TODO find out how to put buttons in a matrix
        buttonGrid[0][1] = findViewById(R.id.button01);
        buttonGrid[0][2] = findViewById(R.id.button02);
        buttonGrid[1][0] = findViewById(R.id.button10);
        buttonGrid[1][1] = findViewById(R.id.button11);
        buttonGrid[1][2] = findViewById(R.id.button12);
        buttonGrid[2][0] = findViewById(R.id.button20);
        buttonGrid[2][1] = findViewById(R.id.button21);
        buttonGrid[2][2] = findViewById(R.id.button22);


        // Set userConsole to "It is X's turn"
        userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));
        renderGameBoard();
        renderScores();

    }


    /**
     * Handles button clicks from the Tic Tac Toe game
     * @param button the Button that got clicked
     */
    public void onGameButtonClicked(View button) {
        Log.i(TAG, "onGameButtonClicked: " + button.getTag() + " clicked...");

        // Get row and column of where the button was clicked
        String buttonTag = (String) button.getTag();
        if (buttonTag == null) {
            throw new IllegalStateException("Button " + button.getId() +
                    "has no associated tag indicating its row and column.");
        }
        int row = Integer.parseInt(buttonTag.substring(6, 7));
        int col = Integer.parseInt(buttonTag.substring(7, 8));

        // Get the current player
        Player player = game.getTurn();

        // Try to play at the specified location
        boolean success = game.play(player, row, col);


        if (success) {
            Log.i(TAG, "onGameButtonClicked: A valid move");

            // Mark the button with the player's symbol
            buttonGrid[row][col].setText(player.getSymbolAsString());

            WinnerReport winnerReport = game.getWinnerReport();

            // There's a winner!
            if (winnerReport != null) {

                // Disable buttons temporarily
                enableGameBoard(false);

                // Colour the winning buttons
                highlightWinningButtons(winnerReport);

                // Display Toast showing winner
                char winnerSymbol = winnerReport.getWinner().getSymbol();
                Toast.makeText(this,
                        getString(R.string.winner_toast, winnerSymbol),
                        Toast.LENGTH_LONG)
                        .show();

                // Reset game board in 2 seconds
                new Handler().postDelayed(() -> {
                    game.handleWin(winnerReport);
                    renderGameBoard();
                    renderScores();
                    userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));

                    // PC's turn in single player mode
                    if (isSinglePlayerMode && game.isComputersTurn())
                        clickRandomValidButton();

                }, 2000);

                // There is a draw!
            } else if (game.isGridFilled()) {

                // Disable buttons temporarily
                enableGameBoard(false);

                // Display Toast showing draw
                Toast.makeText(this,
                        getString(R.string.draw_toast),
                        Toast.LENGTH_LONG)
                        .show();

                // Reset game board in 2 seconds
                new Handler().postDelayed(() -> {
                    game.handleDraw();
                    renderGameBoard();
                    renderScores();
                    userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));

                    // PC's turn in single player mode
                    if (isSinglePlayerMode && game.isComputersTurn())
                        clickRandomValidButton();

                }, 2000);

                // Nobody has won
            } else {
                // Set userConsole to "It is X's turn"
                userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));

                // PC's turn in single player mode
                if (isSinglePlayerMode && game.isComputersTurn())
                    clickRandomValidButton();

            }

        }
        // Invalid button was clicked
        else {
            Log.i(TAG, "onGameButtonClicked: An invalid move");
            userConsole.setText(getString(R.string.user_console_invalid, player.getSymbol()));
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState(outState)");
        outState.putSerializable("player1", player1);
        outState.putSerializable("player2", player2);
        outState.putSerializable("game", game);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState(savedInstanceState)");
        player1 = (Player) savedInstanceState.getSerializable("player1");
        player2 = (Player) savedInstanceState.getSerializable("player2");
        game = (TicTacToeGame) savedInstanceState.getSerializable("game");

        // Update UI
        userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));
        renderGameBoard();
        renderScores();

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void highlightWinningButtons(WinnerReport winnerReport) {

        // Highlight winning buttons
        switch (winnerReport.getWinType()) {
            case WinnerReport.ROW:
                for (int col = 0; col < game.SIZE; col++) {
                    int row = winnerReport.getIndex();
                    buttonGrid[row][col].setBackgroundColor(getColor(R.color.colorAccent));
                }
                break;

            case WinnerReport.COLUMN:
                for (int row = 0; row < game.SIZE; row++) {
                    int col = winnerReport.getIndex();
                    buttonGrid[row][col].setBackgroundColor(getColor(R.color.colorAccent));
                }
                break;

            case WinnerReport.DIAGONAL:
                if (winnerReport.getIndex() == WinnerReport.LEFT_DIAGONAL) {
                    buttonGrid[0][2].setBackgroundColor(getColor(R.color.colorAccent));
                    buttonGrid[1][1].setBackgroundColor(getColor(R.color.colorAccent));
                    buttonGrid[2][0].setBackgroundColor(getColor(R.color.colorAccent));
                } else if (winnerReport.getIndex() == WinnerReport.RIGHT_DIAGONAL) {
                    buttonGrid[0][0].setBackgroundColor(getColor(R.color.colorAccent));
                    buttonGrid[1][1].setBackgroundColor(getColor(R.color.colorAccent));
                    buttonGrid[2][2].setBackgroundColor(getColor(R.color.colorAccent));
                }
        }

    }


    private void clickRandomValidButton() {

        // Disable all buttons so player can't click while computer is "thinking"
        enableGameBoard(false);

        // Generate random valid button to click
        int randomRow = (int) (3 * Math.random());
        int randomCol = (int) (3 * Math.random());

        String randomButtonText = buttonGrid[randomRow][randomCol].getText().toString();
        while (!randomButtonText.equals("")) {
            randomRow = (int) (3 * Math.random());
            randomCol = (int) (3 * Math.random());
            randomButtonText = buttonGrid[randomRow][randomCol].getText().toString();
        }

        // To be used inside lambda
        final int finalRow = randomRow;
        final int finalCol = randomCol;

        // Click button in 0.75 seconds
        new Handler().postDelayed(() -> {
            enableGameBoard(true);
            buttonGrid[finalRow][finalCol].performClick();
        }, 750);

    }

    private void renderScores() {
        int score1 = game.getPlayerScore();
        int score2 = game.getComputerScore();
        player1Score.setText(getString(R.string.player1_score_label, score1));
        player2Score.setText(getString(R.string.player2_score_label, score2));
    }

    public void renderGameBoard() {
        for (int i = 0; i < buttonGrid.length; i++) {
            for (int j = 0; j < buttonGrid[i].length; j++) {
                String buttonSymbol = game.getGridSymbol(i, j);
                buttonGrid[i][j].setText(buttonSymbol);

                buttonGrid[i][j].setBackgroundResource(android.R.drawable.btn_default);
                buttonGrid[i][j].setEnabled(true);
            }
        }
    }

    public void enableGameBoard(boolean enable) {
        for (Button[] buttonRow : buttonGrid)
            for (Button button : buttonRow) {
                button.setEnabled(enable);
            }
    }
}
