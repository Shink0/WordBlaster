package com.wordblaster.renderer;

import com.wordblaster.game.Game;
import com.wordblaster.game.Word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class GameRenderer implements Runnable {

    private int width;
    private int height;

    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;

    private Game game;

    private long desiredFPS = 30;
    private long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

    private Font wordsFont =  new Font("Serif", Font.BOLD, 20);
    private Font menuFont = new Font("Courier", Font.BOLD, 20);

    private StringBuffer wordBuffer = new StringBuffer();
    private boolean deleteFromBuffer = false;
    private char charToAppend = 0;

    private boolean readyToBlast = false;

    private String maxFaultsStr;

    /**
     * Modification of game critical data should never be done inside this KeyListener.
     * This is to avoid concurrent modification by other parts of the game.
     */
    private class Keyboard implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {}

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if ((keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) && wordBuffer.length() > 0) {
                deleteFromBuffer = true;
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                readyToBlast = true;
            } else {
                charToAppend = keyEvent.getKeyChar();
            }

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {}
    }

    public GameRenderer(Game game, DisplaySettings displaySettings) {
        this.game = game;
        this.width = displaySettings.getResolution_w();
        this.height = displaySettings.getResolution_h();
        this.maxFaultsStr = Integer.toString(game.getMaxMisses());

        frame = new JFrame("Word Blaster");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, width, height);
        canvas.setIgnoreRepaint(true);
        canvas.setBackground(Color.BLACK);
        canvas.addKeyListener(new Keyboard());

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(3);
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
    }

    public void run() {
        long beginLoopTime;
        long endLoopTime;
        long deltaLoop;
        while (true) {
            beginLoopTime = System.nanoTime();
            if (!game.isGameOver()) {
                processGameLogic();
                renderGame();
            } else {
                renderGameOver();
            }
            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;
            if (!(deltaLoop > desiredDeltaLoop)) {
                try {
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                } catch(InterruptedException e) {}
            }
        }
    }

    private void processGameLogic() {
        if (readyToBlast) {
            game.blastWord(wordBuffer.toString());
            wordBuffer.delete(0, wordBuffer.length());
            readyToBlast = false;
        }
        if (deleteFromBuffer) {
            wordBuffer.delete(wordBuffer.length() - 1, wordBuffer.length());
            deleteFromBuffer = false;
        }
        if(charToAppend != 0 && wordBuffer.length() <= 30) {
            wordBuffer.append(charToAppend);
            charToAppend = 0;
        }
        game.maybeSpawnNewWord();
        game.doFall();
    }

    private void renderGame() {
        do {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(wordsFont);
            g.clearRect(0, 0, width, height);
            renderGameToBuffer(g);
            g.dispose();
            bufferStrategy.show();  // Draw to the canvas
        } while (bufferStrategy.contentsLost());    // Repeats the rendering if the buffer was lost
    }

    private void renderGameToBuffer(Graphics2D g) {
        // WORDS
        for (Word w : game.getSpawnedWords()) {
            int x = (int) (w.getHorizontalPosition() * width);
            int y = (int) (w.getVerticalPosition() * height);
            String wordString = w.toString();
            short charsCorrect = 0;
            if (wordBuffer.length() <= w.lenght()) {
                for (int i = 0; i < wordBuffer.length(); i++) {
                    if (!(wordBuffer.charAt(i) == wordString.charAt(i))) {
                        charsCorrect = 0;
                        break;
                    } else {
                        charsCorrect++;
                    }
                }
            }
            if (charsCorrect >=  2) {
                if (charsCorrect == wordString.length()) {
                    g.setColor(Color.MAGENTA);
                } else {
                    g.setColor(Color.YELLOW);
                }
            } else {
                g.setColor(Color.GREEN);
            }
            g.drawString(wordString.toString(), x, y);
        }
        // BOTTOM BAR
        int menuY = height - 10;
        g.setColor(Color.CYAN);
        g.fillRect(0, height -30, width, height);
        g.setFont(menuFont);
        g.setColor(Color.BLUE);
        g.drawString("SCORE: ", 5, menuY);
        g.setColor(Color.RED);
        g.drawString(Integer.toString(game.getScore()), 80, menuY);
        g.setColor(Color.CYAN);
        g.fillRect(0, menuY, width, height);
        g.setColor(Color.GRAY);
        g.drawString(wordBuffer.toString(), 200, menuY);
        g.setColor(Color.BLUE);
        g.drawString("MISSES: ", width - 180, menuY);
        g.setColor(Color.RED);
        g.drawString(Integer.toString(game.getMisses()) + "/" + maxFaultsStr, width - 90, menuY);

    }

    private void renderGameOver(){
        do {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.clearRect(0, 0, width, height);
            g.setColor(Color.BLUE);
            g.setFont(new Font("Courier", Font.BOLD, 30));
            g.drawString("GAME OVER", width / 2 - 200, height / 2);
            g.dispose();
            bufferStrategy.show();  // Draw to the canvas
        } while (bufferStrategy.contentsLost());
    }


}
