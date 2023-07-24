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
            StringBuilder stringBuilder = new StringBuilder();
            while ((c = fileReader.read()) != -1){
                stringBuilder.append(Character.toString(c));
            }
            var lines= stringBuilder.toString().split("\n");
            for(var line: lines) {
                var lineEntry = parseLine(line);
                setProfileField(profile, lineEntry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return profile;
    }

    private void setProfileField(Profile profile, Map.Entry<String, String> lineEntry) {
        switch (lineEntry.getKey()){
            case "Name" :{
                profile.setName(lineEntry.getValue());
                break;
            }
            case "Age":{
                profile.setAge(Integer.parseInt(lineEntry.getValue()));
                break;
            }
            case "Email":{
                profile.setEmail(lineEntry.getValue());
                break;
            }
            case "Phone":{
                profile.setPhone(Long.parseLong(lineEntry.getValue()));
                break;
            }
            default:{
            }
        }
    }

    private Map.Entry<String, String> parseLine(String line){
        var entry = line.split(": ");
        String key = entry[0];
        String value = entry[1];
        return Map.entry(key, value);
    }
}
