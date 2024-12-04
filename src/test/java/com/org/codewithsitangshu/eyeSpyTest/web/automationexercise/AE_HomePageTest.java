package com.org.codewithsitangshu.eyeSpyTest.web.automationexercise;

import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpyTest.web.BaseTest;
import com.org.codewithsitangshu.eyeSpyTest.web.automationexercise.pages.AE_HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AE_HomePageTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(AE_HomePageTest.class);

    private AE_HomePage homePage;

    @BeforeMethod
    public void launchHomePage() {
        homePage = new AE_HomePage(driver);
        homePage.goTo();
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        Assert.assertEquals(title, "Automation Exercise", "Page title mismatch.");
        logger.info("Home page launched successfully.");
    }

    @Test(description = "Test Full Home Page of Automation Exercise")
    public void fullHomePageTest() {
        List<EyeSpyResult> result = homePage.compareFullHomePage();
        SoftAssert softAssert = new SoftAssert();
        AtomicInteger index = new AtomicInteger(0);

        result.forEach(rst -> {
            int currentIndex = index.getAndIncrement();
            softAssert.assertTrue(
                    rst.getSimilarity() >= 90,
                    String.format("Home Page section - %s did not match with the sample one. " +
                            "Similarity is %s", currentIndex, rst.getSimilarity())
            );
        });

        softAssert.assertAll();
    }

    @Test(description = "Test Home Page view port of Automation Exercise")
    public void homePageViewPortTest() {
        EyeSpyResult result = homePage.compareViewPortHomePage();
        Assert.assertTrue(result.getSimilarity() >= 95,
                String.format("Home Page did not matched with sample one. Similarity is %s", result.getSimilarity())
        );
    }


}
