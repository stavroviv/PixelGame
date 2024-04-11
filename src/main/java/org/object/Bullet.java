package org.object;

import org.graphics.Animation;
import org.graphics.Renderer;
import org.world.World;

public class Bullet extends Sprite {

    public int direction;
    public float speed = 150.0f;
    public float damage = 10.0f;

    public Bullet(float posX, float posY, int direction) {
        super(posX, posY);
        this.direction = direction;
        width = 8;
        height = 8;
        isSolid = false;

        Animation anim = new Animation();
        anim.fps = 80;
        anim.images.add(Renderer.loadImage("bul1.png"));
        anim.images.add(Renderer.loadImage("bul2.png"));
        animations = new Animation[]{anim};
    }

    public void update(float deltaTime) {
        if (getRealX() < 0 || getRealX() > Renderer.getRealWidth()) {
            World.removeSprite(this);
            return;
        }
        float moveX = 0;
        moveX += speed * direction;
        posX += moveX * deltaTime;
        for (Sprite sprite : getColliders(posX, posY)) {
            if (sprite instanceof BadGuy badGuy) {
                badGuy.takeDamage(damage);
                World.removeSprite(this);
            }
        }
    }
}
