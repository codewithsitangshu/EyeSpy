package com.org.codewithsitangshu.eyeSpy.snapshot;

import com.org.codewithsitangshu.eyeSpy.EyeSpy;
import com.org.codewithsitangshu.eyeSpy.exception.EyeSpyException;
import com.org.codewithsitangshu.eyeSpy.sample.SampleBuilder;
import com.org.codewithsitangshu.eyeSpy.sample.SampleBuilderImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SnapshotBuilderImpl implements SnapshotBuilder {

    private SnapshotAttributes snapshotAttributes = new SnapshotAttributes();

    @Override
    public <T> SnapshotBuilder from(Class<T> pageClass) {
        getSnapAnnotation(pageClass);
        return this;
    }

    @Override
    public SnapshotBuilder from(Object object) {
        getSnapAnnotation(object.getClass());
        return this;
    }

    @Override
    public SnapshotBuilder from(Path path) {
        resolvePath(path);
        return this;
    }

    @Override
    public SnapshotBuilder replaceAttribute(String id, String value) {
        String newPath = snapshotAttributes.getSnapshotPath().toString().replaceAll("\\#\\{" + id + "}", value);
        snapshotAttributes.setSnapshotPath(Paths.get(newPath));
        return this;
    }

    @Override
    public SampleBuilder sample() {
        int similarity = this.snapshotAttributes.getSimilarity() > -1 ?
                this.snapshotAttributes.getSimilarity() : EyeSpy.config().getGlobalSimilarity();

        if(similarity<0 && similarity >100){
            throw new IllegalArgumentException("Similarity should be between 0 and 100. But the actual is " + similarity);
        }

        snapshotAttributes.setSimilarity(similarity);
        return new SampleBuilderImpl(this.snapshotAttributes);
    }

    private void getSnapAnnotation(Class<?> klass) {

        if (!klass.isAnnotationPresent(Snap.class))
            throw new EyeSpyException("Snap annotation is not present for type : " + klass.getName());

        Snap baseline = klass.getAnnotation(Snap.class);
        snapshotAttributes.setSimilarity(baseline.similarity());
        resolvePath(Paths.get(baseline.value()));
    }

    private void resolvePath(Path path){
        path = path.isAbsolute() ? path : EyeSpy.config().getSnapshotPath().resolve(path);
        this.snapshotAttributes.setSnapshotPath(path);
    }

}
