package org.example.config;

import org.example.exeption.ResourseNotFound;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {

    public static final String SOLUTION_CONFIG = "application.yml";

    public Map<String, String> getConfigMap() {
        Properties properties;
        try {
            properties = loadProperties(SOLUTION_CONFIG);
        } catch (IOException e) {
            throw new ResourseNotFound("Resource file not found", e);
        }
        Map<String, String> map = new HashMap<>(4);
        map.put("driver", (String) properties.get("driver"));
        map.put("url", (String) properties.get("url"));
        map.put("username", (String) properties.get("username"));
        map.put("password", (String) properties.get("password"));
        return map;
    }

    private Properties loadProperties(String propertyFile) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(propertyFile);
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }
}
