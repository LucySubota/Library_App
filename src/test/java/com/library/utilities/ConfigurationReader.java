package com.library.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private ConfigurationReader(){}

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("src/configuration.properties");
            properties.load(file);
            file.close();
        }catch (IOException e){
            System.out.println("FILE WITH GIVEN PATH NOT FOUND");
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

}
