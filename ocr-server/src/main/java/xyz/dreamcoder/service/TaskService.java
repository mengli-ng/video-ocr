package xyz.dreamcoder.service;

import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.dreamcoder.client.BaiduAccessToken;
import xyz.dreamcoder.client.BaiduBCEClient;
import xyz.dreamcoder.client.OCRResult;
import xyz.dreamcoder.config.TaskProperties;
import xyz.dreamcoder.model.*;
import xyz.dreamcoder.repository.TaskRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskService {

    private static final String SCRIPT_VIDEO_TO_IMAGES = "video_to_images.sh";
    private static final String REQUEST_URL = "type=st_ocrapi&appid=20005&detecttype=LocateRecognize&languagetype=CHN_ENG&imgDirection=setImgDirFlag&object_type=webimage_v1";

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final ScriptService scriptService;
    private final TaskProperties properties;
    private final TaskRepository taskRepository;
    private final BaiduBCEClient baiduBCEClient;

    public TaskService(ScriptService scriptService, TaskProperties properties, TaskRepository taskRepository,
                       BaiduBCEClient baiduBCEClient) {

        this.scriptService = scriptService;
        this.properties = properties;
        this.taskRepository = taskRepository;
        this.baiduBCEClient = baiduBCEClient;
    }

    @Async
    public void execute(Video video, Task task) {

        updateStatus(task, TaskStatus.EXECUTING);
        try {
            String outputPath = createOutputPath();

            videoToImages(video, task, outputPath);
            executeBaiduOCR(outputPath);
            parseTaskResults(task, outputPath);
            updateStatus(task, TaskStatus.FINISHED);

            LOGGER.info("Task executed finished: {}", task.getVideoName());

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

    private void videoToImages(Video video, Task task, String outputPath) {

        VideoInfo videoInfo = video.getParsedVideoInfo();

        List<String> commands = Arrays.asList(
                "-i", video.getFilePath(),
                "-o", outputPath,
                "-x", String.valueOf(task.getDetectLeft() * videoInfo.getVideoWidth()),
                "-y", String.valueOf(task.getDetectTop() * videoInfo.getVideoHeight()),
                "-w", String.valueOf(task.getDetectWidth() * videoInfo.getVideoWidth()),
                "-h", String.valueOf(task.getDetectHeight() * videoInfo.getVideoHeight())
        );

        scriptService.runAndWait(SCRIPT_VIDEO_TO_IMAGES, commands, true);
    }

    private void executeBaiduOCR(String outputPath) {
        try {
            Files.list(Paths.get(outputPath))
                    .filter(path -> path.toString().endsWith(".jpg"))
                    .forEach(image -> {
                        try {
                            Map<String, String> parameters = new HashMap<>();

                            String imageBase64 = Base64.getEncoder().encodeToString(Files.readAllBytes(image));
                            String requestUrl = REQUEST_URL + "&image=" + imageBase64;

                            parameters.put("data", Base64.getEncoder().encodeToString(requestUrl.getBytes()));

                            if (properties.isBaiduOcrAsync()) {
                                baiduBCEClient.accurateBasicOCR(parameters).observe()
                                        .subscribe(result -> {
                                            parseResult(image, result);

                                        }, Throwable::printStackTrace);
                            } else {
                                OCRResult result = baiduBCEClient.accurateBasicOCR(parameters).execute();
                                parseResult(image, result);
                            }

                            // wait a moment to send next request.
                            Thread.sleep(properties.getOcrDelayMillis());

                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void parseResult(Path image, OCRResult result) {

        if (!Strings.isNullOrEmpty(result.getErr_msg())) {
            throw new IllegalStateException(result.getErr_msg());
        }

        String words = result.getWords().stream().collect(Collectors.joining(" "));
        LOGGER.info("Result text: {}", words);

        Path resultPath = Paths.get(image.getParent().toString(),
                com.google.common.io.Files.getNameWithoutExtension(String.valueOf(image)) + ".txt");
        try {
            Files.write(resultPath, words.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public static void main(String[] args) throws IOException {
        String imageBase64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get("/Users/mengli/00001.jpg")));
        String data1 = REQUEST_URL + "&image=" + imageBase64;
        String data = Base64.getEncoder().encodeToString(data1.getBytes());

        System.out.println(data);
    }
}