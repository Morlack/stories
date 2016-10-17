package org.craftsmenlabs.stories.spike.isolator.parser;

import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.Issue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public abstract class FileParser implements Parser{

    public List<Issue> getIssues(File file){
        List<Issue> issues = null;

        try {
            issues = getIssues(readFileAsString(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return issues;
    }

    public static String readFileAsString(File file) throws IOException {
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    public static String readFileAsString(String filename) throws IOException {
        File file = new File(filename);

        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }
}
