package xyz.dreamcoder.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.dreamcoder.model.CreateVideoRequest;
import xyz.dreamcoder.model.Video;
import xyz.dreamcoder.model.VideoStatus;
import xyz.dreamcoder.repository.VideoRepository;
import xyz.dreamcoder.service.StorageService;
import xyz.dreamcoder.service.VideoService;
import xyz.fastcode.commons.jpa.specification.SpecificationBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static xyz.fastcode.commons.jpa.specification.SpecificationBuilder.equalTo;
import static xyz.fastcode.commons.jpa.specification.SpecificationBuilder.like;

@RestController
@RequestMapping
public class VideoController {

    private final StorageService storageService;
    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(StorageService storageService,
                           VideoService videoService,
                           VideoRepository videoRepository) {

        this.storageService = storageService;
        this.videoService = videoService;
        this.videoRepository = videoRepository;
    }

    @GetMapping("/videos")
    public Page<Video> getVideos(
            String name,
            @PageableDefault(
                    sort = "uploadTime",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        SpecificationBuilder<Video> builder = new SpecificationBuilder<>();

        if (!Strings.isNullOrEmpty(name)) {
            builder = builder.and("name", like(name));
        }

        return videoRepository.findAll(builder.build(), pageable);
    }

    @PostMapping("/videos")
    public List<Video> createVideos(@RequestBody List<CreateVideoRequest> requests) {

        List<Video> videos = requests.stream().map(request -> {

            Video video = videoRepository.findByName(request.getFileName());

            if (video == null) {
                video = new Video();
                video.setName(request.getFileName());
            }

            video.setFilePath(storageService.getFilePath(request.getFileName(), request.getFileIdentifier()));
            video.setFileUrl(storageService.getFileUrl(video.getFilePath()));
            video.setUploadTime(new Date());
            video.setStatus(VideoStatus.NEW);
            videoRepository.save(video);

            return video;

        }).collect(Collectors.toList());

        videos.forEach(videoService::process);
        return videos;
    }

    @GetMapping("/videos/{videoId}")
    public Video getVideo(@PathVariable long videoId) {
        return videoRepository.findOne(videoId);
    }

    @DeleteMapping("/videos/{videoId}")
    public ResponseEntity<?> deleteVideo(@PathVariable long videoId) {

        Video video = videoRepository.findOne(videoId);

        videoService.deleteVideo(video);
        videoRepository.delete(video);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/videos/exists")
    public boolean videoExists(String name) {
        return this.videoRepository.findByName(name) != null;
    }

    @PostMapping("/videos/upload")
    public ResponseEntity<?> upload(MultipartFile file,
                                    @RequestParam("resumableChunkNumber") int chunkNumber,
                                    @RequestParam("resumableChunkSize") long chunkSize,
                                    @RequestParam("resumableTotalSize") long totalSize,
                                    @RequestParam("resumableIdentifier") String identifier,
                                    @RequestParam("resumableFilename") String fileName) throws IOException {

        storageService.upload(file, fileName, identifier, chunkNumber - 1, chunkSize, totalSize);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/videos/ready")
    public List<Video> getReadyVideos(
            String name,
            @SortDefault(
                    sort = "uploadTime",
                    direction = Sort.Direction.DESC) Sort sort) {

        SpecificationBuilder<Video> builder = new SpecificationBuilder<>();
        builder = builder.and("status", equalTo(VideoStatus.READY));

        if (!Strings.isNullOrEmpty(name)) {
            builder = builder.and("name", like(name));
        }

        return videoRepository.findAll(builder.build(), new PageRequest(0, 10, sort)).getContent();
    }
}