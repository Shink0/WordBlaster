package com.wordblaster.game;

public class Word {
    private double verticalPosition = 0.0;
    private double horizontalPosition;
    private boolean dead = false;
    private String word;


    public Word(String word, double horizontalPosition) {
        this.word = word;
        this.horizontalPosition = horizontalPosition;
    }

    public void increaseVerticalPosition(double amount) {
        verticalPosition += amount;
        if (verticalPosition >= 1.0)
            dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean equals(String s){
        if (word.equals(s)){
            return true;
        } else {
            return false;
        }
    }

    public int lenght() {
        return word.length();
    }

    public String toString() {
        return word;
    }

    /**
     * Value from 0 to 1, that describes percantage horizontal position from left to right
     * 1 = max to the left
     * 0 = max to the right
    */
    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    /**
     * Value from 0 to 1, that describes percantage vertical position from left to right
     * 1 = bottom
     * 0 = top
    */
    public double getVerticalPosition() {
        return verticalPosition;
    }


}
