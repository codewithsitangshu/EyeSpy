package com.org.codewithsitangshu.eyeSpyTest.web.google;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.snapshot.Snap;
import com.org.codewithsitangshu.eyeSpyTest.web.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.nio.file.Paths;

@Snap("Google-Home.png")
public class GoogleTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(GoogleTest.class);

    @BeforeTest
    public void eyeSpyConfig() {
        EyeSpy.config()
                .setResultPath(Paths.get("images/result"))
                .setSnapshotPath(Paths.get("images/baseline"))
                .setGlobalSimilarity(100)
                .isSaveSnapshot(false);
    }

    @BeforeMethod
    public void launchGoogleHomePage() {
        driver.get("https://www.google.com");
        logger.info("Navigated to Google homepage.");
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        Assert.assertEquals(title, "Google", "Page title mismatch.");
        logger.info("Google Test completed successfully.");
    }



    @Test
    public void googleHomePageTest() {
        EyeSpy.snapshot().from(this)
                .sample().using(driver)
                .compare();
    }




}
