package org.object;

import org.graphics.Animation;
import org.graphics.Renderer;
import org.world.World;

import java.io.IOException;

public class Bullet extends Sprite {

    public int direction = -1;
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
        try {
            anim.images.add(Renderer.loadImage("/images/bul1.png"));
            anim.images.add(Renderer.loadImage("/images/bul2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        animations = new Animation[]{anim};
    }

    public void update(float deltaTime) {
        float moveX = 0;

        moveX += speed * direction;

        posX += moveX * deltaTime;

        Sprite[] colliders = getColliders(posX, posY);
        if (colliders.length > 0) {
//			boolean 

            for (Sprite sprite : colliders) {
                if (sprite instanceof BadGuy) {
                    BadGuy badGuy = (BadGuy) sprite;
                    badGuy.takeDamage(damage);
                    World.currentWorld.removeSprite(this);
                }
            }
        }
    }

//	public void render(){
//		animations[currentAnimations].playAnimation();
//		super.render(g);
//	}
}
