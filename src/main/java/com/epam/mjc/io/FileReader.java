package com.epam.mjc.io;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class FileReader {

    public static final String PROFILE_PARAM_VALUE_DELIMETER = ": ";
    public static final String SOURCE_CODE_LOCATION = "src/main/java";

    //This method will be invoked for testing your code
    public Profile getDataFromFile(File file) {
        try (InputStream fis = new FileInputStream(file)) {
            int c;
            StringBuilder str = new StringBuilder();
            while ((c = fis.read()) != -1) {
                str.append((char) c);
            }

            String data = str.toString();
            String[] splitData = data.split("\\n");
            Profile profile = new Profile();
            Arrays.stream(splitData).forEach(string -> setValue(profile, string));
            return profile;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValue(Profile profile, String line) {
        Pair<String, String> keyValue = parseIntoParamValue(line);
        String paramName = keyValue.getLeft();
        String paramValue = keyValue.getRight();
        if (paramName.equalsIgnoreCase("name")) {
            profile.setName(paramValue);
        } else if (paramName.equalsIgnoreCase("age")) {
            profile.setAge(Integer.parseInt(paramValue));
        } else if (paramName.equalsIgnoreCase("email")) {
            profile.setEmail(paramValue);
        } else if (paramName.equalsIgnoreCase("phone")) {
            profile.setPhone(Long.parseLong(paramValue));
        }
    }

    private Pair<String, String> parseIntoParamValue(String line) {
        List<String> parsedString = Arrays.stream(line.split(PROFILE_PARAM_VALUE_DELIMETER)).collect(Collectors.toList());
        return Pair.of(parsedString.get(0), parsedString.get(1));
    }
}
