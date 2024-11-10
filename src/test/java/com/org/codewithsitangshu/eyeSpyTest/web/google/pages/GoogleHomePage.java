package com.org.codewithsitangshu.eyeSpyTest.web.google.pages;

import com.org.codewithsitangshu.eyeSpy.Eye;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.image.Masking;
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

    @FindBy(css = "div[aria-label='Search by image']")
    private WebElement searchByImage;

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

    public EyeSpyResult compareHomePage() {
        return Eye.snapshot()
                .from(this)
                .replaceValuePlaceholder("Title","HomePage")
                .sample()
                .using(driver)
                .capture()
                .compare();
    }

    public EyeSpyResult compareSearchBoxOnly() {
        return Eye.snapshot()
                .from(this)
                .replaceValuePlaceholder("Title","SearchBox")
                .sample()
                .using(driver)
                .including(searchBox)
                .capture()
                .compare();
    }

    public EyeSpyResult compareHomePageIgnoreMic() {
        return Eye.snapshot()
                .from(this)
                .replaceValuePlaceholder("Title","HomePage-ignoreMic")
                .sample()
                .using(driver)
                .excluding(mic)
                .capture()
                .compare();
    }

    public EyeSpyResult compareSearchBoxIgnoreMic() {
        return Eye.snapshot()
                .from(this)
                .replaceValuePlaceholder("Title","SearchBox-ignoreMic")
                .sample()
                .using(driver)
                .including(searchBox)
                .excluding(mic)
                .masking(Masking.SAMPLE)
                .capture()
                .compare();
    }

    public EyeSpyResult compareSearchBoxIgnoreMicAndImage() {
        return Eye.snapshot()
                .from(this)
                .replaceValuePlaceholder("Title","SearchBox-ignoreMicAndImage")
                .sample()
                .using(driver)
                .including(searchBox)
                .excluding(mic)
                .excluding(searchByImage)
                .masking(Masking.SAMPLE)
                .capture()
                .compare();
    }

}
