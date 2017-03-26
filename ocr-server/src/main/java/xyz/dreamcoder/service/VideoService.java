package xyz.dreamcoder.service;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import xyz.dreamcoder.config.TranscodeProperties;
import xyz.dreamcoder.model.Video;
import xyz.dreamcoder.model.VideoStatus;
import xyz.dreamcoder.repository.VideoRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VideoService {

    private static final String SCRIPT_INFO = "video_info.sh";
    private static final String SCRIPT_TRANSCODE = "video_transcode.sh";

    private final StorageService storageService;
    private final ScriptService scriptService;
    private final VideoRepository videoRepository;
    private final TranscodeProperties transcodeProperties;
    private final ExecutorService transcodeExecutor;

    public VideoService(StorageService storageService,
                        ScriptService scriptService,
                        VideoRepository videoRepository,
                        TranscodeProperties transcodeProperties) {

        this.storageService = storageService;
        this.scriptService = scriptService;
        this.videoRepository = videoRepository;
        this.transcodeProperties = transcodeProperties;

        transcodeExecutor = Executors.newFixedThreadPool(transcodeProperties.getThreads());
    }

    public void process(Video video) {

        transcodeExecutor.execute(() -> {
            updateStatus(video, VideoStatus.TRANSCODING);
            try {
                getVideoInfo(video);
                transcode(video);
                updateStatus(video, VideoStatus.READY);

            } catch (Throwable e) {
                e.printStackTrace();
                updateStatus(video, VideoStatus.TRANSCODE_FAILED);
            }
        });
    }

    private void getVideoInfo(Video video) {
        try {
            String inputPath = video.getFilePath();
            List<String> args = Arrays.asList("-i", inputPath);
            BufferedReader bufferedReader = scriptService.runAndWait(SCRIPT_INFO, args, true);

            video.setVideoInfo(CharStreams.toString(bufferedReader));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void transcode(Video video) {

        String inputPath = video.getFilePath();

        transcodeProperties.getFormats().forEach((name, format) -> {

            String outFileName = String.format("%s_%s.mp4", Files.getNameWithoutExtension(inputPath), name);
            String outputPath = Paths.get(Paths.get(inputPath).getParent().toString(), outFileName).toString();

            List<String> args = Arrays.asList(
                    "-i", inputPath,
                    "-o", outputPath,
                    "-f", format.getFormat(),
                    "-v", format.getVideoCodec(),
                    "-b", String.valueOf(format.getBitrate()));

            scriptService.runAndWait(SCRIPT_TRANSCODE, args, true);

            if (name.equals("browse")) {
                video.setBrowseFileUrl(outputPath);
                video.setBrowseFileUrl(storageService.getFileUrl(outputPath));
            }
        });
    }

    private void updateStatus(Video video, VideoStatus status) {
        video.setStatus(status);
        videoRepository.save(video);
    }
}