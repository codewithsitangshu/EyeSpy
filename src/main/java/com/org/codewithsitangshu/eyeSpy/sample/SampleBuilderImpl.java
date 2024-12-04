package com.org.codewithsitangshu.eyeSpy.sample;

import com.org.codewithsitangshu.eyeSpy.Eye;
import com.org.codewithsitangshu.eyeSpy.capture.Capture;
import com.org.codewithsitangshu.eyeSpy.capture.CaptureMobile;
import com.org.codewithsitangshu.eyeSpy.capture.CaptureWeb;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyComparator;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.image.Masking;
import com.org.codewithsitangshu.eyeSpy.image.StoreImage;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotAttributes;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class SampleBuilderImpl implements SampleBuilder {

    private Capture capture;
    private BufferedImage sample;
    private List<WebElement> excludeElements;
    private WebElement includeElement;
    private final SnapshotAttributes snapshotAttributes;
    private SampleAttributes sampleAttributes;
    private final StoreImage storeImage;
    private Masking masking;
    private final EyeSpyComparator eyeSpyComparator;

    public SampleBuilderImpl(SnapshotAttributes snapshotAttributes) {
        this.snapshotAttributes = snapshotAttributes;
        excludeElements = new LinkedList<>();
        sampleAttributes = new SampleAttributes();
        storeImage = new StoreImage();
        masking = Masking.COMPARE;
        eyeSpyComparator = new EyeSpyComparator();
    }

    @Override
    public SampleBuilder using(WebDriver driver) {
        this.capture = new CaptureWeb(driver);
        resolvePath();
        return this;
    }

    @Override
    public SampleBuilder using(AppiumDriver driver) {
        this.capture = new CaptureMobile(driver);
        resolvePath();
        return this;
    }

    @Override
    public SampleBuilder using(Path path) {
        try {
            this.sample = ImageIO.read(resolvePath(path).toFile());
            this.sampleAttributes.setSamplePath(path);
        } catch (IOException e) {
            throw new EyeSpyException(e.getMessage() + " " + path.toString());
        }
        return this;
    }

    @Override
    public SampleBuilder including(WebElement element) {
        this.includeElement = element;
        return this;
    }

    @Override
    public SampleBuilder excluding(WebElement element) {
        this.excludeElements.add(element);
        return this;
    }

    @Override
    public SampleBuilder excluding(List<WebElement> list) {
        this.excludeElements.addAll(list);
        return this;
    }

    @Override
    public SampleBuilder similarity(int cutoff) {
        this.snapshotAttributes.setSimilarity(cutoff);
        return this;
    }

    @Override
    public SampleBuilder masking(Masking masking) {
        this.masking = masking;
        return this;
    }

    @Override
    public SampleBuilder capture() {
        if (null == this.sample) {
            if (includeElement == null) {
                this.sample = this.capture.capturePageSnapshot(this.masking,excludeElements);
            } else {
                this.sample = this.capture.captureSnapshotElement(this.masking,includeElement,excludeElements);
            }
        }
        this.storeImage.storeSnap(this.sample,this.snapshotAttributes);
        this.storeImage.storeSample(this.sample,this.sampleAttributes);
        return this;
    }

    @Override
    public EyeSpyResult compare() {
        return this.eyeSpyComparator.compare(this.snapshotAttributes, this.sample, this.sampleAttributes, this.excludeElements,this.masking);
    }

    private void resolvePath() {
        sampleAttributes.setSamplePath(Eye.open().getSamplePath().resolve(snapshotAttributes.getSnapValue()));
    }

    private Path resolvePath(Path path){
        return path.isAbsolute() ? path : Eye.open().getSamplePath().resolve(path);
    }


}
