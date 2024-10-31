package com.org.codewithsitangshu.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeDriverManager extends DriverManager {

    private ChromeDriverService chService;
    private static final Logger logger = LogManager.getLogger(ChromeDriverManager.class);

    @Override
    public void startService() {
        if (null == chService) {
            try {
                chService = new ChromeDriverService.Builder()
                        .usingAnyFreePort()
                        .build();
                chService.start();
                logger.info("ChromeDriverService started successfully.");
            } catch (Exception e) {
                logger.error("Error occurred while starting ChromeDriverService.", e);
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chService && chService.isRunning()) {
            chService.stop();
            logger.info("ChromeDriverService stopped successfully.");
        } else {
            logger.warn("ChromeDriverService is already stopped. No action needed.");
        }
    }

    @Override
    public void createDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("Chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.merge(capabilities);
        driver = new ChromeDriver(chService, options);
        logger.info("ChromeDriver created successfully.");
    }

}