package com.org.codewithsitangshu.eyeSpyTest.web.test;

import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpyTest.web.BaseTest;
import com.org.codewithsitangshu.eyeSpyTest.web.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HomePageTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(HomePageTest.class);

    private HomePage homePage;

    @BeforeMethod
    public void launchHomePage() {
        homePage = new HomePage(driver);
        homePage.goTo();
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        Assert.assertEquals(title, "Automation Exercise", "Page title mismatch.");
        logger.info("Home page launched successfully.");
    }

    @Test
    public void googleHomePageTest() {
        List<EyeSpyResult> result = homePage.compareFullHomePage();
        SoftAssert softAssert = new SoftAssert();
        AtomicInteger index = new AtomicInteger(0);

        result.forEach(rst -> {
            int currentIndex = index.getAndIncrement();
            softAssert.assertTrue(
                    rst.getSimilarity() >= 90,
                    "Home Page section " + currentIndex + " did not match with the sample one. Similarity was " + rst.getSimilarity()
            );
        });

        softAssert.assertAll();
    }



}
