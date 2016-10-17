package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class CommandlineParameters {
    public String getDelimter() {
        return delimter;
    }

    @Parameter(names = {"-delimiter", "-d"}, echoInput = true)
    private String delimter;

    @Parameter(required = true, names = {"-storyFilePath", "-f"}, description = "Complete path to file")
    private String storyFilePath;

    @Parameter(required = true, names = {"-dataFormat", "-df"}, description = "Format of the data {jirajson, jiracsv}")
    private String dataFromat;
}
