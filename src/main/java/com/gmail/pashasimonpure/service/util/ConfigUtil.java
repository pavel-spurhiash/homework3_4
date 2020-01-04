package com.gmail.pashasimonpure.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigUtil {

    private static final Logger logger = LogManager.getLogger(ConfigUtil.class);
    private Properties properties;
    private String configPath;
    private boolean loaded = false;

    public ConfigUtil(String configPath) {
        this.properties = new Properties();
        this.configPath = configPath;
    }

    private void readConfig() {
        try (InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream(this.configPath)) {
            this.properties.load(propertiesStream);
            this.loaded = true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("config read error: bad config path");
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("properties not found"); //test null pointer
        }
    }

    public String getProperty(String name) {

        if (this.loaded == false) {
            readConfig();
        }

        return this.properties.getProperty(name);
    }

}