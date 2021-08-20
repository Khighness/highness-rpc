package top.parak.factory;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author KHighness
 * @since 2021-08-21
 */
@Slf4j
public class PropertiesFileUtils {

    private PropertiesFileUtils() {
    }

    public static Properties readProperties(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        if (url != null) {
            rpcConfigPath = url.getPath() + fileName;
        }
        Properties properties = null;
        try (InputStreamReader inputStreamReadr = new InputStreamReader(
                new FileInputStream(rpcConfigPath), StandardCharsets.UTF_8)) {
            properties = new Properties();
            properties.load(inputStreamReadr);
        } catch (IOException e) {
            log.error("occur exception when read properties file [{}]", fileName);
        }
        return properties;
    }
}
