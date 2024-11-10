package com.org.codewithsitangshu.eyeSpy.capture;

import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.image.Masking;
import com.org.codewithsitangshu.eyeSpy.image.MaskingImage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class CaptureMobile implements Capture {

    private final AppiumDriver driver;
    private final MaskingImage maskingImage;

    public CaptureMobile(AppiumDriver driver) {
        this.driver = driver;
        this.maskingImage = new MaskingImage();
    }

    @Override
    public BufferedImage capturePageSnapshot(Masking masking, List<WebElement> exclude) {
        BufferedImage pageSnapshot = null;
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            pageSnapshot = ImageIO.read(screen);
            if(masking == Masking.SAMPLE) {
                pageSnapshot = this.maskingImage.maskElements(pageSnapshot,exclude);
            }
        } catch (Exception e) {
            throw new EyeSpyException("Unable to get page snapshot", e);
        }
        return pageSnapshot;
    }

    @Override
    public BufferedImage captureSnapshotElement(Masking masking, WebElement element, List<WebElement> exclude) {
        BufferedImage elementSnapshot = null;
        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        return capturePageSnapshot(masking,exclude).getSubimage(p.getX(), p.getY(), width, height);
    }

}
