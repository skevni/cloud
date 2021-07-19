package ru.gb.sklyarov.cloud.client.util.impl;

import ru.gb.sklyarov.cloud.client.util.PropertyUtil;

import java.io.IOException;
import java.io.InputStream;
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
        try (InputStream InputStream = PropertyUtilImpl.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(InputStream);
            properties.forEach((key, value) -> propertiesMap.put((String) key, (String) value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }
}
