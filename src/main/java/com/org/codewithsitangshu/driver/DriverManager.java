package com.org.codewithsitangshu.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    protected abstract void startService();

    public abstract void stopService();

    protected abstract void createDriver();

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
            logger.info("WebDriver quit successfully.");
        } else {
            logger.warn("WebDriver is already null. No action needed.");
        }
    }

    public WebDriver getDriver() {
        if (null == driver) {
            startService();
            createDriver();
            logger.info("WebDriver initialized successfully.");
        }
        return driver;
    }
}
