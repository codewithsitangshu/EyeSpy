package com.org.codewithsitangshu.eyeSpy.capture;

import com.org.codewithsitangshu.eyeSpy.image.Masking;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Capture {
    public BufferedImage capturePageSnapshot(Masking masking, List<WebElement> exclude);
    public BufferedImage captureSnapshotElement(Masking masking, WebElement element, List<WebElement> exclude);
}

