package com.wordblaster.game;

public class GameSettings {
    private int maxMisses;
    private int initialSpawnDelay;
    private int spawnDelayReduction;
    private int minimalSpawnDelay;
    private double spawnXMin;   // 0.0 - 1.0 of screen
    private double spawnXMax;

    public int getMaxMisses() {
        return maxMisses;
    }

    public void setMaxMisses(int maxMisses) {
        this.maxMisses = maxMisses;
    }

    public int getInitialSpawnDelay() {
        return initialSpawnDelay;
    }

    public void setInitialSpawnDelay(int initialSpawnDelay) {
        this.initialSpawnDelay = initialSpawnDelay;
    }

    public int getSpawnDelayReduction() {
        return spawnDelayReduction;
    }

    public void setSpawnDelayReduction(int spawnDelayReduction) {
        this.spawnDelayReduction = spawnDelayReduction;
    }

    public int getMinimalSpawnDelay() {
        return minimalSpawnDelay;
    }

    public void setMinimalSpawnDelay(int minimalSpawnDelay) {
        this.minimalSpawnDelay = minimalSpawnDelay;
    }

    public double getSpawnXMin() {
        return spawnXMin;
    }

    public void setSpawnXMin(double spawnXMin) {
        this.spawnXMin = spawnXMin;
    }

    public double getSpawnXMax() {
        return spawnXMax;
    }

    public void setSpawnXMax(double spawnXMax) {
        this.spawnXMax = spawnXMax;
    }

}
