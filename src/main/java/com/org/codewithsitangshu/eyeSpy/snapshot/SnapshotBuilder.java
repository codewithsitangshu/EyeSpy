package com.org.codewithsitangshu.eyeSpy.snapshot;

import com.org.codewithsitangshu.eyeSpy.sample.SampleBuilder;

import java.nio.file.Path;

public interface SnapshotBuilder {

    public <T> SnapshotBuilder from(Class<T> pageClass);
    public SnapshotBuilder from(Object object);
    public SnapshotBuilder from(Path path);
    public SnapshotBuilder replaceAttribute(String id, String value);
    public SampleBuilder sample();

}
