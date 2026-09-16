package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigReader {

    private static final String CONFIG_FILE = "config.json";

    public static TestConfig load() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(CONFIG_FILE)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Файл конфигурации не найден: " + CONFIG_FILE
                );
            }

            return objectMapper.readValue(inputStream, TestConfig.class);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Ошибка чтения конфигурации",
                    e
            );
        }
    }
}