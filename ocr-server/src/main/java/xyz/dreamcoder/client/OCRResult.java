package xyz.dreamcoder.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Meng Li
 */
public class OCRResult {

    public static class Result {

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_msg")
    private String errorMessage;

    @JsonProperty("words_result")
    private List<Result> wordsResult = new ArrayList<>();

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Result> getWordsResult() {
        return wordsResult;
    }

    public void setWordsResult(List<Result> wordsResult) {
        this.wordsResult = wordsResult;
    }
}