package com.org.codewithsitangshu.eyeSpy;

import java.nio.file.Path;

public interface EyeSpyConfig {

    public EyeSpyConfig setSnapshotPath(Path path);
    public Path getSnapshotPath();

    public EyeSpyConfig setSamplePath(Path path);
    public Path getSamplePath();

    public EyeSpyConfig setResultPath(Path path);
    public Path getResultPath();

    public EyeSpyConfig setDevice(String device);
    public String getDevice();

    public EyeSpyConfig setGlobalSimilarity(int cutoff);
    public int getGlobalSimilarity();

    public EyeSpyConfig setSaveSnapshot(boolean save);
    public boolean isSaveSnapshot();

    public void reset();

}
