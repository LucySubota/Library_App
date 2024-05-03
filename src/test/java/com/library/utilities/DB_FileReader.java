package com.library.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DB_FileReader {

    private DB_FileReader() {
    }

    private static final String filePath = "src/test/resources/files/DataBaseQueries";

    public static Map<String, String> getQueryAsMap(String key) {

        Map<String, String> keyValueMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] keyValue = line.split("=", 2); // Split each line by '=' into key and value
                if (keyValue.length == 2) {
                    String queryKey = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    keyValueMap.put(queryKey, value);
                }
            }
            return keyValueMap;
        } catch (IOException e) {
            throw new RuntimeException("QUERY WAS NOT READ FROM THE FILE");
        }
    }

    public static String getQuery(String key) {

        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] keyValue = line.split("=", 2); // Split each line by '=' into key and value
                if (keyValue.length == 2) {
                    return keyValue[1].trim();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("QUERY WAS NOT READ FROM THE FILE");
        }
        return null;
    }
}
