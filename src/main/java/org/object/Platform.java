package org.object;

import org.graphics.Renderer;

import java.awt.*;

public class Platform extends Sprite {

    public Platform(float posX, float posY, float width, float height) {
        super(posX, posY);
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g) {
        g.setColor(new Color(120, 80, 50));
        g.fillRect(
                (int) (posX - width / 2) - (int) Renderer.camX + Renderer.gameWidth / 2,
                (int) (posY - height / 2) - (int) Renderer.camY + Renderer.gameHeight / 2,
                (int) width,
                (int) height
        );
    }
}
