package org.utils;

import org.graphics.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageUtils {

    private ImageUtils() {
    }

    public static BufferedImage loadImage(String path) throws RuntimeException {
        BufferedImage rowImage;
        try {
            URL resource = ImageUtils.class.getResource("/images/" + path);
            assert resource != null;
            rowImage = ImageIO.read(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BufferedImage image = Renderer.getCanvas().getGraphicsConfiguration()
                .createCompatibleImage(rowImage.getWidth(), rowImage.getHeight(), rowImage.getTransparency());
        image.getGraphics().drawImage(rowImage, 0, 0, rowImage.getWidth(), rowImage.getHeight(), null);
        return image;
    }
}
