package com.org.codewithsitangshu.eyeSpy;

import java.nio.file.Path;

public interface EyeSpyConfig {
    public EyeSpyConfig setSnapshotPath(Path path);
    public EyeSpyConfig setSamplePath(Path path);
    public EyeSpyConfig setResultPath(Path path);
    public EyeSpyConfig setGlobalSimilarity(int cutoff);
    public EyeSpyConfig isSaveSnapshot(boolean save);
    public Path getSnapshotPath();
    public Path getSamplePath();
    public Path getResultPath();
    public int getGlobalSimilarity();
    public boolean canSaveSnapshot();
    public void reset();
}
