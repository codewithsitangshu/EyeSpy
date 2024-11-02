package com.org.codewithsitangshu.eyeSpyTest.web.google.pages;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.snapshot.Snap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Snap(value = "Google-#{Title}.png",similarity = 100)
public class GoogleHomePage {

    @FindBy(css = "div.RNNXgb")
    private WebElement searchBox;

    @FindBy(css = "div[aria-label='Search by voice']")
    private WebElement mic;

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(GoogleHomePage.class);

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void goTo() {
        driver.get("https://www.google.com");
        logger.info("Navigated to Google homepage.");
    }

    public EyeSpyResult compareWholePage() {
        return EyeSpy.snapshot()
                .from(this)
                .replaceAttribute("Title","HomePage")
                .sample()
                .using(driver)
                .compare();
    }

    public EyeSpyResult compareSearchBoxOnly() {
        return EyeSpy.snapshot()
                .from(this)
                .replaceAttribute("Title","SearchBox")
                .sample()
                .using(driver)
                .element(searchBox)
                .compare();
    }

    public EyeSpyResult compareSearchBoxIgnoreMic() {
        return EyeSpy.snapshot()
                .from(this)
                .replaceAttribute("Title","SearchBox-ignoreMic")
                .sample()
                .using(driver)
                .element(searchBox)
                .excluding(mic)
                .compare();
    }





}
