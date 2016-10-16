package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class CommandlineParameters {

    @Parameter(names = {"-delimiter", "-d"}, echoInput = true)
    private String delimter;

    @Parameter(required = true, names = {"-storyFilePath", "-f"}, description = "Complete path to file")
    private String storyFilePath;

}
