package com.wordblaster.menu;

import com.wordblaster.game.Game;
import com.wordblaster.game.GameSettings;
import com.wordblaster.game.gametypes.StandardGame;
import com.wordblaster.persistence.SettingsReader;
import com.wordblaster.renderer.DisplaySettings;
import com.wordblaster.renderer.GameRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {

    private JFrame frame;
    private JComboBox<String> libraryComboBox = new JComboBox<String>();
    private JButton playButton = new JButton("PLAY");
    private JRadioButton libraryRadioButton1 = new JRadioButton();
    private JRadioButton libraryRadioButton2 = new JRadioButton();
    private ButtonGroup libraryBG = new ButtonGroup();
    private JTextField libraryCustomPathTF = new JTextField("", 20);
    private JButton libraryCustomPathButton = new JButton("...");
    private String selectedLibraryFilePath;
    private DisplaySettings displaySettings;
    private GameSettings gameSettings;

    public Launcher() {
        try {
            initializeComponents();
            initializeSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        frame = new JFrame("Word Blaster");
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(700, 60));
        panel.setLayout(new FlowLayout());

        libraryBG.add(libraryRadioButton1);
        libraryBG.add(libraryRadioButton2);
        libraryRadioButton1.setSelected(true);
        libraryCustomPathTF.setEnabled(false);
        libraryCustomPathButton.setEnabled(false);
        libraryCustomPathButton.setPreferredSize(new Dimension(15,15));
        panel.add(libraryRadioButton1);
        panel.add(libraryComboBox);
        panel.add(libraryRadioButton2);
        panel.add(libraryCustomPathTF);
        panel.add(libraryCustomPathButton);
        panel.add(playButton);

        fillLibraryComboBox();

        playButton.addActionListener(new PlayButtonListener());
        libraryRadioButton1.addActionListener(new LibraryRadioButton1Listener());
        libraryRadioButton2.addActionListener(new LibraryRadioButton2Listener());
        libraryCustomPathButton.addActionListener(new LibraryPathButtonListener());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void initializeSettings() throws IOException {
        initializeDisplaySettings();
        initializeGameSettings();
    }

    private void initializeDisplaySettings() throws  IOException {
        File displaySettingsFile = new File("displaySettings");
        if (displaySettingsFile.exists()) {
            this.displaySettings = SettingsReader.readDisplaySettings(displaySettingsFile);
        } else {
            createDisplaySettingsFile();
            initializeDisplaySettings();
        }
    }

    private void initializeGameSettings() throws IOException {
        File gameSettingsFile = new File("gameSettings");
        if (gameSettingsFile.exists()) {
            this.gameSettings = SettingsReader.readGameSettings(gameSettingsFile);
        } else {
            createGameSettingsFile();
            initializeGameSettings();
        }
    }

    private static void createDisplaySettingsFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("displaySettings", "UTF-8");
        String[] settings = {
                "resolution_w=1200",
                "resolution_h=600",
                "desired_fps=30",
                "color_word_default=32,232,118",
                "color_word_triggered=35,244,255",
                "color_word_full=232,27,146",
                "color_background=0,0,0",
                "color_menu_background=0,246,255",
                "color_menu_word=155,155,155",
                "color_menu_numbers=61,61,61",
                "color_menu_labels=255,0,0"
        };
        for (String line : settings) {
            writer.println(line);
        }
        writer.close();
    }

    private static void createGameSettingsFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("gameSettings", "UTF-8");
        String[] settings = {
                "max_misses=10",
                "initial_spawn_delay=1500",
                "spawn_delay_reduction=1",
                "minimal_spawn_delay=1",
                "spawn_x_min=0.2",
                "spawn_x_max=0.8"
        };
        for (String line : settings) {
            writer.println(line);
        }
        writer.close();
    }

    private void fillLibraryComboBox() {
        libraryComboBox.addItem("ENGLISH");
    }

    private String[] readCustomWordLibrary(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        List<String> words = new ArrayList<String>();
        while(sc.hasNext()) {
            words.add(sc.next());
        }
        return words.toArray(new String[words.size()]);
    }

    private String[] readEmbeddedWordLibrary(InputStream inputStream) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] splitLine = line.split(" ");
            for (String word : splitLine)
                words.add(word.trim());
            line = bufferedReader.readLine();
        }
        return words.toArray(new String[words.size()]);
    }

    private class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                String[] words = null;
                if (libraryRadioButton1.isSelected()) { // If user selects one of the embedded libraries
                    InputStream library = getClass().getResourceAsStream("/res/libraries/" + libraryComboBox.getSelectedItem().toString() + ".txt");
                    words = readEmbeddedWordLibrary(library);
                } else if (libraryRadioButton2.isSelected()) { // or a custom library
                    words = readCustomWordLibrary(selectedLibraryFilePath);
                }
                Game game = new StandardGame(words, gameSettings);
                GameRenderer gameRenderer = new GameRenderer(game, displaySettings);
                new Thread(gameRenderer).start();
                frame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LibraryPathButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));
            jFileChooser.setName("Choose word library file");
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            jFileChooser.setFileFilter(filter);
            jFileChooser.showOpenDialog(libraryCustomPathButton);
            selectedLibraryFilePath = jFileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private class LibraryRadioButton1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            libraryRadioButton1.setEnabled(false);
            libraryRadioButton2.setEnabled(true);
            libraryCustomPathButton.setEnabled(false);
            libraryCustomPathTF.setEnabled(false);
            libraryComboBox.setEnabled(true);

        }
    }

    private class LibraryRadioButton2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            libraryRadioButton2.setEnabled(false);
            libraryRadioButton1.setEnabled(true);
            libraryCustomPathButton.setEnabled(true);
            libraryCustomPathTF.setEnabled(true);
            libraryComboBox.setEnabled(false);
        }
    }

}
