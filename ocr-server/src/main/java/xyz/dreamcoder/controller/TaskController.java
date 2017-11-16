package xyz.dreamcoder.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.dreamcoder.minicommons.jpa.specification.SpecificationBuilder;
import xyz.dreamcoder.model.*;
import xyz.dreamcoder.repository.TaskRepository;
import xyz.dreamcoder.repository.TaskResultRepository;
import xyz.dreamcoder.repository.VideoRepository;
import xyz.dreamcoder.service.TaskService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import static xyz.dreamcoder.minicommons.jpa.specification.SpecificationBuilder.like;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final VideoRepository videoRepository;
    private final TaskResultRepository taskResultRepository;

    @Autowired
    public TaskController(TaskService taskService,
                          TaskRepository taskRepository,
                          VideoRepository videoRepository,
                          TaskResultRepository taskResultRepository) {

        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.videoRepository = videoRepository;
        this.taskResultRepository = taskResultRepository;
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody CreateTaskRequest request) {

        Video video = videoRepository.findOne(request.getVideoId());

        Task task = new Task();
        task.setVideoId(video.getId());
        task.setVideoName(video.getName());
        task.setDetectLeft(request.getDetectLeft());
        task.setDetectTop(request.getDetectTop());
        task.setDetectWidth(request.getDetectWidth());
        task.setDetectHeight(request.getDetectHeight());
        task.setLanguage(request.getLanguage());
        task.setStatus(TaskStatus.NEW);
        task.setCreatedTime(new Date());

        taskRepository.save(task);
        taskService.execute(video, task);

        return task;
    }

    @GetMapping("/tasks")
    public Page<Task> getTasks(
            String videoName,
            @PageableDefault(
                    sort = "createdTime",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        SpecificationBuilder<Task> builder = new SpecificationBuilder<>();

        if (!Strings.isNullOrEmpty(videoName)) {
            builder = builder.and("videoName", like(videoName));
        }

        return taskRepository.findAll(builder.build(), pageable);
    }

    @GetMapping("/tasks/{taskId}")
    public Task getTask(@PathVariable long taskId) {
        return taskRepository.findOne(taskId);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        taskRepository.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tasks/saveResult/{resultId}")
    public TaskResult saveText(@PathVariable long resultId, String text) {

        TaskResult result = taskResultRepository.findOne(resultId);
        result.setText(text);

        taskResultRepository.save(result);
        return result;
    }

    @PostMapping("/tasks/{taskId}/addResult")
    public TaskResult addResult(@PathVariable long taskId, int position, String text) {

        Task task = taskRepository.findOne(taskId);
        TaskResult result = new TaskResult();
        result.setPosition(position);
        result.setText(text);

        task.getResults().add(result);
        taskRepository.save(task);

        return result;
    }

    @DeleteMapping("/tasks/deleteResult/{resultId}")
    public void deleteResult(@PathVariable long resultId) {
        taskResultRepository.delete(resultId);
    }

    @GetMapping("/tasks/{taskId}/export")
    public ResponseEntity<byte[]> export(@PathVariable long taskId) throws UnsupportedEncodingException {

        Task task = taskRepository.findOne(taskId);
        String fileName = URLEncoder.encode(task.getVideoName().replace('.', '_') + ".txt", "UTF-8");

        StringBuilder builder = new StringBuilder();
        task.getResults().forEach(result -> {
            builder.append(String.format("%s: %s\r\n", result.getPositionText(), result.getText()));
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("Content-Disposition", "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(builder.toString().getBytes("utf-8"));
    }
}