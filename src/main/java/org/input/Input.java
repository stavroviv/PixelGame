package org.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private static boolean[] lastKeys = new boolean[128];
    private static boolean[] currentKeys = new boolean[128];

    public static boolean getKey(int keyCode) {
        return currentKeys[keyCode];
    }

    public static boolean getKeyDown(int keyCode) {
        return currentKeys[keyCode] && !lastKeys[keyCode];
    }

    public static boolean getKeyUp(int keyCode) {
        return !currentKeys[keyCode] && lastKeys[keyCode];
    }

    public static void finishInput() {
        lastKeys = currentKeys.clone();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() > currentKeys.length) {
            return;
        }
        currentKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() > currentKeys.length) {
            return;
        }
        currentKeys[e.getKeyCode()] = false;
    }
}
