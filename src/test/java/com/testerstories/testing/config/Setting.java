package com.testerstories.testing.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class Setting {
    private final Properties settingProp = new Properties();

    private Setting() {
        InputStream domains = this.getClass().getClassLoader().getResourceAsStream("domain.properties");
        InputStream logins = this.getClass().getClassLoader().getResourceAsStream("login.properties");

        try {
            settingProp.load(domains);
            settingProp.load(logins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String useSetting(String key) {
        Setting setting = getInstance();
        return setting.getProperty(key);
    }

    private static Setting getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final Setting INSTANCE = new Setting();
    }

    private String getProperty(String key) {
        return settingProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return settingProp.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return settingProp.containsKey(key);
    }
}
