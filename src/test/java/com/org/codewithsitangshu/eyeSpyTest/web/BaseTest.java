package com.org.codewithsitangshu.eyeSpyTest.web;

import com.org.codewithsitangshu.driver.DriverManager;
import com.org.codewithsitangshu.driver.DriverModule;
import com.org.codewithsitangshu.driver.DriverType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    DriverManager driverManager;
    protected WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void beforeTest(@Optional("Chrome") String browser) {
        logger.info("Starting beforeTest method...");
        try {
            logger.info("Initializing driver manager...");
            driverManager = DriverModule.getManager(DriverType.valueOf(browser.toUpperCase()));
            logger.info("Driver manager initialized successfully.");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid browser type provided: " + browser, e);
            throw new IllegalArgumentException("Invalid browser type provided: " + browser, e);
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        logger.info("Starting beforeMethod method...");
        driver = driverManager.getDriver();
        logger.info("WebDriver instance obtained successfully.");
    }

    @AfterTest
    public void afterTest() {
        logger.info("Starting afterTest method...");
        driverManager.quitDriver();
        logger.info("WebDriver quit successfully.");
    }

    @AfterSuite
    public void afterSuite() {
        logger.info("Starting afterSuite method...");
        driverManager.stopService();
        logger.info("Driver service stopped successfully.");
    }

}
