/**
 * @author low_
 */
package com.example.bomberman.gui;

import com.example.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public static final String FONT_NAME = "Falstin";
    public static final Font INFO_FONT = new Font(FONT_NAME, Font.PLAIN, 16);

    private final JLabel timeLabel;
    private final JLabel pointsLabel;
    private final JLabel livesLabel;
    private final JLabel HighScoreLabel;

    /**
     * Constructor.
     *
     * @param game Game
     */
    public InfoPanel(Game game) {
        setLayout(new GridLayout());

        timeLabel = new JLabel("Time: " + game.getBoard().getTime());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(INFO_FONT);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
        pointsLabel.setForeground(Color.YELLOW);
        pointsLabel.setFont(INFO_FONT);
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);

        livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
        livesLabel.setForeground(Color.RED);
        livesLabel.setFont(INFO_FONT);
        livesLabel.setHorizontalAlignment(JLabel.CENTER);

        HighScoreLabel = new JLabel("High Score: " + game.getBoard().getHighScore());
        HighScoreLabel.setForeground(Color.ORANGE);
        HighScoreLabel.setFont(INFO_FONT);
        HighScoreLabel.setHorizontalAlignment(JLabel.CENTER);

        add(timeLabel);
        add(pointsLabel);
        add(livesLabel);
        add(HighScoreLabel);

        setBackground(Color.black);
        setPreferredSize(new Dimension(0, 40));
    }

    /**
     * set time.
     *
     * @param t time
     */
    public void setTime(int t) {
        timeLabel.setText("Time: " + t);
    }

    /**
     * set Lives
     *
     * @param lives lives
     */
    public void setLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    /**
     * set Points.
     *
     * @param p points
     */
    public void setPoints(int p) {
        pointsLabel.setText("Score: " + p);
    }

    /**
     * set high score.
     *
     * @param highScore high score
     */
    public void setHighScore(int highScore) {
        HighScoreLabel.setText("High Score: " + highScore);
    }
}
