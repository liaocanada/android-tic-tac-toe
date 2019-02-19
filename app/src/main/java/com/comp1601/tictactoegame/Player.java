package com.comp1601.tictactoegame;

import java.util.ArrayList;
import java.util.List;

public class Player {

    /**
     * Increments by one every time a new Player is made, so that Players have unique IDs
     */
    private static byte idCount = 0;

    /**
     * Stores which symbols have been used, so that each Player has an unique symbol
     */
    private static List<Character> takenSymbols = new ArrayList<>();


    /**
     * The unique ID of a Player
     */
    private final int id;

    /**
     * The unique symbol of a Player
     */
    private final char symbol;

    /**
     * The number of times this player has won
     */
    private int score = 0;


    /**
     * Creates a new player object with the given symbol.
     *
     * @param symbol A unique symbol that is displayed on the TicTacToe board
     * @throws IllegalArgumentException if the symbol has already been taken by another Player.
     */
    public Player(char symbol) throws IllegalArgumentException {
        this.id = idCount++;

        if (!takenSymbols.contains(symbol)) {
            this.symbol = symbol;
            takenSymbols.add(symbol);
        } else {
            throw new IllegalArgumentException("A player with symbol '" + symbol + "' already exists.");
        }
    }


    /* Getters */
    public char getSymbol() {
        return this.symbol;
    }

    public String getSymbolAsString() {
        return String.valueOf(this.symbol);
    }

    public int getId() {
        return this.id;
    }

    public int getScore() {
        return this.score;
    }

    /* Setters */
    public void incrementScore() {
        score++;
    }


    @Override
    public String toString() {
        return "Player " + getSymbol() + " (" + this.id + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) return false;

        Player otherPlayer = (Player) other;

        return this.getId() == otherPlayer.getId();
    }

}
