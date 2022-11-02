/**
 * Class Frame
 *
 * @author low_
 */

package com.example.bomberman.gui;

import com.example.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public GamePanel gamePanel;
    private final InfoPanel infoPanel;

    /**
     * Constructor.
     */
    public Frame() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        gamePanel = new GamePanel(this);
        infoPanel = new InfoPanel(gamePanel.getGame());
        containerPanel.add(infoPanel, BorderLayout.PAGE_START);
        containerPanel.add(gamePanel, BorderLayout.PAGE_END);

        Game game = gamePanel.getGame();

        add(containerPanel);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    /**
     * Set time.
     * @param time time
     */
    public void setTime(int time) {
        infoPanel.setTime(time);
    }

    /**
     * Set pts.
     * @param points Points
     */
    public void setPoints(int points) {
        infoPanel.setPoints(points);
    }

    /**
     * Set lives.
     * @param lives lives
     */
    public void setLives(int lives) {
        infoPanel.setLives(lives);
    }

    /**
     * Set high score.
     * @param highScore high score
     */
    public void setHighScore(int highScore) {
        infoPanel.setHighScore(highScore);
    }

}
