package com.markyang.framework.util;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 图片工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class ImageUtils {

    @SneakyThrows
    public static InputStream trimWhiteArea(InputStream image) {
        BufferedImage bufferedImage = ImageIO.read(image);
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        BufferedImage trimmedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        trimmedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(trimmedImage, "png", byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
