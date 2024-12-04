package com.org.codewithsitangshu.eyeSpy.sample;

import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.image.Masking;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.file.Path;
import java.util.List;

public interface SampleBuilder {
    public SampleBuilder using(WebDriver driver);
    public SampleBuilder using(AppiumDriver driver);
    public SampleBuilder using(Path path);
    public SampleBuilder including(WebElement element);
    public SampleBuilder excluding(WebElement element);
    public SampleBuilder excluding(List<WebElement> list);
    public SampleBuilder similarity(int cutoff);
    public SampleBuilder masking(Masking masking);
    public SampleBuilder capture();
    public EyeSpyResult compare();
}
