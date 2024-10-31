package com.org.codewithsitangshu.eyeSpy.sample;

import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyComparator;
import com.org.codewithsitangshu.eyeSpy.comparator.EyeSpyResult;
import com.org.codewithsitangshu.eyeSpy.comparator.ImageUtil;
import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotAttributes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class SampleBuilderImpl implements SampleBuilder {

    private WebDriver driver;
    private BufferedImage sample;
    private List<WebElement> exclusionList = new LinkedList<WebElement>();
    private SnapshotAttributes snapshotAttributes;

    public SampleBuilderImpl(SnapshotAttributes snapshotAttributes) {
        this.snapshotAttributes = snapshotAttributes;
    }

    public SampleBuilder using(WebDriver driver) {
        this.driver = driver;
        return this;
    }

    public SampleBuilder using(Path path) {
        try {
            this.sample = ImageIO.read(path.toFile());
        } catch (IOException e) {
            throw new EyeSpyException(e.getMessage() + " " + path.toString());
        }
        return this;
    }

    public SampleBuilder element(WebElement element) {
        this.sample = ImageUtil.getElementSnapshot(this.driver, element);
        return this;
    }

    public SampleBuilder excluding(WebElement element) {
        exclusionList.add(element);
        return this;
    }

    public SampleBuilder excluding(List<WebElement> list) {
        exclusionList.addAll(list);
        return this;
    }

    public SampleBuilder similarity(int cutoff) {
        this.snapshotAttributes.setSimilarity(cutoff);
        return this;
    }

    public EyeSpyResult compare() {
        if(null==this.sample){
            this.sample =ImageUtil.getPageSnapshot(this.driver);
        }
        return EyeSpyComparator.compare(this.snapshotAttributes, this.sample, this.exclusionList);
    }


}
