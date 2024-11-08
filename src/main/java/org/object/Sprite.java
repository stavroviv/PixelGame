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
import java.util.List;

public class Sprite {

    public float posX;
    public float posY;

    public Animation[] animations;

    public float width = 0;
    public float height = 0;

    public int currentAnimations = 0;

    public boolean isSolid = true;

    private int realX;
    private int realY;

    public Sprite(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void render(Graphics g) {
        if (animations == null || currentAnimations >= animations.length) {
            return;
        }

        animations[currentAnimations].playAnimation();

        var image = animations[currentAnimations].getImage();
        if (image == null) {
            return;
        }

        realX = (int) posX - image.getHeight() / 2;
        realY = (int) posY - image.getWidth() / 2;

        realX = realX - (int) Renderer.camX + Renderer.gameWidth / 2;
        realY = realY - (int) Renderer.camY + Renderer.gameHeight / 2;

        g.drawImage(image, realX, realY, image.getWidth(), image.getHeight(), null);
    }

    public int getRealX() {
        return realX;
    }

    public void update(float deltaTime) {
        if (Input.getKey(KeyEvent.VK_ESCAPE)) {
            Game.quit();
        }
    }

    protected List<Sprite> getColliders(float x, float y) {
        List<Sprite> sprites = new ArrayList<>();

        var myLeft = x - width / 2;
        var myRight = x + width / 2;
        var myUp = y - height / 2;
        var myDown = y + height / 2;

        for (var sprite : World.getSprites()) {
            if (sprite == this || !sprite.isSolid) {
                continue;
            }

            var otherLeft = sprite.posX - sprite.width / 2;
            var otherRight = sprite.posX + sprite.width / 2;
            var otherUp = sprite.posY - sprite.height / 2;
            var otherDown = sprite.posY + sprite.height / 2;

            if (myLeft < otherRight && myRight > otherLeft && myDown > otherUp && myUp < otherDown) {
                sprites.add(sprite);
            }
        }

        return sprites;
    }

    protected boolean doesCollide(float x, float y) {
        return !getColliders(x,y).isEmpty();
    }
}
