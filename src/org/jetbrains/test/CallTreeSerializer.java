package org.jetbrains.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public final class CallTreeSerializer {
    public static final String DEFAULT_ENCODING = "UTF-8";

    public static void writeToFile(CallTree callTree, String fileName) throws IOException {
        try (Writer writer = createWriter(fileName)) {
            Gson gson = buildGson();
            gson.toJson(callTree, writer);
        }
    }

    public static CallTree readFromFile(String fileName) throws IOException {
        Gson gson = buildGson();

        try (Reader reader = createReader(fileName)) {
            return gson.fromJson(reader, CallTree.class);
        }
    }

    private static Gson buildGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    private static BufferedReader createReader(String fileName)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING));
    }

    private static BufferedWriter createWriter(String fileName)
            throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), DEFAULT_ENCODING));
    }
}
