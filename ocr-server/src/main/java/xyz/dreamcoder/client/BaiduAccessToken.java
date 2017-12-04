package xyz.dreamcoder.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Meng Li
 */
public class BaiduAccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}