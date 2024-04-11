package org.object;

import org.graphics.Renderer;

import java.awt.*;
import java.util.Random;

public class Platform extends Sprite {
    private final Color color;

    public Platform(float posX, float posY, float width, float height) {
        super(posX, posY);
        this.width = width;
        this.height = height;
        this.color = new Color(20, new Random().nextInt(70), new Random().nextInt(150));
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(
                (int) (posX - width / 2) - (int) Renderer.camX + Renderer.gameWidth / 2,
                (int) (posY - height / 2) - (int) Renderer.camY + Renderer.gameHeight / 2,
                (int) width,
                (int) height
        );
    }
}
