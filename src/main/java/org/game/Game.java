package org.game;

import org.graphics.Renderer;
import org.object.BadGuy;
import org.object.Platform;
import org.object.Player;
import org.world.World;

public class Game {

	public static void main(String[] args) {
		
		Renderer.init();
		
		World.currentWorld = new World();
		World.currentWorld.addSprite(new Player(400, 100));
		World.currentWorld.addSprite(new Platform(600, 800, 1000, 40));
		World.currentWorld.addSprite(new Platform(1700, 700, 1400, 40));
		World.currentWorld.addSprite(new BadGuy(500, 100));
		
		Renderer.startRendering();
				
	}
	
	public static void quit() {

		System.exit(0);

	}
}
