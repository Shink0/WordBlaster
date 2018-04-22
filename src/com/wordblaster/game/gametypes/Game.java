package com.wordblaster.game.gametypes;

import com.wordblaster.game.Word;

import java.util.List;

public interface Game {
    void maybeSpawnNewWord();
    void doFall();
    boolean isGameOver();
    boolean blastWord(String s);
    List<Word> getSpawnedWords();
    int getScore();
    int getMisses();
    int getMaxMisses();
}
