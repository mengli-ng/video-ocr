package xyz.dreamcoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.dreamcoder.config.StorageProperties;
import xyz.dreamcoder.model.ResourceInfo;
import xyz.dreamcoder.util.MimeTypeUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private final StorageProperties properties;

    @Autowired
    public StorageService(StorageProperties properties) {
        this.properties = properties;
    }

    public void upload(MultipartFile file, String fileName, String identifier, int chunkNumber, long chunkSize, long totalSize) {
        try {
            String filePath = getFilePath(fileName, identifier);
            boolean newCreated = !Files.exists(Paths.get(filePath));

            try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
                if (newCreated) {
                    randomAccessFile.setLength(totalSize);
                }
                FileChannel mergeFileChannel = randomAccessFile.getChannel();
                ReadableByteChannel inputChannel = Channels.newChannel(file.getInputStream());

                mergeFileChannel.transferFrom(inputChannel, (long) chunkNumber * chunkSize, file.getSize());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilePath(String fileName, String identifier) {

        String extension = com.google.common.io.Files.getFileExtension(fileName);

        if (!StringUtils.isEmpty(extension)) {
            extension = "." + extension;
        }

        return Paths.get(properties.getBasePath(), identifier + extension).toString();
    }

    public String getFileUrl(String filePath) {

        String basePath = properties.getBasePath();
        String fileRelativePath = filePath.substring(basePath.length());

        return properties.getBaseUrl() + fileRelativePath;
    }

    public ResourceInfo read(String url) {
        try {
            String basePath = properties.getBasePath();
            String relativePath = getRelativeUrl(url);

            Path path = Paths.get(basePath, relativePath);
            String mimeType = MimeTypeUtils.getMimeType(path.toFile());

            return Files.exists(path)
                    ? new ResourceInfo(Files.newInputStream(path), mimeType)
                    : null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRelativeUrl(String url) throws MalformedURLException {

        String baseUrl = properties.getBaseUrl();
        String urlPath = new URL(url).getPath();
        String baseUrlPath = new URL(baseUrl).getPath();

        if (!urlPath.startsWith(baseUrlPath)) {
            throw new IllegalStateException(String.format("'%s' does not start with '%s'", url, baseUrl));
        }

        return urlPath.substring(baseUrlPath.length());
    }
}