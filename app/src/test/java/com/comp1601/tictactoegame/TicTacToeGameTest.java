package com.comp1601.tictactoegame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TicTacToeGameTest {

    private static Player player1;
    private static Player player2;
    private static TicTacToeGame game;

    @BeforeClass
    public static void initialize() {
        player1 = new Player('X');
        player2 = new Player('O');
    }

    @Before
    public void resetGame() {
        game = new TicTacToeGame(player1, player2);
    }

    @Test
    public void testPrintEmptyGame() {
        String expected = " | | \n | | \n | | \n";
        String actual = game.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPlayWithWinner() {

        boolean expected1 = true;
        boolean actual1 = game.play(player1, 0, 0);
        String expected2 = "X| | \n | | \n | | \n";
        String actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        expected1 = game.play(player2, 0, 2);
        actual1 = true;
        expected2 = "X| |O\n | | \n | | \n";
        actual2 = game.toString();
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected1, actual1);

        expected1 = game.play(player1, 1, 1);
        actual1 = true;
        expected2 = "X| |O\n |X| \n | | \n";
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        expected1 = game.play(player2, 0, 1);
        actual1 = true;
        expected2 = "X|O|O\n |X| \n | | \n";
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        expected1 = game.play(player1, 2, 2);
        actual1 = true;
        expected2 = "X|O|O\n |X| \n | |X\n";
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void testPlayWithOnePlayer() {

        boolean expected1 = true;
        boolean actual1 = game.play(player1, 2, 2);
        String expected2 = " | | \n | | \n | |X\n";
        String actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        actual1 = game.play(player1, 0, 2);
        expected1 = false;
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        actual1 = game.play(player1, 1, 0);
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        actual1 = game.play(player1, 1, 1);
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void testPlayingOnSameCell() {

        boolean expected1 = true;
        boolean actual1 = game.play(player1, 1, 2);
        String expected2 = " | | \n | |X\n | | \n";
        String actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        expected1 = false;
        actual1 = game.play(player2, 1, 2);
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

        actual1 = game.play(player1, 1, 2);
        actual2 = game.toString();
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

    }


}
