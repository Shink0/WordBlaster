package com.wordblaster.renderer;

import java.awt.Color;

public class DisplaySettings {

    private int resolution_h;
    private int resolution_w;
    private Color colorWordDefault;
    private Color colorWordTriggered;
    private Color colorWordFull;
    private Color colorMenuBackground;
    private Color colorMenuWord;
    private Color colorMenuNumbers;
    private Color colorMenuLabels;
    private Color colorBackground;

    public void setResolution_h(int resolution_h) {
        this.resolution_h = resolution_h;
    }

    public void setResolution_w(int resolution_w) {
        this.resolution_w = resolution_w;
    }

    public void setColorWordDefault(Color colorWordDefault) {
        this.colorWordDefault = colorWordDefault;
    }

    public void setColorWordTriggered(Color colorWordTriggered) {
        this.colorWordTriggered = colorWordTriggered;
    }

    public void setColorWordFull(Color colorWordFull) {
        this.colorWordFull = colorWordFull;
    }

    public void setColorMenuBackground(Color colorMenuBackground) {
        this.colorMenuBackground = colorMenuBackground;
    }

    public void setColorMenuWord(Color colorMenuWord) {
        this.colorMenuWord = colorMenuWord;
    }

    public void setColorMenuNumbers(Color colorMenuNumbers) {
        this.colorMenuNumbers = colorMenuNumbers;
    }

    public void setColorMenuLabels(Color colorMenuLabels) {
        this.colorMenuLabels = colorMenuLabels;
    }

    public void setColorBackground(Color colorBackground) {
        this.colorBackground = colorBackground;
    }

    public int getResolution_h() {
        return resolution_h;
    }

    public int getResolution_w() {
        return resolution_w;
    }

    public Color getColorWordDefault() {
        return colorWordDefault;
    }

    public Color getColorWordTriggered() {
        return colorWordTriggered;
    }

    public Color getColorWordFull() {
        return colorWordFull;
    }

    public Color getColorMenuBackground() {
        return colorMenuBackground;
    }

    public Color getColorMenuWord() {
        return colorMenuWord;
    }

    public Color getColorMenuNumbers() {
        return colorMenuNumbers;
    }

    public Color getColorMenuLabels() {
        return colorMenuLabels;
    }

    public Color getColorBackground() {
        return colorBackground;
    }

}
