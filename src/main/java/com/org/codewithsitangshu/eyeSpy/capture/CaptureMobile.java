package com.org.codewithsitangshu.eyeSpy.capture;

import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CaptureMobile implements Capture {

    private final AppiumDriver driver;

    public CaptureMobile(AppiumDriver driver) {
        this.driver = driver;
    }

    @Override
    public BufferedImage capturePageSnapshot() {
        BufferedImage pageSnapshot = null;
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            pageSnapshot = ImageIO.read(screen);
        } catch (Exception e) {
            throw new EyeSpyException("Unable to get page snapshot", e);
        }
        return pageSnapshot;
    }

    @Override
    public BufferedImage captureSnapshotElement(WebElement element) {
        BufferedImage elementSnapshot = null;
        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        return capturePageSnapshot().getSubimage(p.getX(), p.getY(), width, height);
    }

}
