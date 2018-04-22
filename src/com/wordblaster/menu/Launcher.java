package com.wordblaster.menu;

import com.wordblaster.game.gametypes.Game;
import com.wordblaster.game.gametypes.StandardGame;
import com.wordblaster.renderer.GameRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {

    private JFrame frame;
    private JComboBox<String> libaryComboBox = new JComboBox<String>();
    private JButton playButton = new JButton("PLAY");
    private JRadioButton libaryRadioButton1 = new JRadioButton();
    private JRadioButton libaryRadioButton2 = new JRadioButton();
    private ButtonGroup libaryBG = new ButtonGroup();
    private JTextField libaryCustomPathTF = new JTextField("", 20);
    private JButton libaryCustomPathButton = new JButton("...");
    private String selectedLibaryFilePath;

    private String libariesFolder = "resources/libaries";

    public Launcher() {
        frame = new JFrame("Word Blaster");
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(700, 60));
        panel.setLayout(new FlowLayout());

        libaryBG.add(libaryRadioButton1);
        libaryBG.add(libaryRadioButton2);
        libaryRadioButton1.setSelected(true);
        libaryCustomPathTF.setEnabled(false);
        libaryCustomPathButton.setEnabled(false);
        libaryCustomPathButton.setPreferredSize(new Dimension(15,15));
        panel.add(libaryRadioButton1);
        panel.add(libaryComboBox);
        panel.add(libaryRadioButton2);
        panel.add(libaryCustomPathTF);
        panel.add(libaryCustomPathButton);
        panel.add(playButton);

        fillLibaryComboBox();

        playButton.addActionListener(new PlayButtonListener());
        libaryRadioButton1.addActionListener(new LibaryRadioButton1Listener());
        libaryRadioButton2.addActionListener(new LibaryRadioButton2Listener());
        libaryCustomPathButton.addActionListener(new LibaryPathButtonListener());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void fillLibaryComboBox() {
        File libaryFolder = new File(libariesFolder);
        for (File f : libaryFolder.listFiles()) {
            if (f.isFile() && f.getName().contains(".txt"))
                libaryComboBox.addItem(f.getName().split("\\.")[0]);
        }
    }

    private String[] readWordLibary(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        List<String> stringList = new ArrayList<String>();
        while(sc.hasNext()) {
            stringList.add(sc.next());
        }
        return stringList.toArray(new String[stringList.size()]);
    }

    private class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String selectedLibary = "";
            if (libaryRadioButton1.isSelected()) {
                selectedLibary = libariesFolder + "/" + libaryComboBox.getSelectedItem().toString() + ".txt";
            } else if (libaryRadioButton2.isSelected()) {
                selectedLibary = selectedLibaryFilePath;
            }
            try {
                String[] words = readWordLibary(selectedLibary);
                Game game = new StandardGame(words, 10);
                GameRenderer gameRenderer = new GameRenderer(game,1200,600);
                new Thread(gameRenderer).start();
                frame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LibaryPathButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));
            jFileChooser.setName("Choose word libary file");
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            jFileChooser.setFileFilter(filter);
            jFileChooser.showOpenDialog(libaryCustomPathButton);
            selectedLibaryFilePath = jFileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private class LibaryRadioButton1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            libaryRadioButton1.setEnabled(false);
            libaryRadioButton2.setEnabled(true);
            libaryCustomPathButton.setEnabled(false);
            libaryCustomPathTF.setEnabled(false);
            libaryComboBox.setEnabled(true);

        }
    }

    private class LibaryRadioButton2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            libaryRadioButton2.setEnabled(false);
            libaryRadioButton1.setEnabled(true);
            libaryCustomPathButton.setEnabled(true);
            libaryCustomPathTF.setEnabled(true);
            libaryComboBox.setEnabled(false);
        }
    }

}
