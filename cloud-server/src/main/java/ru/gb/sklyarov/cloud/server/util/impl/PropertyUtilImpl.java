package ru.gb.sklyarov.cloud.server.util.impl;

import ru.gb.sklyarov.cloud.server.util.PropertyUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtilImpl implements PropertyUtil {
    private final Properties properties;
    private final Map<String, String> propertiesMap;

    public PropertyUtilImpl() {
        this.properties = new Properties();
        this.propertiesMap = new HashMap<>();
    }

    public Map<String, String> getAllProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(Paths.get(".").toAbsolutePath() + "config.properties")) {
            properties.load(fileInputStream);
            properties.forEach((key, value) -> propertiesMap.put((String) key, (String) value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }
}
