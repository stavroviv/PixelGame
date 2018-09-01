package org.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.game.Game;
import org.graphics.Renderer;
import org.input.Input;

public class Platform extends Sprite{
	
	public Platform(float posX, float posY, float width, float height) {
		super(posX, posY);

		this.width = width;
		this.height = height;
	}

	public void render (Graphics g){
		g.setColor(new Color(120, 80, 50));
		g.fillRect((int) (posX - width/2) - (int) Renderer.camX + (int) Renderer.gameWidth/2, 
				(int) (posY - height/2) - (int) Renderer.camY + (int) Renderer.gameHeight/2, (int) width, (int) height);
	}
	

}
