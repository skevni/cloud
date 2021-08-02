package ru.gb.sklyarov.cloud.client.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyConfig {
    private static final Properties properties = new Properties();
    private static final Map<String, String> propertiesMap = new HashMap<>();

    private static final Logger log = LogManager.getLogger(PropertyConfig.class);

    static {
        try (InputStream inputStream = PropertyConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
            properties.forEach((key, value) -> propertiesMap.put((String) key, (String) value));
        } catch (IOException ex) {
            log.error("Error in loading properties file", ex);
        }
    }

    public static String getServerHost(){
        return propertiesMap.getOrDefault(PropertyFields.SERVER_HOST.getField(),"localhost");
    }

    public static int getServerPort() {
        return Integer.parseInt(propertiesMap.getOrDefault(PropertyFields.SERVER_PORT.getField(),"9000"));
    }
}
