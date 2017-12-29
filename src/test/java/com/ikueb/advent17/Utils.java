package com.ikueb.advent17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

final class Utils {

    private Utils() {
        // empty
    }

    static List<String> getInput(Class clazz) {
        try {
            return Files.readAllLines(Paths.get("build", "resources", "test",
                    clazz.getSimpleName() + ".txt"));
        } catch (IOException e) {
            throw new UnexpectedException("number of lines but got: " + e.getMessage());
        }
    }

    static String getSingleInput(Class clazz) {
        try {
            return Files.readAllLines(Paths.get("build", "resources", "test",
                    clazz.getSimpleName() + ".txt")).iterator().next();
        } catch (IOException e) {
            throw new UnexpectedException("line but got: " + e.getMessage());
        }
    }
}
