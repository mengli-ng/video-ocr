package xyz.dreamcoder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Video Not Found")
public class VideoNotFoundException extends RuntimeException {
}