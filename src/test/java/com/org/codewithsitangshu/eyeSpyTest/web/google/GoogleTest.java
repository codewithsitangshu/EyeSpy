package com.org.codewithsitangshu.eyeSpyTest.web.google;

import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpyTest.web.BaseTest;
import com.org.codewithsitangshu.eyeSpyTest.web.google.pages.GoogleHomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(GoogleTest.class);

    private GoogleHomePage googleHomePage;

    @BeforeMethod
    public void launchGoogleHomePage() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.goTo();
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        Assert.assertEquals(title, "Google", "Page title mismatch.");
        logger.info("Google Test completed successfully.");
    }

    @Test
    public void googleHomePageTest() {
        EyeSpyResult result = googleHomePage.compareWholePage();
        Assert.assertEquals(result.getSimilarity(), 100, "Home page is mismatched with sample one." +
                "Mismatch % is " + (100 - result.getSimilarity()));
    }

    @Test
    public void matchSearchBoxOnlyTest() {
        EyeSpyResult result = googleHomePage.compareSearchBoxOnly();
        Assert.assertEquals(result.getSimilarity(), 100, "Search box is mismatched with sample one." +
                "Mismatch % is " + (100 - result.getSimilarity()));
    }

    @Test
    public void matchSearchBoxWithoutMicTest() {
        EyeSpyResult result = googleHomePage.compareSearchBoxIgnoreMic();
        Assert.assertEquals(result.getSimilarity(), 100, "Search box without Mic is mismatched with sample one." +
                "Mismatch % is " + (100 - result.getSimilarity()));
    }


}
