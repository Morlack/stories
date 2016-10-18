package org.craftsmenlabs.stories.spike.importer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class FileImporter implements Importer{
    String filename;

    public FileImporter(String filename) {
        this.filename = filename;
    }

    public String getDataAsString() {
        String input = null;
        try {
            input = FileUtils.readFileToString(new File(filename), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> lines = Arrays.asList(input.split("\n"));

        //drop header and footer
        input = String.join("\n", lines.subList(1, lines.size() - 1));

        return input;
    }


}
