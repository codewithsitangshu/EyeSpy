package com.org.codewithsitangshu.eyeSpy.image;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.sample.SampleAttributes;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotAttributes;
import org.arquillian.rusheye.oneoff.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StoreImage {

    public void storeSample(BufferedImage sample, SampleAttributes sampleAttributes) {
        File sampleFile = sampleAttributes.getSamplePath().toFile();
        saveImage(sample, sampleFile);
    }

    public void storeSnap(BufferedImage sample, SnapshotAttributes snapshotAttribute) {
        File snapFile = snapshotAttribute.getSnapshotPath().toFile();
        if (!snapFile.exists() && EyeSpy.config().isSaveSnapshot()) {
            saveImage(sample, snapFile);
        }
    }

    public void storeOutput(BufferedImage output, SnapshotAttributes snapshotAttribute) {
        File outputFile = EyeSpy.config().getResultPath().resolve(snapshotAttribute.getSnapshotPath().getFileName()).toFile();
        saveImage(output, outputFile);
    }

    private void saveImage(BufferedImage result, File file) {
        try {
            ImageUtils.writeImage(result, file.getParentFile(), file.getName());
        } catch (IOException e) {
            throw new EyeSpyException("Unable to store image", e);
        }
    }

}
