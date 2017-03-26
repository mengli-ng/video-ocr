package xyz.dreamcoder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Video {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String filePath;
    private String fileUrl;
    private String thumbnailFilePath;
    private String thumbnailFileUrl;
    private String browseFilePath;
    private String browseFileUrl;
    private Date uploadTime;

    @Enumerated(EnumType.STRING)
    private VideoStatus status;

    @Lob
    private String videoInfo;

    @Transient
    private VideoInfo parsedVideoInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public String getThumbnailFileUrl() {
        return thumbnailFileUrl;
    }

    public void setThumbnailFileUrl(String thumbnailFileUrl) {
        this.thumbnailFileUrl = thumbnailFileUrl;
    }

    public String getBrowseFilePath() {
        return browseFilePath;
    }

    public void setBrowseFilePath(String browseFilePath) {
        this.browseFilePath = browseFilePath;
    }

    public String getBrowseFileUrl() {
        return browseFileUrl;
    }

    public void setBrowseFileUrl(String browseFileUrl) {
        this.browseFileUrl = browseFileUrl;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
        parsedVideoInfo = null;
    }

    @JsonIgnore
    public VideoInfo getParsedVideoInfo() {

        if (parsedVideoInfo != null) {
            return parsedVideoInfo;
        }

        if (Strings.isNullOrEmpty(videoInfo)) {
            parsedVideoInfo = null;
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            parsedVideoInfo = mapper.readValue(this.videoInfo, VideoInfo.class);
            return parsedVideoInfo;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}