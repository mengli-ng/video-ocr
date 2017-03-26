package xyz.dreamcoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreamcoder.model.ResourceInfo;
import xyz.dreamcoder.service.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/file/**")
    public ResponseEntity<InputStreamResource> readFile(HttpServletRequest request) throws IOException {

        String url = request.getRequestURL().toString();
        ResourceInfo resource = storageService.read(url);

        return ResponseEntity.ok()
                .contentLength(resource.getInputStream().available())
                .contentType(MediaType.parseMediaType(resource.getMimeType()))
                .body(new InputStreamResource(resource.getInputStream()));
    }
}