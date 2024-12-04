package com.org.codewithsitangshu.eyeSpy;

import java.nio.file.Path;

public interface EyeConfig {

    public EyeConfig setSnapshotPath(Path path);
    public Path getSnapshotPath();

    public EyeConfig setSamplePath(Path path);
    public Path getSamplePath();

    public EyeConfig setResultPath(Path path);
    public Path getResultPath();

    public EyeConfig setGlobalSimilarity(int cutoff);
    public int getGlobalSimilarity();

    public EyeConfig setSaveSnapshot(boolean save);
    public boolean isSaveSnapshot();

    public void close();

}
