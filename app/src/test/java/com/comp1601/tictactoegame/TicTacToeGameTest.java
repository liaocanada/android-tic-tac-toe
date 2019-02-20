package com.comp1601.tictactoegame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TicTacToeGameTest {

    private static Player player1;
    private static Player player2;
    private static TicTacToeGame game;

    @Before
    public void resetGame() {
        player1 = new Player('X');
        player2 = new Player('O');
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

        Assert.assertTrue(game.play(player1, 0, 0));
        String expected = "X| | \n | | \n | | \n";
        String actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertTrue(game.play(player2, 0, 2));
        expected = "X| |O\n | | \n | | \n";
        actual = game.toString();
        Assert.assertEquals(expected, actual);


        Assert.assertTrue(game.play(player1, 1, 1));
        expected = "X| |O\n |X| \n | | \n";
        actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertTrue(game.play(player2, 0, 1));
        expected = "X|O|O\n |X| \n | | \n";
        actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertNull(game.getWinnerReport());

        Assert.assertTrue(game.play(player1, 2, 2));
        expected = "X|O|O\n |X| \n | |X\n";
        actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertNotNull(game.getWinnerReport());
        Assert.assertEquals(WinnerReport.DIAGONAL, game.getWinnerReport().getWinType());
        Assert.assertEquals(WinnerReport.RIGHT_DIAGONAL, game.getWinnerReport().getIndex());
        Assert.assertEquals(player1, game.getWinnerReport().getWinner());

        game.handleWin(game.getWinnerReport());
        Assert.assertEquals(1, player1.getScore());
        Assert.assertEquals(0, player2.getScore());
    }

    @Test
    public void testPlayWithDraw() {

        Assert.assertTrue(game.play(player1, 0, 0));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player2, 0, 1));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player1, 0, 2));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player2, 1, 0));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player1, 1, 1));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player2, 1, 2));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player1, 2, 0));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player2, 2, 1));
        Assert.assertFalse(game.isGridFilled());

        Assert.assertTrue(game.play(player1, 2, 2));
        Assert.assertTrue(game.isGridFilled());

        String expected = "X|O|X\nO|X|O\nX|O|X\n";
        String actual = game.toString();
        Assert.assertEquals(expected, actual);

        game.handleDraw();
        Assert.assertEquals(1, player1.getScore());
        Assert.assertEquals(1, player2.getScore());

        expected = " | | \n | | \n | | \n";
        actual = game.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPlayWithOnePlayer() {

        Assert.assertTrue(game.play(player1, 2, 2));
        String expected = " | | \n | | \n | |X\n";
        String actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertFalse(game.play(player1, 0, 2));
        actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertFalse(game.play(player1, 1, 0));
        actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertFalse(game.play(player1, 1, 1));
        actual = game.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPlayingOnSameCell() {

        Assert.assertTrue(game.play(player1, 1, 2));
        String expected = " | | \n | |X\n | | \n";
        String actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertFalse(game.play(player2, 1, 2));
        actual = game.toString();
        Assert.assertEquals(expected, actual);

        Assert.assertFalse(game.play(player2, 1, 2));
        actual = game.toString();
        Assert.assertEquals(expected, actual);

    }


}
