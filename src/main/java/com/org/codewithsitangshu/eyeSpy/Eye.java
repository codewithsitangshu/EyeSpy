package com.org.codewithsitangshu.eyeSpy;

import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotBuilder;
import com.org.codewithsitangshu.eyeSpy.snapshot.SnapshotBuilderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Eye {

    public static SnapshotBuilder snapshot(){
        return new SnapshotBuilderImpl();
    }

    public static EyeConfig open(){
        return EyeConfigImpl.get();
    }

    public static void close(){
        EyeConfigImpl.get().close();
    }

    private static class EyeConfigImpl implements EyeConfig {

        private static final EyeConfig config = new EyeConfigImpl();

        private Path baselineSnapshotpath;
        private Path currentSnapshotpath;
        private Path resultpath;
        private int similarity = 100;
        private boolean saveSnapshot = true;
        private String device = "web";

        private EyeConfigImpl() {};

        public static EyeConfig get(){
            return config;
        }

        public EyeConfig setSnapshotPath(Path path) {
            this.baselineSnapshotpath = path;
            resolvePath(path);
            return this;
        }

        @Override
        public EyeConfig setSamplePath(Path path) {
            this.currentSnapshotpath = path;
            resolvePath(path);
            return this;
        }

        public EyeConfig setResultPath(Path path) {
            this.resultpath = path;
            resolvePath(path);
            return this;
        }

        public EyeConfig setGlobalSimilarity(int cutoff) {
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

        public EyeConfig setSaveSnapshot(boolean save) {
            this.saveSnapshot = save;
            return this;
        }

        public boolean isSaveSnapshot() {
            return this.saveSnapshot;
        }

        public void close() {
            this.baselineSnapshotpath = null;
            this.currentSnapshotpath = null;
            this.resultpath = null;
            this.similarity = 100;
            this.saveSnapshot = true;
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
