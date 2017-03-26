package xyz.dreamcoder.model;

public class CreateTaskRequest {

    private long videoId;
    private float detectTop;
    private float detectLeft;
    private float detectWidth;
    private float detectHeight;
    private String language;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public float getDetectTop() {
        return detectTop;
    }

    public void setDetectTop(float detectTop) {
        this.detectTop = detectTop;
    }

    public float getDetectLeft() {
        return detectLeft;
    }

    public void setDetectLeft(float detectLeft) {
        this.detectLeft = detectLeft;
    }

    public float getDetectWidth() {
        return detectWidth;
    }

    public void setDetectWidth(float detectWidth) {
        this.detectWidth = detectWidth;
    }

    public float getDetectHeight() {
        return detectHeight;
    }

    public void setDetectHeight(float detectHeight) {
        this.detectHeight = detectHeight;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}