package com.org.codewithsitangshu.eyeSpy.image;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MaskingImage {

    private final AlphaComposite COMPOSITE = AlphaComposite.getInstance(AlphaComposite.CLEAR);
    private final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public BufferedImage maskElement(BufferedImage img, WebElement element) {
        return maskArea(img, element);
    }

    public BufferedImage maskElements(BufferedImage img, List<WebElement> elements) {
        for (WebElement element : elements) {
            img = maskArea(img, element);
        }
        return img;
    }

    private BufferedImage maskArea(BufferedImage img, WebElement element) {
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.setComposite(COMPOSITE);
        g2d.setColor(TRANSPARENT);

        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        g2d.fillRect(p.getX(), p.getY(), width, height);

        return img;
    }

}
