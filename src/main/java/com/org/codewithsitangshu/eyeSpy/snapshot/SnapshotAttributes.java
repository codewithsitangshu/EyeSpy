package com.org.codewithsitangshu.eyeSpy.snapshot;

import org.arquillian.rusheye.suite.Mask;
import org.arquillian.rusheye.suite.Perception;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class SnapshotAttributes {

    private Path snapshotPath;
    private int similarity = -1;

    public Path getSnapshotPath() {
        return snapshotPath;
    }

    public void setSnapshotPath(Path snapshotPath) {
        this.snapshotPath = snapshotPath;
    }

    public int getSimilarity() {
        return similarity;
    }

    public void setSimilarity(int similarity) {
        this.similarity = similarity;
    }

    public Perception getPerception(){
        Perception perception = new Perception();
        perception.setOnePixelTreshold(0f);
        perception.setGlobalDifferenceTreshold(0f);
        return perception;
    }

    public Set<Mask> getMasks(){
        return new HashSet<Mask>();
    }

}
