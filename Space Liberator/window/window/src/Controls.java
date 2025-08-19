/*******
 * CSCI 185
 * Antoun Elabd & Mohamed Youssef
 * Dr. Wenjia Li
 * Final Programming Project
 * 5/8/2025
 *******/

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener {

    private boolean[] keyspressed;

    public Controls() {
        keyspressed = new boolean[10000];
    }

    public boolean isKeyPressed(int key) {
        return keyspressed[key];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    keyspressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyspressed[e.getKeyCode()] = false;

    }
}
