package com.vit.externalization;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Started processing the file.");
        File inFile = FileUtils.getFile("/home/deva/Downloads/ROAdd.jsp");
        File outFile = FileUtils.getFile("/home/deva/Downloads/ROAdd-mod.jsp");
        try {
            File tmpFile = Utilities.commentScriptletTags(inFile);

            // <spring:message code="farmer.login_text" text="Sign into the Depot Online System with Farmer Registration Number" />

            Document doc = Jsoup.parse(tmpFile, StandardCharsets.UTF_8.name());
            Elements elements = doc.select("label");
            elements.forEach(ele -> {
                String originalText = ele.text();
                System.out.println("Replace \"" + originalText + "\" with: ");
//                String userInput = in.next();
                String innerHtml = String.format("<spring:message  text=\"%s\" />",
//                        userInput,
                        originalText);
                ele.textNodes().forEach(Node::remove);
                ele.html(innerHtml);
                ele.html();
            });
            FileUtils.writeStringToFile(tmpFile, doc.outerHtml(), StandardCharsets.UTF_8.name());

            tmpFile = Utilities.uncommentScriptletTags(tmpFile);
            FileUtils.copyFile(tmpFile, outFile);

            System.out.println("Completed..!");
        } catch (IOException e) {
            System.out.println("Something went wrong..!");
            e.printStackTrace();
        }
    }
}
