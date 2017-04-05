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

    public BufferedReader runAndWait(String scriptName, List<String> args, boolean showOutput) {

        List<String> commands = new ArrayList<>();
        commands.add(Paths.get(properties.getBasePath(), scriptName).toString());
        commands.addAll(args);

        try {
            System.out.println(String.join(" ", commands));

            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            if (showOutput) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            return new BufferedReader(new InputStreamReader(process.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}