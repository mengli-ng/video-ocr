package xyz.dreamcoder.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;
    private long videoId;
    private String videoName;

    private float detectTop;
    private float detectLeft;
    private float detectWidth;
    private float detectHeight;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Date createdTime;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("position ASC")
    @JoinColumn(name = "task_id")
    private List<TaskResult> results = new ArrayList<>();

    private String language;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public List<TaskResult> getResults() {
        return results;
    }

    public void setResults(List<TaskResult> results) {
        this.results = results;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}