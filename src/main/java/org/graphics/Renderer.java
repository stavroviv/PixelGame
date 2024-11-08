package org.graphics;

import org.game.Game;
import org.input.Input;
import org.world.World;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;

public class Renderer {

    public static float camX = 0;
    public static float camY = 0;

    private static Frame frame;
    private static Canvas canvas;

    private static int canvasWidth = 0;
    private static int canvasHeight = 0;

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;

    public static int gameWidth = 0;
    public static int gameHeight = 0;

    private static long lastFPSCheck = 0;
    private static int currentFPS = 0;

    private static final int targetFPS = 100;
    private static final int targetTime = 1000000000 / targetFPS;

    private static void getBestSize() {
        var toolkit = Toolkit.getDefaultToolkit();
        var screenSize = toolkit.getScreenSize();

        var done = false;

        while (!done) {
            canvasWidth += GAME_WIDTH;
            canvasHeight += GAME_HEIGHT;

            if (canvasWidth > screenSize.getWidth() || canvasHeight > screenSize.getHeight()) {
                canvasWidth -= GAME_WIDTH;
                canvasHeight -= GAME_HEIGHT;
                done = true;
            }
        }

        var xDiff = screenSize.width - canvasWidth;
        var yDiff = screenSize.height - canvasHeight;
        var factor = canvasWidth / GAME_WIDTH;

        gameWidth = canvasWidth / factor + xDiff / factor;
        gameHeight = canvasHeight / factor + yDiff / factor;

        canvasWidth = gameWidth * factor;
        canvasHeight = gameHeight * factor;
    }

    public static int getRealWidth() {
        return canvasWidth;
    }

    private static void makeFullScreen() {
        var env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var gd = env.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            // doesn't set a really fullscreen in win 10, even
            // with hint System.setProperty("sun.java2d.d3d", "false");
            // gd.setFullScreenWindow(frame);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
    }

    public static void init() {
        getBestSize();

        frame = new Frame();
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        frame.add(canvas);
        makeFullScreen();

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Game.quit();
            }
        });

        frame.setVisible(true);

        canvas.addKeyListener(new Input());
        canvas.requestFocus();
    }

    public static void startRendering() {
        new Thread(Renderer::gameActivity).start();
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    private static void gameActivity() {
        var gc = canvas.getGraphicsConfiguration();
        var vImage = gc.createCompatibleVolatileImage(gameWidth, gameHeight);
        var totalFrames = 0;

        while (true) {
            var startTime = System.nanoTime();
            totalFrames++;
            if (System.nanoTime() > lastFPSCheck + 1000000000) {
                lastFPSCheck = System.nanoTime();
                currentFPS = totalFrames;
                totalFrames = 0;
            }

            if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                vImage = gc.createCompatibleVolatileImage(gameWidth, gameHeight);
            }

            showGraphics(vImage);

            var totalTime = System.nanoTime() - startTime;

            if (totalTime < targetTime) {
                try {
                    Thread.sleep((targetTime - totalTime) / 1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void showGraphics(VolatileImage image) {
        var g = image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, gameWidth, gameHeight);

        World.update();
        Input.finishInput();
        World.render(g);

        g.setColor(Color.black);
        g.drawString("FPS: " + currentFPS +
                        " sprites: " + World.getSprites().size(),
                5, gameHeight - 15);
        g.dispose();
        g = canvas.getGraphics();
        g.drawImage(image, 0, 0, canvasWidth, canvasHeight, null);
        g.dispose();
    }
}
