package com.org.codewithsitangshu.eyeSpy;

import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotBuilder;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotBuilderImpl;

import java.nio.file.Path;

public class EyeSpy {

    public static SnapshotBuilder snapshot(){
        return new SnapshotBuilderImpl();
    }

    public static EyeSpyConfig config(){
        return EyeSpyConfigImpl.get();
    }

    private static class EyeSpyConfigImpl implements EyeSpyConfig {

        private static final EyeSpyConfig config = new EyeSpyConfigImpl();

        private Path snapshotpath;
        private Path resultpath;
        private int similarity = 100;
        private boolean savesnapshot = true;

        private EyeSpyConfigImpl() {};

        public static EyeSpyConfig get(){
            return config;
        }

        public EyeSpyConfig setSnapshotPath(Path path) {
            this.snapshotpath = path;
            return this;
        }

        public EyeSpyConfig setResultPath(Path path) {
            this.resultpath = path;
            return this;
        }

        public EyeSpyConfig setGlobalSimilarity(int cutoff) {
            this.similarity = cutoff;
            return this;
        }

        public Path getSnapshotPath() {
            return snapshotpath;
        }

        public Path getResultPath() {
            return resultpath;
        }

        public int getGlobalSimilarity() {
            return similarity;
        }

        public EyeSpyConfig isSaveSnapshot(boolean save) {
            this.savesnapshot = save;
            return this;
        }

        public boolean canSaveSnapshot() {
            return this.savesnapshot;
        }

        public void reset() {
            this.snapshotpath = null;
            this.resultpath = null;
            this.similarity = 100;
            this.savesnapshot = true;
        }
    }

}
