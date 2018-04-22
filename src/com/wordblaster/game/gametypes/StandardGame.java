package com.wordblaster.game.gametypes;

import com.wordblaster.game.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StandardGame implements Game {

    private int maxMisses;
    private String[] words;
    private List<Word> spawnedWords = new ArrayList<Word>();
    private List<Word> deadWords = new ArrayList<Word>();;
    private int misses = 0;
    private int score = 0;
    private long spawnDelay= 1500;
    private long lastSpawnTime = System.currentTimeMillis();
    private double prevoisHorizontalLocation;

    public StandardGame(String[] words, int maxFaults) {
        this.words = words;
        this.maxMisses = maxFaults;
    }

    public void maybeSpawnNewWord() {
        if ((System.currentTimeMillis() - lastSpawnTime) >= spawnDelay) {
            Random r = new Random();
            int randIndex = r.nextInt(words.length);
            double rangeMin, rangeMax, randPos;
            do {
                rangeMin = 0.15;
                rangeMax = 0.85;
                randPos = rangeMin + (rangeMax - rangeMin) * r.nextDouble(); // Random double between 0.2 and 0.8 for horizontal position
            } while (!((prevoisHorizontalLocation - 0.3) <= randPos)
                        &&  !(randPos <= (prevoisHorizontalLocation + 0.3)));  // To avoid word overlapping
            Word w = new Word(words[randIndex], randPos);
            spawnedWords.add(w);
            lastSpawnTime = System.currentTimeMillis();
            prevoisHorizontalLocation = randPos;
            if (spawnDelay >= 1)
                spawnDelay -= 1;
        }
    }

    public void doFall() {
        for (Word w : spawnedWords) {
            w.increaseVerticalPosition(0.001);
            if (w.isDead()) {
                misses++;
                deadWords.add(w);
            }
        }
        removeDeadWords();
    }

    private void removeDeadWords() {
        for (Word w : deadWords) {
            spawnedWords.remove(w);
        }
        deadWords.clear();
    }

    public boolean isGameOver() {
        if (misses >= maxMisses)
            return true;
        else
            return false;
    }

    public boolean blastWord(String s) {
        boolean blasted = false;
        s = s.trim();
        for (Word w : spawnedWords) {
            if (w.equals(s)) {
                deadWords.add(w);
                score += w.lenght();
                blasted = true;
            }
        }
        removeDeadWords();
        return blasted;
    }

    public List<Word> getSpawnedWords() {
        return spawnedWords;
    }

    public int getScore() {
        return score;
    }

    public int getMisses() {
        return misses;
    }

    public int getMaxMisses() {
        return maxMisses;
    }


}
