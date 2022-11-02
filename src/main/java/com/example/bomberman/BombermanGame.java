/**
 * @author low_
 */

package com.example.bomberman;

import com.example.bomberman.graphics.Screen;
import com.example.bomberman.gui.Frame;

import java.awt.*;
import java.io.File;

public class BombermanGame {
    private static void addFontToSystem() {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Screen.FONT_LINK)).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addFontToSystem();
        new Frame();
    }
}