package xyz.dreamcoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("task")
public class TaskProperties {

    private String outputPath;
    private boolean baiduOcrAsync;
    private int ocrDelayMillis;

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public boolean isBaiduOcrAsync() {
        return baiduOcrAsync;
    }

    public void setBaiduOcrAsync(boolean baiduOcrAsync) {
        this.baiduOcrAsync = baiduOcrAsync;
    }

    public int getOcrDelayMillis() {
        return ocrDelayMillis;
    }

    public void setOcrDelayMillis(int ocrDelayMillis) {
        this.ocrDelayMillis = ocrDelayMillis;
    }
}