package org.object;

import org.game.Game;
import org.graphics.Animation;
import org.graphics.Renderer;
import org.input.Input;
import org.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

    public float posX;
    public float posY;

    public Animation[] animations;

    public float width = 0;
    public float height = 0;

    public int currentAnimations = 0;

    public boolean isSolid = true;

    public Sprite(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void render(Graphics g) {
        if (animations == null || currentAnimations >= animations.length) {
            return;
        }

        animations[currentAnimations].playAnimation();

        BufferedImage image = animations[currentAnimations].getImage();
        if (image == null) {
            return;
        }

        int realX = (int) posX - image.getHeight() / 2;
        int realY = (int) posY - image.getWidth() / 2;

        realX = realX - (int) Renderer.camX + Renderer.gameWidth / 2;
        realY = realY - (int) Renderer.camY + Renderer.gameHeight / 2;

        g.drawImage(image, realX, realY, image.getWidth(), image.getHeight(), null);
    }

    public void update(float deltaTime) {
        if (Input.getKey(KeyEvent.VK_ESCAPE)) {
            Game.quit();
        }
    }

    protected Sprite[] getColliders(float x, float y) {
        ArrayList<Sprite> sprites = new ArrayList<>();

        float myLeft = x - width / 2;
        float myRight = x + width / 2;
        float myUp = y - height / 2;
        float myDown = y + height / 2;

        for (Sprite sprite : World.currentWorld.sprites) {
            if (sprite == this || !sprite.isSolid) {
                continue;
            }

            float otherLeft = sprite.posX - sprite.width / 2;
            float otherRight = sprite.posX + sprite.width / 2;
            float otherUp = sprite.posY - sprite.height / 2;
            float otherDown = sprite.posY + sprite.height / 2;

            if (myLeft < otherRight && myRight > otherLeft && myDown > otherUp && myUp < otherDown) {
                sprites.add(sprite);
            }
        }

        Sprite[] spreiteArray = new Sprite[sprites.size()];
        return sprites.toArray(spreiteArray);
    }

    protected boolean doesCollide(float x, float y) {
        float myLeft = x - width / 2;
        float myRight = x + width / 2;
        float myUp = y - height / 2;
        float myDown = y + height / 2;

        for (Sprite sprite : World.currentWorld.sprites) {
            if (sprite == this || !sprite.isSolid) {
                continue;
            }

            float otherLeft = sprite.posX - sprite.width / 2;
            float otherRight = sprite.posX + sprite.width / 2;
            float otherUp = sprite.posY - sprite.height / 2;
            float otherDown = sprite.posY + sprite.height / 2;

            if (myLeft < otherRight && myRight > otherLeft && myDown > otherUp && myUp < otherDown) {
                return true;
            }
        }

        return false;
    }
}
