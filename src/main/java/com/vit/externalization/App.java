package com.vit.externalization;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.vit.externalization.Utilities.TAGS_WITH_TEXT;

public class App {
    public static void main(String[] args) {
        System.out.println("Started processing the file.");
        String userHome = System.getProperty("user.home");
        File inFile = FileUtils.getFile(userHome + "/Downloads/trash/garb/ROAdd.jsp");
        File plainStrings = FileUtils.getFile(userHome + "/Downloads/trash/garb/plainStrings.csv");

        try {
            Document doc = Jsoup.parse(inFile, StandardCharsets.UTF_8.name());
            Elements elements = doc.select(String.join(",", TAGS_WITH_TEXT));
            for (Element ele : elements) {
                String originalText = (ele.tagName().equals("input") && ele.attr("type").equals("submit")) ?
                        ele.attr("value") :
                        ele.text();

                if (!originalText.equals("")) {
                    Utilities.writeToCSV(plainStrings.getPath(), new String[]{inFile.getPath(), ele.tagName(), originalText});
                }
            }

            System.out.println("\nCompleted..!");
        } catch (IOException e) {
            System.out.println("\nSomething went wrong..!");
            e.printStackTrace();
        }
    }
}
