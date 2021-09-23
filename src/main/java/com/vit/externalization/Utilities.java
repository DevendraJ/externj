package com.vit.externalization;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Utilities {
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
}
