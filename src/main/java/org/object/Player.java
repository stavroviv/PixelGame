package org.object;

import org.graphics.Animation;
import org.graphics.Renderer;
import org.input.Input;
import org.utils.ImageUtils;
import org.world.World;

import java.awt.event.KeyEvent;

public class Player extends Mob {

    private float velocityY = 10;
    private float gravity = 90.f;
    private float jumpHeight = 250;

    private int direction = 1;

    public Player(float posX, float posY) {
        super(posX, posY);
        width = 56;
        height = 28;
        var anim = new Animation();
        loadImages(anim);
        animations = new Animation[]{anim};
    }

    private static void loadImages(Animation anim) {
        for (var i = 1; i < 8; i++) {
            anim.images.add(ImageUtils.loadImage("roach/" + i + ".gif"));
        }
    }

    public void update(float deltaTime) {
        float moveX = 0;

        if (Input.getKey(KeyEvent.VK_LEFT)) {
            moveX -= runSpeed;
        }

        if (Input.getKey(KeyEvent.VK_RIGHT)) {
            moveX += runSpeed;
        }

        if (moveX > 0) {
            direction = 1;
        }

        if (moveX < 0) {
            direction = -1;
        }

        velocityY += gravity * deltaTime;

        if (doesCollide(posX, posY + 1)) {
            if (Input.getKeyDown(KeyEvent.VK_UP)) {
                velocityY = (float) -Math.sqrt(2 * jumpHeight * gravity);
            }
        }

        // COLLISIONS
        if (doesCollide(posX + moveX * deltaTime, posY)) {
            moveX -= moveX;
        }

        if (doesCollide(posX, posY + velocityY * deltaTime)) {
            velocityY -= velocityY;
        }

        // END COLLISIONS
        if (Input.getKey(KeyEvent.VK_SPACE)) {
            var bullet = new Bullet(posX + (direction == 1 ? 60 : -30), posY - 15, direction);
            World.addSprite(bullet);
        }

        posX += moveX * deltaTime;
        posY += velocityY * deltaTime;

        Renderer.camX = posX;
        Renderer.camY = 600;
    }
}
