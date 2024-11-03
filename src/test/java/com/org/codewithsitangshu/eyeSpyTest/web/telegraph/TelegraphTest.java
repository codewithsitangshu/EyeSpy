package com.org.codewithsitangshu.eyeSpyTest.web.telegraph;

import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpyTest.web.BaseTest;
import com.org.codewithsitangshu.eyeSpyTest.web.google.GoogleTest;
import com.org.codewithsitangshu.eyeSpyTest.web.google.pages.GoogleHomePage;
import com.org.codewithsitangshu.eyeSpyTest.web.telegraph.pages.TelegraphHomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TelegraphTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(TelegraphTest.class);

    private TelegraphHomePage telegraphHomePage;

    @BeforeMethod
    public void launchTelegraphHomePage() {
        telegraphHomePage = new TelegraphHomePage(driver);
        telegraphHomePage.goTo();
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        Assert.assertEquals(title, "Telegraph India | Latest News, Top Stories, Opinion, News Analysis and Comments",
                "Page title mismatch.");
        logger.info("Telegraph page title is matched Test completed successfully.");
    }

    @Test
    public void telegraphHomePageTest() {
        EyeSpyResult result = telegraphHomePage.compareWholePage();
        Assert.assertEquals(result.getSimilarity(), 100, "Home page is mismatched with sample one." +
                "Mismatch % is " + (100 - result.getSimilarity()));
    }

}
