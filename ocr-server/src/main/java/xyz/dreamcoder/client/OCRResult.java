package xyz.dreamcoder.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Meng Li
 */
@Data
public class OCRResult {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String err_no;
    private String err_msg;
    private String format;
    private String result;

    @Data
    static class Result {
        private String errno;
        private String errmas;
        private String querysign;
        private Ret ret;
    }

    @Data
    static class Ret {
        private String word;
    }

    public String getWord() {

        if (Strings.isNullOrEmpty(result)) {
            return null;
        }

        try {
            Result result = OBJECT_MAPPER.readValue(getResult(), Result.class);
            return result.getRet() == null ? null : result.getRet().getWord();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}