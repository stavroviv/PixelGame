package org.object;

import org.graphics.Renderer;
import org.world.World;

import java.awt.*;

public class BadGuy extends Mob {

    private float velocityY = 10;
    private float gravity = 90.f;
    private float jumpHeight = 150;

    private int direction = 1;//1 = right, -1 = left

    public BadGuy(float posX, float posY) {
        super(posX, posY);
        width = 40;
        height = 55;
    }

    public void update(float deltaTime) {
        float moveX = 0;

        moveX += direction * runSpeed;


        velocityY += gravity * deltaTime;

        if (doesCollide(posX, posY + 1)) {

        }
        // COLLISIONS
        if (doesCollide(posX + moveX * deltaTime, posY)) {
            moveX -= moveX;
            direction = -direction;
        }

        if (doesCollide(posX, posY + velocityY * deltaTime)) {
            velocityY -= velocityY;
        }

        // END COLLISIONS

        if (!doesCollide(posX + width * direction, posY + 1)) {
            direction = -direction;
        }

        posX += moveX * deltaTime;
        posY += velocityY * deltaTime;

    }

    public void render(Graphics g) {
        int realX = (int) (posX - width / 2);
        int realY = (int) (posY - height / 2);

        realX = realX - (int) Renderer.camX + Renderer.gameWidth / 2;
        realY = realY - (int) Renderer.camY + Renderer.gameHeight / 2;

        g.setColor(Color.red);
        g.fillRect(realX, realY,
                (int) width, (int) height);
    }

    public void takeDamage(float damage) {
        World.currentWorld.removeSprite(this);
    }

}
