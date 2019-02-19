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
}
