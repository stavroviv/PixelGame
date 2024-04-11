package org.object;

import org.graphics.Animation;
import org.graphics.Renderer;
import org.world.World;

import java.io.IOException;
import java.util.List;

public class Bullet extends Sprite {

    public int direction;
    public float speed = 200.0f;
    public float damage = 10.0f;

    public Bullet(float posX, float posY, int direction) {
        super(posX, posY);
        this.direction = direction;
        width = 16;
        height = 16;
        isSolid = false;

        Animation anim = new Animation();
        anim.fps = 80;
        anim.images.add(Renderer.loadImage("/images/bul1.png"));
        anim.images.add(Renderer.loadImage("/images/bul2.png"));
        animations = new Animation[]{anim};
    }

    public void update(float deltaTime) {
        float moveX = 0;
        moveX += speed * direction;
        posX += moveX * deltaTime;
        for (Sprite sprite : getColliders(posX, posY)) {
            if (sprite instanceof BadGuy badGuy) {
                badGuy.takeDamage(damage);
                World.currentWorld.removeSprite(this);
            }
        }
    }

//	public void render(){
//		animations[currentAnimations].playAnimation();
//		super.render(g);
//	}
}
