package org.world;

import org.graphics.Renderer;
import org.object.BadGuy;
import org.object.Platform;
import org.object.Player;
import org.object.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class World {

    private static final Set<Sprite> sprites = new HashSet<>();
    private static final Set<Sprite> addSprites = new HashSet<>();
    private static final Set<Sprite> removeSprites = new HashSet<>();
    private static BufferedImage backDrop;
    private static BufferedImage backDrop2;

    private static int backDropX = 0;

    public static void init() {
        backDrop = Renderer.loadImage("backDrop.png");
        backDrop2 = Renderer.loadImage("backDrop2.png");
        addSprite(new Player(400, 100));
        addSprite(new Platform(600, 800, 1000, 40));
        addSprite(new Platform(1700, 700, 1400, 40));
        addSprite(new Platform(1700, 700, 1400, 40));
        addSprite(new BadGuy(500, 100));
    }

    private World() {
    }

    public static void update() {
        float deltaTime = 0.07f;
        for (Sprite sprite : sprites) {
            sprite.update(deltaTime);
        }
        sprites.addAll(addSprites);
        addSprites.clear();
        for (Sprite sprite : removeSprites) {
            sprites.remove(sprite);
        }
        removeSprites.clear();
    }

    public static void render(Graphics g) {
        loadBack(g, 50, backDrop);
        loadBack(g, 3, backDrop2);
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }

    public static void loadBack(Graphics g, int divide, BufferedImage backDrop) {
        if (backDropX < Renderer.camX / divide - Renderer.gameWidth) {
            backDropX += Renderer.gameWidth;
        }
        if (backDropX > Renderer.camX / divide + Renderer.gameWidth) {
            backDropX -= Renderer.gameWidth;
        }

        int x = backDropX - (int) Renderer.camX / divide;
        int bufferX;

        if (backDropX > Renderer.camX / divide) {
            bufferX = backDropX - Renderer.gameWidth - (int) Renderer.camX / divide;
        } else {
            bufferX = backDropX + Renderer.gameWidth - (int) Renderer.camX / divide;
        }

        g.drawImage(backDrop, x, 0, Renderer.gameWidth, Renderer.gameHeight, null);
        g.drawImage(backDrop, bufferX, 0, Renderer.gameWidth, Renderer.gameHeight, null);
    }

    public static Set<Sprite> getSprites() {
        return sprites;
    }

    public static void addSprite(Sprite sprite) {
        addSprites.add(sprite);
    }

    public static void removeSprite(Sprite sprite) {
        removeSprites.add(sprite);
    }
}
