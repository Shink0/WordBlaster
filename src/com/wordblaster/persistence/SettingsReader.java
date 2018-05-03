package com.wordblaster.persistence;

import com.wordblaster.game.GameSettings;
import com.wordblaster.renderer.DisplaySettings;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SettingsReader {

    public static DisplaySettings readDisplaySettings(File file) throws FileNotFoundException {
        DisplaySettings displaySettings = new DisplaySettings();
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line.contains("=")) {
                line = line.trim();
                String[] pair = line.split("=");
                String settingName = pair[0];
                String settingValue = pair[1];
                if (settingName.equals("resolution_h")) {
                    displaySettings.setResolution_h(Integer.parseInt(settingValue));
                } else if (settingName.equals("resolution_w")) {
                    displaySettings.setResolution_w(Integer.parseInt(settingValue));
                } else if (settingName.contains("color")) {
                    String[] rgb = settingValue.split(",");
                    int r = Integer.parseInt(rgb[0]);
                    int g = Integer.parseInt(rgb[1]);
                    int b = Integer.parseInt(rgb[2]);
                    Color color = new Color(r, g, b);
                    if (settingName.equals("color_word_default")) {
                        displaySettings.setColorWordDefault(color);
                    } else if (settingName.equals("color_word_triggered")) {
                        displaySettings.setColorWordTriggered(color);
                    } else if (settingName.equals("color_word_full")) {
                        displaySettings.setColorWordFull(color);
                    } else if (settingName.equals("color_background")) {
                        displaySettings.setColorBackground(color);
                    } else if (settingName.equals("color_menu_background")) {
                        displaySettings.setColorMenuBackground(color);
                    } else if (settingName.equals("color_menu_word")) {
                        displaySettings.setColorMenuWord(color);
                    } else if (settingName.equals("color_menu_numbers")) {
                        displaySettings.setColorMenuNumbers(color);
                    } else if (settingName.equals("color_menu_labels")) {
                        displaySettings.setColorMenuLabels(color);
                    }
                }
            }
        }
        sc.close();
        return displaySettings;
    }

    public static GameSettings readGameSettings(File file)throws FileNotFoundException {
        GameSettings gameSettings = new GameSettings();
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line.contains("=")) {
                line = line.trim();
                String[] pair = line.split("=");
                String settingName = pair[0];
                String settingValue = pair[1];
                if (settingName.equals("max_misses")) {
                    gameSettings.setMaxMisses(Integer.parseInt(settingValue));
                } else if (settingName.equals("initial_spawn_delay")){
                    gameSettings.setInitialSpawnDelay(Integer.parseInt(settingValue));
                } else if (settingName.equals("spawn_delay_reduction")){
                    gameSettings.setSpawnDelayReduction(Integer.parseInt(settingValue));
                } else if (settingName.equals("minimal_spawn_delay")){
                    gameSettings.setMinimalSpawnDelay(Integer.parseInt(settingValue));
                } else if (settingName.equals("spawn_x_min")){
                    gameSettings.setSpawnXMin(Double.parseDouble(settingValue));
                } else if (settingName.equals("spawn_x_max")){
                    gameSettings.setSpawnXMax(Double.parseDouble(settingValue));
                }
            }
        }
        sc.close();
        return gameSettings;
    }

}
