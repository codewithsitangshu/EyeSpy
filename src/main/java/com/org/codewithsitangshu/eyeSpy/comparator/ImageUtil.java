package com.org.codewithsitangshu.eyeSpy.comparator;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import org.arquillian.rusheye.oneoff.ImageUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageUtil {

    private final static AlphaComposite COMPOSITE = AlphaComposite.getInstance(AlphaComposite.CLEAR);
    private final static Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static BufferedImage getPageSnapshot(WebDriver driver, String snapshotType) {
        BufferedImage page = null;
        if (snapshotType.equalsIgnoreCase("view-port")) {
            page = getPageViewPortSnapshot(driver);
        } else if (snapshotType.equalsIgnoreCase("full")) {
            page = getPageFullSnapshot(driver);
        }
        return page;
    }

    private static BufferedImage getPageFullSnapshot(WebDriver driver) {
        BufferedImage page = null;
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            page = screenshot.getImage();
        } catch (Exception e) {
            throw new EyeSpyException("Unable to get page snapshot", e);
        }
        return page;
    }

    private static BufferedImage getPageViewPortSnapshot(WebDriver driver) {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage page = null;
        try {
            page = ImageIO.read(screen);
        } catch (Exception e) {
            throw new EyeSpyException("Unable to get page snapshot", e);
        }
        return page;
    }

    public static BufferedImage getElementSnapshot(WebDriver driver, WebElement element,String snapshotType) {
        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        return getPageSnapshot(driver,snapshotType).getSubimage(p.getX(), p.getY(), width, height);
    }

    public static BufferedImage maskElement(BufferedImage img, WebElement element) {
        return maskArea(img, element);
    }

    public static BufferedImage maskElements(BufferedImage img, List<WebElement> elements) {
        for (WebElement element : elements) {
            img = maskArea(img, element);
        }
        return img;
    }

    public static void saveImage(BufferedImage result, File file) {
        try {
            ImageUtils.writeImage(result, file.getParentFile(), file.getName());
        } catch (IOException e) {
            throw new EyeSpyException("Unable to write the difference", e);
        }
    }

    private static BufferedImage maskArea(BufferedImage img, WebElement element) {
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
