package com.comp1601.tictactoegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button[][] buttonGrid = new Button[3][3];
    Player player1 = new Player('X');
    Player player2 = new Player('O');
    TicTacToeGame game = new TicTacToeGame(player1, player2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO find out how to put buttons in a matrix
        // Populate buttonGrid
        buttonGrid[0][0] = findViewById(R.id.button00);
        buttonGrid[0][1] = findViewById(R.id.button01);
        buttonGrid[0][2] = findViewById(R.id.button02);
        buttonGrid[1][0] = findViewById(R.id.button10);
        buttonGrid[1][1] = findViewById(R.id.button11);
        buttonGrid[1][2] = findViewById(R.id.button12);
        buttonGrid[2][0] = findViewById(R.id.button20);
        buttonGrid[2][1] = findViewById(R.id.button21);
        buttonGrid[2][2] = findViewById(R.id.button22);


    }


    /**
     * Handles button clicks from the Tic Tac Toe game
     * @param view the Button that got clicked
     */
    public void onGameButtonClicked(View view) {

        // Tag stores the row and column
//        String tag = (String)view.getTag();
//
////        game.play(player1, view.gett);
//
//        for (Button[] buttonColumn : buttonGrid)
//            for (Button button : buttonColumn)
//                if (view.getId() == button.getId())
//
//        R.id.
//
//        switch(view.getId()) {
//            case R.id.selection_button_1:
//                Log.i(TAG, "Radio button 1 clicked");
//                mSelections[mCurrentQuestionIndex] = 1;     // Update the mSelections array
//                mSelectionButton1.setChecked(true);         // Check the RadioButton
//                break;
//        }
    }
}
