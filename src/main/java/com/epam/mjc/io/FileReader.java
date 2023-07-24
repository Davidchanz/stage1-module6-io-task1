package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        try(FileInputStream fileReader = new FileInputStream(file)) {
            int c;
            StringBuilder fileText = new StringBuilder();
            while ((c = fileReader.read()) != -1){
                fileText.append(Character.toString(c));
            }
            var lines= fileText.toString().split("\n");
            for(var line: lines) {
                var lineEntry = parseLine(line);
                setProfileField(profile, lineEntry);
            }
        } catch (IOException e) {
            System.err.println("File IO Error!");
            throw new RuntimeException(e);
        }
        return profile;
    }

    private void setProfileField(Profile profile, Map.Entry<String, Object> lineEntry) {
        try {
            var field = profile.getClass().getDeclaredField(lineEntry.getKey().toLowerCase());
            field.setAccessible(true);
            field.set(profile, lineEntry.getValue());
            field.setAccessible(false);
        } catch (NoSuchFieldException e) {
            System.err.println("Error! No field " + lineEntry.getKey());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.err.println("Error! Can't set field " + lineEntry.getKey());
            throw new RuntimeException(e);
        }
    }

    private Map.Entry<String, Object> parseLine(String line) throws IllegalArgumentException{
        var entry = line.split(": ");
        String key = entry[0];
        String rawValue = entry[1];
        Object value = rawValue;
        switch (key){
            case "Name":
            case "Email": {
                break;
            }
            case "Age":{
                value = Integer.parseInt(rawValue);
                break;
            }
            case "Phone":{
                value = Long.parseLong(rawValue);
                break;
            }
        }
        return Map.entry(key, value);
    }
}
