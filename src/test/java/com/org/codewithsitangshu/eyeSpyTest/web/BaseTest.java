package com.org.codewithsitangshu.eyeSpyTest.web;

import com.org.codewithsitangshu.eyeSpy.Eye;
import com.org.codewithsitangshu.util.driver.DriverManager;
import com.org.codewithsitangshu.util.driver.DriverModule;
import com.org.codewithsitangshu.util.driver.DriverType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.nio.file.Paths;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    DriverManager driverManager;
    protected WebDriver driver;
    private final String snapshotPath = "images/baseline";
    private final String samplePath = "images/current";
    private final String resultPath = "images/result";
    private final boolean isSaveSnapshot = true;

    @BeforeTest
    @Parameters("browser")
    public void initDriver(@Optional("Chrome") String browser) {
        try {
            logger.info("Initializing driver manager...");
            driverManager = DriverModule.getManager(DriverType.valueOf(browser.toUpperCase()));
            logger.info("Driver manager initialized successfully.");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid browser type provided: " + browser, e);
            throw new IllegalArgumentException("Invalid browser type provided: " + browser, e);
        }
    }

    @BeforeTest()
    public void openEye() {
        Eye.open()
                .setResultPath(Paths.get(resultPath))
                .setSnapshotPath(Paths.get(snapshotPath))
                .setSamplePath(Paths.get(samplePath))
                //.setGlobalSimilarity(100)
                .setSaveSnapshot(isSaveSnapshot);
        logger.info("Open Eye with below configuration.");
        logger.info("Snapshot Path : " + snapshotPath);
        logger.info("Sample Path : " + samplePath);
        logger.info("Result Path : " + resultPath);
        logger.info("Save Snapshot : " + isSaveSnapshot);
    }

    @BeforeMethod
    public void getDriver() {
        driver = driverManager.getDriver();
        logger.info("WebDriver instance obtained successfully.");
    }

    @AfterTest
    public void quitDriver() {
        driverManager.quitDriver();
        logger.info("WebDriver quit successfully.");
    }

    @AfterTest
    public void closeEye() {
        Eye.close();
        logger.info("Close the Eye.");
    }

    @AfterSuite
    public void stopDriverService() {
        logger.info("Starting afterSuite method...");
        driverManager.stopService();
        logger.info("Driver service stopped successfully.");
    }

}
