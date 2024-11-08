package org.utils;

import org.graphics.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageUtils {

    private static final String IMAGE_PATH = "/images/";

    private ImageUtils() {
    }

    public static BufferedImage loadImage(String path) throws RuntimeException {
        try {
            var resource = ImageUtils.class.getResource(IMAGE_PATH + path);
            assert resource != null;
            var rowImage = ImageIO.read(resource);
            var image = Renderer.getCanvas().getGraphicsConfiguration()
                    .createCompatibleImage(rowImage.getWidth(), rowImage.getHeight(), rowImage.getTransparency());
            image.getGraphics().drawImage(
                    rowImage, 0, 0, rowImage.getWidth(), rowImage.getHeight(), null);
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
