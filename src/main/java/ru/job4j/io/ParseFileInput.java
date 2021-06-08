package ru.job4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ParseFileInput {
    public String getContent(File file) throws IOException {
        return getContent(file, t -> true);
    }

    public String getContentWithoutUnicode(File file) throws IOException {
        return getContent(file, t -> t < 0x80);
    }

    private String getContent(File file, Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
