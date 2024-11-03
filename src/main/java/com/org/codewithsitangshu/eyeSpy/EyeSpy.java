package com.org.codewithsitangshu.eyeSpy;

import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotBuilder;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotBuilderImpl;

import java.io.IOException;
import java.nio.file.Files;
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

        private Path baselineSnapshotpath;
        private Path currentSnapshotpath;
        private Path resultpath;
        private int similarity = 100;
        private boolean savesnapshot = true;
        private String snapshotType = "view-port";

        private EyeSpyConfigImpl() {};

        public static EyeSpyConfig get(){
            return config;
        }

        public EyeSpyConfig setSnapshotPath(Path path) {
            this.baselineSnapshotpath = path;
            resolvePath(path);
            return this;
        }

        @Override
        public EyeSpyConfig setSamplePath(Path path) {
            this.currentSnapshotpath = path;
            resolvePath(path);
            return this;
        }

        public EyeSpyConfig setResultPath(Path path) {
            this.resultpath = path;
            resolvePath(path);
            return this;
        }

        @Override
        public EyeSpyConfig setSnapshotType(String snapshotType) {
            this.snapshotType = snapshotType;
            return null;
        }

        public EyeSpyConfig setGlobalSimilarity(int cutoff) {
            this.similarity = cutoff;
            return this;
        }

        public Path getSnapshotPath() {
            return baselineSnapshotpath;
        }

        @Override
        public Path getSamplePath() {
            return currentSnapshotpath;
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

        @Override
        public String getSnapshotType() {
            return snapshotType;
        }

        public void reset() {
            this.baselineSnapshotpath = null;
            this.currentSnapshotpath = null;
            this.resultpath = null;
            this.similarity = 100;
            this.savesnapshot = true;
        }

        private void resolvePath(Path path) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new EyeSpyException(e.getMessage() + " " + path.toString());
            }
        }
    }

}
