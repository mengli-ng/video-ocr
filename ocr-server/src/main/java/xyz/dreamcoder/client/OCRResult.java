package xyz.dreamcoder.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Meng Li
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OCRResult {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String err_no;
    private String err_msg;
    private String format;
    private String result;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Result {
        private String errno;
        private String errmas;
        private String querysign;
        private List<Ret> ret;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Ret {
        private String word;
    }

    public List<String> getWords() {

        if (Strings.isNullOrEmpty(result)) {
            return null;
        }

        try {
            Result result = OBJECT_MAPPER.readValue(Base64.getDecoder().decode(getResult()), Result.class);
            return result.getRet() == null ? Collections.emptyList() : result.getRet().stream().map(Ret::getWord).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}