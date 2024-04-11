package org.game;

import org.graphics.Renderer;
import org.object.BadGuy;
import org.object.Platform;
import org.object.Player;
import org.world.World;

public class Game {

    public static void main(String[] args) {
        Renderer.init();
        World.init();
        Renderer.startRendering();
    }

    public static void quit() {
        System.exit(0);
    }
}
