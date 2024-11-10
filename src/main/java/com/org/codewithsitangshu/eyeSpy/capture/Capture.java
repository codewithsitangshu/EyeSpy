package com.org.codewithsitangshu.eyeSpy.capture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

public interface Capture {
    public BufferedImage capturePageSnapshot();
    public BufferedImage captureSnapshotElement(WebElement element);
}

