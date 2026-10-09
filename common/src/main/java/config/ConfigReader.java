package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/** По умолчанию читаем общий config.json; параметры запуска имеют приоритет. */
public class ConfigReader {
    private static final TestConfig CONFIG = read();

    public static TestConfig load() { return CONFIG; }

    private static TestConfig read() {
        String file = System.getProperty("config.file");
        try (InputStream stream = file == null
                ? ConfigReader.class.getClassLoader().getResourceAsStream("config.json")
                : Files.newInputStream(Path.of(file))) {
            if (stream == null) throw new IllegalStateException("Не найден config.json");
            TestConfig config = new ObjectMapper().readValue(stream, TestConfig.class);
            config.setApiUrl(System.getProperty("apiUrl", config.getApiUrl()));
            config.setWebUrl(System.getProperty("webUrl", config.getWebUrl()));
            config.setBrowser(System.getProperty("browser", config.getBrowser()));
            config.setHeadless(Boolean.parseBoolean(System.getProperty("headless", Boolean.toString(config.getHeadless()))));
            config.setTimeout(Long.parseLong(System.getProperty("timeout", Long.toString(config.getTimeout()))));
            if (config.getTimeout() <= 0 || config.getListSize() <= 0) {
                throw new IllegalStateException("timeout и listSize должны быть положительными");
            }
            return config;
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать конфиг", e);
        }
    }
}
