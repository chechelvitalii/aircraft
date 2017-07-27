package com.cv.aircraft;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class IOTestUtils {
    @SneakyThrows
    public String readFileAsString(String pathToFile) {
        return new String(Files.readAllBytes(Paths.get(IOTestUtils.class.getResource(pathToFile).toURI())));
    }
}
