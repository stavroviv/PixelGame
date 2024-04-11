package org.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private static boolean[] lastkeys = new boolean[128];
    private static boolean[] currentkeys = new boolean[128];

    public static boolean getKey(int keyCode) {
        return currentkeys[keyCode];
    }

    public static boolean getKeyDown(int keyCode) {
        return currentkeys[keyCode] && !lastkeys[keyCode];
    }

    public static boolean getKeyUp(int keyCode) {
        return !currentkeys[keyCode] && lastkeys[keyCode];
    }

    public static void finishInput() {
        lastkeys = currentkeys.clone();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentkeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentkeys[e.getKeyCode()] = false;
    }

}
