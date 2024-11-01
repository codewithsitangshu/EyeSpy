package com.org.codewithsitangshu.util.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxDriverManager extends DriverManager {

    private GeckoDriverService ffService;
    private static final Logger logger = LogManager.getLogger(FirefoxDriverManager.class);

    @Override
    public void startService() {
        if (null == ffService) {
            try {
                ffService = new GeckoDriverService.Builder()
                        .usingAnyFreePort()
                        .build();
                ffService.start();
                logger.info("GeckoDriverService started successfully.");
            } catch (Exception e) {
                logger.error("Error occurred while starting GeckoDriverService.", e);
            }
        }
    }

    @Override
    public void stopService() {
        if (null != ffService && ffService.isRunning()) {
            ffService.stop();
            logger.info("GeckoDriverService stopped successfully.");
        } else {
            logger.warn("GeckoDriverService is already stopped. No action needed.");
        }
    }

    @Override
    public void createDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.proxy.no_proxies_on", "localhost");
        profile.setPreference("javascript.enabled", true);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("Firefox");
        capabilities.setCapability("marionette", true);

        FirefoxOptions options = new FirefoxOptions();
        options.merge(capabilities);
        options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        options.addArguments("test-type");
        options.addPreference("browser.link.open_newwindow", 3);
        options.addPreference("browser.link.open_newwindow.restriction", 0);

        driver = new FirefoxDriver(ffService, options);
        logger.info("FirefoxDriver created successfully.");
    }

}