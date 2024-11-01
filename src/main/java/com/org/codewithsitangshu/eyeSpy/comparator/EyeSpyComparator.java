package com.org.codewithsitangshu.eyeSpy.comparator;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.sample.SampleAttributes;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotAttributes;
import org.arquillian.rusheye.comparison.ImageComparator;
import org.arquillian.rusheye.core.DefaultImageComparator;
import org.arquillian.rusheye.suite.ComparisonResult;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EyeSpyComparator {

    private static final ImageComparator imageComparator = new DefaultImageComparator();

    public static EyeSpyResult compare(SnapshotAttributes snapshotAttribute, BufferedImage sample,
                                       SampleAttributes sampleAttributes, List<WebElement> exclusionList) {

        final File patternFile = snapshotAttribute.getSnapshotPath().toFile();
        final File sampleFile = sampleAttributes.getSamplePath().toFile();

        if(!patternFile.exists() && EyeSpy.config().canSaveSnapshot()){
            ImageUtil.saveImage(sample, patternFile);
        }

        ImageUtil.saveImage(sample,sampleFile);

        //pattern
        BufferedImage pattern = getImage(patternFile);
        pattern = ImageUtil.maskElements(pattern, exclusionList);

        //sample
        sample = ImageUtil.maskElements(sample, exclusionList);

        ComparisonResult result = imageComparator.compare(pattern,
                sample,
                snapshotAttribute.getPerception(),
                snapshotAttribute.getMasks());

        File outputFile = EyeSpy.config().getResultPath().resolve(snapshotAttribute.getSnapshotPath().getFileName()).toFile();
        ImageUtil.saveImage(result.getDiffImage(), outputFile);
        return new EyeSpyResult(result, snapshotAttribute.getSimilarity());
    }

    private static BufferedImage getImage(final File file){
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(file);
        } catch (IOException e) {

            throw new EyeSpyException(e.getMessage() + " - " + file.getAbsolutePath());
        }
        return pattern;
    }


}
