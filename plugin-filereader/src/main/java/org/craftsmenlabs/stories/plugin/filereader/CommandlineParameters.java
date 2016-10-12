package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.Parameter;

public class CommandlineParameters {
    public String getDelimter() {
        return delimter;
    }

    @Parameter(names = {"-delimiter", "-d"}, echoInput = true)
    private String delimter;

    public String getStoryFilePath() {
        return storyFilePath;
    }

    @Parameter(required = true, names = {"-storyFilePath", "-f"}, description = "Complete path to file")
    private String storyFilePath;
}
