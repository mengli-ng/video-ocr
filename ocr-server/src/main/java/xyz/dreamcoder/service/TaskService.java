package xyz.dreamcoder.service;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.dreamcoder.config.TaskProperties;
import xyz.dreamcoder.model.*;
import xyz.dreamcoder.repository.TaskRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class TaskService {

    private static final String SCRIPT_VIDEO_OCR = "video_ocr.sh";

    private final ScriptService scriptService;
    private final TaskProperties properties;
    private final TaskRepository taskRepository;

    public TaskService(ScriptService scriptService, TaskProperties properties, TaskRepository taskRepository) {
        this.scriptService = scriptService;
        this.properties = properties;
        this.taskRepository = taskRepository;
    }

    @Async
    public void execute(Video video, Task task) {

        updateStatus(task, TaskStatus.EXECUTING);
        try {
            String outputPath = createOutputPath();

            executeOCR(video, task, outputPath);
            parseTaskResults(task, outputPath);
            updateStatus(task, TaskStatus.FINISHED);

        } catch (Throwable e) {
            updateStatus(task, TaskStatus.FAILED);
            throw new RuntimeException(e);
        }
    }

    private String createOutputPath() {

        String folderName = UUID.randomUUID().toString();
        String outputPath = Paths.get(properties.getOutputPath(), folderName).toString();

        try {
            Files.createDirectories(Paths.get(outputPath));
            return outputPath;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeOCR(Video video, Task task, String outputPath) {

        VideoInfo videoInfo = video.getParsedVideoInfo();

        List<String> commands = Arrays.asList(
                "-i", video.getFilePath(),
                "-o", outputPath,
                "-x", String.valueOf(task.getDetectLeft() * videoInfo.getVideoWidth()),
                "-y", String.valueOf(task.getDetectTop() * videoInfo.getVideoHeight()),
                "-w", String.valueOf(task.getDetectWidth() * videoInfo.getVideoWidth()),
                "-h", String.valueOf(task.getDetectHeight() * videoInfo.getVideoHeight()),
                "-l", task.getLanguage(),
                "-t", String.valueOf(properties.getThreads())
        );

        scriptService.runAndWait(SCRIPT_VIDEO_OCR, commands, true);
    }

    private void parseTaskResults(Task task, String outputPath) {

        File[] txtFiles = new File(outputPath).listFiles((dir, name) -> name.endsWith(".txt"));

        if (txtFiles == null || txtFiles.length == 0) {
            throw new IllegalStateException("can't find any result files.");
        }

        final String[] lastText = {""};
        Stream.of(txtFiles).sorted(Comparator.comparing(File::getName)).forEach(txtFile -> {
            try {
                String fileName = com.google.common.io.Files.getNameWithoutExtension(txtFile.getName());
                int position = (Integer.parseInt(fileName) - 1) * 1000; // milliseconds
                String text = FileUtils.readFileToString(txtFile, "utf-8")
                        .replace("\r\n", "")
                        .replace("\n", "")
                        .trim();

                if (!Strings.isNullOrEmpty(text)) {
                    if (!text.equals(lastText[0])) {
                        TaskResult result = new TaskResult();
                        result.setPosition(position);
                        result.setText(text);
                        task.getResults().add(result);
                    } else {
                        task.getResults().get(task.getResults().size() - 1).setPosition(position);
                    }
                    lastText[0] = text;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateStatus(Task task, TaskStatus status) {
        task.setStatus(status);
        taskRepository.save(task);
    }
}