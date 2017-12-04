package xyz.dreamcoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("task")
public class TaskProperties {

    private String outputPath;
    private String baiduClientId;
    private String baiduClientSecret;
    private boolean baiduOcrAsync;

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getBaiduClientId() {
        return baiduClientId;
    }

    public void setBaiduClientId(String baiduClientId) {
        this.baiduClientId = baiduClientId;
    }

    public String getBaiduClientSecret() {
        return baiduClientSecret;
    }

    public void setBaiduClientSecret(String baiduClientSecret) {
        this.baiduClientSecret = baiduClientSecret;
    }

    public boolean isBaiduOcrAsync() {
        return baiduOcrAsync;
    }

    public void setBaiduOcrAsync(boolean baiduOcrAsync) {
        this.baiduOcrAsync = baiduOcrAsync;
    }
}