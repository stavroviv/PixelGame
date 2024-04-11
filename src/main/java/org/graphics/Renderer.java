package org.graphics;

import org.game.Game;
import org.input.Input;
import org.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

public class Renderer {

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

    private static int targetFPS = 100;
    private static int targetTime = 1000000000 / targetFPS;

    public static float camX = 0;
    public static float camY = 0;

    private static void getBestSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        boolean done = false;

        while (!done) {
            canvasWidth += GAME_WIDTH;
            canvasHeight += GAME_HEIGHT;

            if (canvasWidth > screenSize.getWidth() || canvasHeight > screenSize.getHeight()) {
                canvasWidth -= GAME_WIDTH;
                canvasHeight -= GAME_HEIGHT;
                done = true;
            }
        }

        int xDiff = screenSize.width - canvasWidth;
        int yDiff = screenSize.height - canvasHeight;
        int factor = canvasWidth / GAME_WIDTH;

        gameWidth = canvasWidth / factor + xDiff / factor;
        gameHeight = canvasHeight / factor + yDiff / factor;

        canvasWidth = gameWidth * factor + 1;
        canvasHeight = gameHeight * factor;
    }

    private static void makeFullScreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = env.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            // doesn't set a really fullscreen in win 10, even with hint System.setProperty("sun.java2d.d3d", "false");
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

        Thread thread = new Thread() {

            public void run() {

                GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
                VolatileImage vImage = gc.createCompatibleVolatileImage(gameWidth, gameHeight);
                int TotalFrames = 0;

                while (true) {
                    long startTime = System.nanoTime();

                    TotalFrames++;
                    if (System.nanoTime() > lastFPSCheck + 1000000000) {
                        lastFPSCheck = System.nanoTime();
                        currentFPS = TotalFrames;
                        TotalFrames = 0;
                    }

                    if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                        vImage = gc.createCompatibleVolatileImage(gameWidth, gameHeight);
                    }

                    Graphics g = vImage.getGraphics();

                    g.setColor(Color.black);
                    g.fillRect(0, 0, gameWidth, gameHeight);

                    // update
                    World.update();
                    Input.finishInput();

                    // RENDER

                    World.render(g);

                    // RENDER END

                    g.setColor(Color.black);
                    g.drawString("" + currentFPS, 5, gameHeight - 15);

                    g.dispose();

                    g = canvas.getGraphics();
                    g.drawImage(vImage, 0, 0, canvasWidth, canvasHeight, null);

                    g.dispose();

                    long totalTime = System.nanoTime() - startTime;

                    if (totalTime < targetTime) {
                        try {
                            Thread.sleep((targetTime - totalTime) / 1000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }


            }
        };
        thread.start();

    }

    public static BufferedImage loadImage(String path) throws IOException {
        BufferedImage rowImage = ImageIO.read(Renderer.class.getResource(path));
        BufferedImage finalImage = canvas.getGraphicsConfiguration()
                .createCompatibleImage(rowImage.getWidth(), rowImage.getHeight(), rowImage.getTransparency());
        finalImage.getGraphics().drawImage(rowImage, 0, 0, rowImage.getWidth(), rowImage.getHeight(), null);
        return finalImage;
    }
}
