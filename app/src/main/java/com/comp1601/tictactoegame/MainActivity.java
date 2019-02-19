package com.comp1601.tictactoegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        savedInstanceState.put;
        // Player's Turn
        //

        Player x = new Player('X');
        Player o = new Player('O');

        TicTacToeGame game = new TicTacToeGame(x, o);
    }


    /**
     * Handles button clicks from the Tic Tac Toe game
     * @param view the Button that got clicked
     */
//    public void onGameButtonClicked(View view) {
//        R.id.
//
//        switch(view.getId()) {
//            case R.id.selection_button_1:
//                Log.i(TAG, "Radio button 1 clicked");
//                mSelections[mCurrentQuestionIndex] = 1;     // Update the mSelections array
//                mSelectionButton1.setChecked(true);         // Check the RadioButton
//                break;
//        }
//    }
}
