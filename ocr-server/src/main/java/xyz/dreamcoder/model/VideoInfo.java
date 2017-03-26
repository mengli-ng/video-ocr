package xyz.dreamcoder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoInfo {

    private VideoFormatInfo format;
    private List<VideoStreamInfo> streams;

    public VideoFormatInfo getFormat() {
        return format;
    }

    public void setFormat(VideoFormatInfo format) {
        this.format = format;
    }

    public List<VideoStreamInfo> getStreams() {
        return streams;
    }

    public void setStreams(List<VideoStreamInfo> streams) {
        this.streams = streams;
    }

    public Integer getVideoWidth() {

        VideoStreamInfo video = findVideoStream();
        return video == null ? null : video.getWidth();
    }

    public Integer getVideoHeight() {

        VideoStreamInfo video = findVideoStream();
        return video == null ? null : video.getHeight();
    }

    public Long getVideoDuration() {
        VideoStreamInfo video = findVideoStream();
        return video == null ? null : video.getDuration() == null ? null : Long.parseLong(video.getDuration());
    }

    private VideoStreamInfo findVideoStream() {

        Optional<VideoStreamInfo> first = streams.stream()
                .filter(x -> "video".equals(x.getCodec_type()))
                .findFirst();

        return first.isPresent() ? first.get() : null;
    }
}