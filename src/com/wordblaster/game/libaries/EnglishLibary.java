package com.wordblaster.game.libaries;

import java.io.FileNotFoundException;

public class EnglishLibary extends Libary {

    public EnglishLibary() {
        try {
            super.readFile("libaries/ENGLISH.txt");
        } catch (FileNotFoundException e) {
            System.err.print(e.getMessage());
        }
    }

}
