package com.org.codewithsitangshu.eyeSpyTest.web.telegraph.pages;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.snapshot.Snap;
import com.org.codewithsitangshu.eyeSpyTest.web.google.pages.GoogleHomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Snap(value = "Telegraph-#{Title}.png",similarity = 100,type = "full")
public class TelegraphHomePage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(TelegraphHomePage.class);

    public TelegraphHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void goTo() {
        driver.get("https://www.telegraphindia.com/");
        logger.info("Navigated to Telegraph homepage.");
    }

    public EyeSpyResult compareWholePage() {
        return EyeSpy.snapshot()
                .from(this)
                .replaceAttribute("Title","HomePage")
                .sample()
                .using(driver)
                .compare();
    }

}
