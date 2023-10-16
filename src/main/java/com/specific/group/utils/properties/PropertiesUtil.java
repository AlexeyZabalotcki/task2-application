package com.specific.group.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.specific.group.constants.Constants.Database.PATH_FILL;

/**
 * Util class for find and get properties from application.properties file.
 */
public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();


    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PATH_FILL)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
