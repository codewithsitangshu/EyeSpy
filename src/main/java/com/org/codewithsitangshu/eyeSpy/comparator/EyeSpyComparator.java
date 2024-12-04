package com.org.codewithsitangshu.eyeSpy.comparator;

import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.image.Masking;
import com.org.codewithsitangshu.eyeSpy.image.MaskingImage;
import com.org.codewithsitangshu.eyeSpy.image.StoreImage;
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

    private final ImageComparator imageComparator;

    public EyeSpyComparator() {
        imageComparator = new DefaultImageComparator();
    }

    public EyeSpyResult compare(SnapshotAttributes snapshotAttribute, BufferedImage sample,
                                SampleAttributes sampleAttributes, List<WebElement> exclusionList, Masking masking) {

        final File snapFile = snapshotAttribute.getSnapshotPath().toFile();
        final File sampleFile = sampleAttributes.getSamplePath().toFile();
        MaskingImage maskingImage = new MaskingImage();
        StoreImage storeImage = new StoreImage();

        BufferedImage snap = getImage(snapFile);

        //Mask Snap and Sample image
        if(masking == Masking.COMPARE) {
            snap = maskingImage.maskElements(snap, exclusionList);
            sample = maskingImage.maskElements(sample, exclusionList);
        }

        ComparisonResult result = imageComparator.compare(snap, sample,
                snapshotAttribute.getPerception(),
                snapshotAttribute.getMasks());

        storeImage.storeOutput(result.getDiffImage(),snapshotAttribute);
        return new EyeSpyResult(result, snapshotAttribute.getSimilarity());
    }

    private BufferedImage getImage(final File file){
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(file);
        } catch (IOException e) {
            throw new EyeSpyException(e.getMessage() + " - " + file.getAbsolutePath());
        }
        return pattern;
    }


}
