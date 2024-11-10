package com.org.codewithsitangshu.eyeSpyTest.web.pages;

import com.org.codewithsitangshu.eyeSpy.Eye;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.snapshot.Snap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Snap(value = "Home-#{Title}.png",similarity = 90)
public class HomePage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void goTo() {
        driver.get("https://automationexercise.com/");
        logger.info("Navigated to homepage.");
    }

    public List<EyeSpyResult> compareFullHomePage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int viewportHeight = driver.manage().window().getSize().getHeight();
        long pageHeight = (long) js.executeScript("return document.body.scrollHeight");

        int currentPosition = 0;
        int screenshotCount = 1;
        List<EyeSpyResult> eyeSpyResults = new ArrayList<>();

        while (currentPosition < pageHeight) {
            eyeSpyResults.add(Eye.snapshot()
                    .from(this)
                    .replaceValuePlaceholder("Title","Section-"+screenshotCount)
                    .sample()
                    .using(driver)
                    .capture()
                    .compare());
            // Scroll down
            currentPosition += viewportHeight;
            js.executeScript("window.scrollTo(0, " + currentPosition + ");");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            screenshotCount++;
        }
        return eyeSpyResults;
    }

}
