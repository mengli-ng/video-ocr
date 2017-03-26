package xyz.dreamcoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties("transcode")
public class TranscodeProperties {

    public static class TranscodeFormat {

        private String format;
        private String videoCodec;
        private int bitrate;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getVideoCodec() {
            return videoCodec;
        }

        public void setVideoCodec(String videoCodec) {
            this.videoCodec = videoCodec;
        }

        public int getBitrate() {
            return bitrate;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }
    }

    private Map<String, TranscodeFormat> formats = new HashMap<>();
    private int threads = 1;

    public Map<String, TranscodeFormat> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, TranscodeFormat> formats) {
        this.formats = formats;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }
}