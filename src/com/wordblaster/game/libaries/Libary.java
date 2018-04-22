package com.wordblaster.game.libaries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Libary implements WordLibary {

    protected List<String> words = new ArrayList<String>();

    protected void readFile(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath));
        while (sc.hasNext()) {
            words.add(sc.next());
        }
    }

    public String[] getWords() {
        return  Arrays.copyOf(words.toArray(), words.toArray().length, String[].class);
    }

}
