package com.comp1601.tictactoegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    /* Views */
    Button startGameButton;
    EditText player1SymbolInput;
    EditText player2SymbolInput;
    TextView userConsole;
    TextView player1Score;
    TextView player2Score;

    Button[][] buttonGrid = new Button[TicTacToeGame.SIZE][TicTacToeGame.SIZE];

    Player player1 = new Player('X');
    Player player2 = new Player('O');
    TicTacToeGame game = new TicTacToeGame(player1, player2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        /* Initialize views */
        startGameButton = findViewById(R.id.startGameButton);
        player1SymbolInput = findViewById(R.id.player1SymbolInput);
        player2SymbolInput = findViewById(R.id.player2SymbolInput);
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
        resetGameBoard();
        renderScores();

        // Add action listener to submit button
        startGameButton.setOnClickListener(button -> {
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show();
        });

    }


    /**
     * Handles button clicks from the Tic Tac Toe game
     * @param button the Button that got clicked
     */
    public void onGameButtonClicked(View button) {
        Log.i(TAG, "onGameButtonClicked: " + button.getTag() + " clicked");

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
            Log.i(TAG, "onGameButtonClicked: valid move");

            // Mark the button with the player's symbol
            buttonGrid[row][col].setText(player.getSymbolAsString());

            // Check for a win
            WinnerReport winnerReport = game.getWinner();
            if (winnerReport != null) {  // There's a winner!

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
                    resetGameBoard();
                    renderScores();
                    userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));
                }, 2000);
            } else if (game.isGridFilled()) {  // There is a draw!
                // Display Toast showing draw
                Toast.makeText(this,
                        getString(R.string.draw_toast),
                        Toast.LENGTH_LONG)
                        .show();

                // Reset game board in 2 seconds
                new Handler().postDelayed(() -> {
                    game.handleDraw();
                    resetGameBoard();
                    renderScores();
                    userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));
                }, 2000);
            } else {  // Nobody has won
                // Set userConsole to "It is X's turn"
                userConsole.setText(getString(R.string.user_console_turn, game.getTurn().getSymbol()));
            }

        }
        // Invalid button was clicked
        else {
            Log.i(TAG, "onGameButtonClicked: invalid move");
            userConsole.setText(getString(R.string.user_console_invalid, player.getSymbol()));
        }

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

    private void renderScores() {
        int score1 = game.getPlayer(0).getScore();
        int score2 = game.getPlayer(1).getScore();
        player1Score.setText(getString(R.string.player1_score_label, score1));
        player2Score.setText(getString(R.string.player2_score_label, score2));
    }

    public void resetGameBoard() {
        for (Button[] buttonRow : buttonGrid)
            for (Button button : buttonRow) {
                button.setText("");
                button.setBackgroundResource(android.R.drawable.btn_default);
            }
    }
}
