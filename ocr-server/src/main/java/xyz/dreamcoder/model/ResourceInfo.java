package xyz.dreamcoder.model;

import java.io.InputStream;

public class ResourceInfo {

    private final InputStream inputStream;
    private final String mimeType;

    public ResourceInfo(InputStream inputStream, String mimeType) {
        this.inputStream = inputStream;
        this.mimeType = mimeType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getMimeType() {
        return mimeType;
    }
}