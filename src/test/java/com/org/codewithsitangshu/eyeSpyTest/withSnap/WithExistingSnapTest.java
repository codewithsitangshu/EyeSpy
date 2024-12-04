package com.org.codewithsitangshu.eyeSpyTest.withSnap;

import com.org.codewithsitangshu.eyeSpy.Eye;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class WithExistingSnapTest {

    private static final Logger logger = LogManager.getLogger(WithExistingSnapTest.class);
    private final String snapshotPath = "src/test/resources/images/baseline/snap";
    private final String samplePath = "src/test/resources/images/current/snap";
    private final String resultPath = "src/test/resources/images/result/snap";
    private final boolean isSaveSnapshot = false;

    @BeforeTest()
    public void openEye() {
        Eye.open()
                .setResultPath(Paths.get(resultPath))
                .setSnapshotPath(Paths.get(snapshotPath))
                .setSamplePath(Paths.get(samplePath))
                .setGlobalSimilarity(100)
                .setSaveSnapshot(isSaveSnapshot);
        logger.info("Open Eye with below configuration.");
        logger.info("Snapshot Path : " + snapshotPath);
        logger.info("Sample Path : " + samplePath);
        logger.info("Result Path : " + resultPath);
        logger.info("Save Snapshot : " + isSaveSnapshot);
    }

    @Test(description = "Compare with 2 snapshot")
    public void compare() {

        /*EyeSpyResult result = Eye.snapshot()
                .from(Paths.get("src/test/resources/images/baseline/snap/Flipkart-HomePage.png"))
                .sample()
                .using(Paths.get("src/test/resources/images/current/snap/Flipkart-HomePage.png"))
                .compare();*/

        EyeSpyResult result = Eye.snapshot()
                .from(Paths.get("Flipkart-HomePage.png"))
                .sample()
                .using(Paths.get("Flipkart-HomePage.png"))
                .compare();

        Assert.assertTrue(result.getSimilarity() >= 97, "Snap is mismatched with sample one." +
                "Mismatch % is " + (100 - result.getSimilarity()));

    }

    @AfterTest
    public void closeEye() {
        Eye.close();
        logger.info("Close the Eye.");
    }


}
