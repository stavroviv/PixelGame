package org.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.graphics.Renderer;
import org.object.Sprite;

public class World {

	public static World currentWorld = null;
	
//	private static long lastTime = System.nanoTime();
	
	public ArrayList<Sprite> sprites = new ArrayList<>();
	public ArrayList<Sprite> addSprites = new ArrayList<>();
	public ArrayList<Sprite> removeSprites = new ArrayList<>();
	
	private static BufferedImage backDrop = null;
	private static BufferedImage backDrop2 = null;
	
	private static int backDropX = 0;
	
	public World(){

		try {
			backDrop = Renderer.loadImage("/resources/images/backDrop.png");
			backDrop2 = Renderer.loadImage("/resources/images/backDrop2.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void update() {
		
//		float deltaTime = (System.nanoTime() - lastTime)/1000000000.0f;
		float deltaTime = 0.07f;
		
		for (Sprite sprite : currentWorld.sprites) {
			sprite.update(deltaTime);
		}
		
		for (Sprite sprite : currentWorld.addSprites) {
			if (!currentWorld.sprites.contains(sprite)){
				currentWorld.sprites.add(sprite);
			}
		}
		currentWorld.addSprites.clear();
		
		for (Sprite sprite : currentWorld.removeSprites) {
			if (currentWorld.sprites.contains(sprite)){
				currentWorld.sprites.remove(sprite);
			}
		}
		currentWorld.removeSprites.clear();
	}
	
	public static void render(Graphics g) {
				
		loadBack(g, 50, backDrop);
		loadBack(g, 3, backDrop2);

		for (Sprite sprite : currentWorld.sprites) {
			sprite.render(g);
		}

	}
	
	public static void loadBack(Graphics g, int divide, BufferedImage backDrop){
		
		if (backDropX < Renderer.camX/divide - Renderer.gameWidth){
			backDropX += Renderer.gameWidth;
		}
		if (backDropX > Renderer.camX / divide + Renderer.gameWidth){
			backDropX -= Renderer.gameWidth;
		}
					
		int x = backDropX - (int) Renderer.camX/divide;
		int bufferX = 0;
		
		if (backDropX > Renderer.camX/divide) {
			bufferX = backDropX - Renderer.gameWidth - (int) Renderer.camX/divide;
		} else {
			bufferX = backDropX + Renderer.gameWidth - (int) Renderer.camX/divide;
		}
		
		g.drawImage(backDrop, x, 0, Renderer.gameWidth, Renderer.gameHeight, null);
		g.drawImage(backDrop, bufferX, 0, Renderer.gameWidth, Renderer.gameHeight, null);

		
	}

	public void addSprite(Sprite sprite){
		if (!addSprites.contains(sprite)) {
			addSprites.add(sprite);
		}
	}
	
	public void removeSprite(Sprite sprite){
		if (!removeSprites.contains(sprite)) {
			removeSprites.add(sprite);
		}
	}
}
