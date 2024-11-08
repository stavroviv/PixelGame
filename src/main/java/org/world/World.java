package org.world;

import org.graphics.Renderer;
import org.object.BadGuy;
import org.object.Platform;
import org.object.Player;
import org.object.Sprite;
import org.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class World {

    private static final Set<Sprite> sprites = new HashSet<>();
    private static final Set<Sprite> addSprites = new HashSet<>();
    private static final Set<Sprite> removeSprites = new HashSet<>();
    private static BufferedImage backDrop;
    private static BufferedImage backDrop2;

    private static int backDropX = 0;

    private World() {
    }

    public static void init() {
        initBackground();
        addSprite(new Player(400, 100));
        initPlatforms();
        addSprite(new BadGuy(500, 100));
    }

    private static void initPlatforms() {
        for (var i = 0; i < 500; i++) {
            addSprite(new Platform(2 * i * 100, 900 - new Random().nextInt(4) * 50, 300, 25));
        }
    }

    private static void initBackground() {
        backDrop = ImageUtils.loadImage("backDrop.png");
        backDrop2 = ImageUtils.loadImage("backDrop2.png");
    }

    public static void update() {
        var deltaTime = 0.07f;
        for (var sprite : sprites) {
            sprite.update(deltaTime);
        }
        sprites.addAll(addSprites);
        addSprites.clear();
        for (var sprite : removeSprites) {
            sprites.remove(sprite);
        }
        removeSprites.clear();
    }

    public static void render(Graphics g) {
        loadBack(g, 16, backDrop);
        loadBack(g, 4, backDrop2);
        for (var sprite : sprites) {
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

        var x = backDropX - (int) Renderer.camX / divide;
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
