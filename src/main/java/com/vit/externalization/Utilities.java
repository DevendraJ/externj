package com.vit.externalization;

import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Utilities {
    static List<String> TAGS_WITH_TEXT = Arrays.asList("p", "span", "h1", "h2", "h3", "h4", "h5", "h6", "label", "input", "button", "a");

    public static File randomTmpFile() {
        String randFilePath = RandomStringUtils.randomAlphanumeric(10).concat(".jsp");
        return FileUtils.getFile(FileUtils.getTempDirectoryPath() + File.separator + randFilePath);
    }

    public static File commentScriptletTags(File inFile) throws IOException {
        File outFile = randomTmpFile();

        LineIterator iterator = FileUtils.lineIterator(inFile, StandardCharsets.UTF_8.name());
        while (iterator.hasNext()) {
            String line = iterator.next();
            line = line.replace("<%", "<!-- <%").replace("%>", "%> -->")
                    .replace("<jsp:include", "<!-- <jsp:include").replace("</jsp:include>", "</jsp:include> -->")
                    .concat("\n");

            FileUtils.writeStringToFile(outFile, line, StandardCharsets.UTF_8.name(), true);
        }

        return outFile;
    }

    public static File uncommentScriptletTags(File inFile) throws IOException {
        File outFile = randomTmpFile();

        LineIterator iterator = FileUtils.lineIterator(inFile, StandardCharsets.UTF_8.name());
        while (iterator.hasNext()) {
            String line = iterator.next();
            line = line.replace("<!-- <%", "<%").replace("%> -->", "%>")
                    .replace("<!-- <jsp:include", "<jsp:include").replace("</jsp:include> -->", "</jsp:include>")
                    .concat("\n");

            FileUtils.writeStringToFile(outFile, line, StandardCharsets.UTF_8.name(), true);
        }

        return outFile;
    }

    public static void writeToCSV(String csvFilePath, String[] lineContent) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true))) {
            writer.writeNext(lineContent);
        }
    }

}
