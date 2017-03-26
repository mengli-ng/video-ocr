package xyz.dreamcoder.service;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.dreamcoder.config.ScriptProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScriptService {

    private final ScriptProperties properties;

    @Autowired
    public ScriptService(ScriptProperties properties) {
        this.properties = properties;
    }

    public BufferedReader runAndWait(String scriptName, List<String> args, boolean catchError) {

        List<String> commands = new ArrayList<>();
        commands.add(Paths.get(properties.getBasePath(), scriptName).toString());
        commands.addAll(args);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            Process process = processBuilder.start();
            process.waitFor();

            if (catchError) {
                String error = CharStreams.toString(new InputStreamReader(process.getErrorStream(), "utf-8"));
                if (!Strings.isNullOrEmpty(error)) {
                    throw new IllegalStateException(error);
                }
            }

            return new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}