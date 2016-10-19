package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class CommandlineParameters {
    public String getDelimter() {
        return delimter;
    }

    @Parameter(required = true, names = {"-storyFilePath", "-f"}, description = "Complete path to file")
    private String storyFilePath;

    @Parameter(required = true, names = {"-dataFormat", "-df"}, description = "Format of the data {jirajson, jiracsv}")
    private String dataFromat;

    @Parameter(required = false, names = {"-delimiter", "-d"}, echoInput = true)
    private String delimter;

    @Parameter(required = false, names = {"-url"}, description = "URL of the rest-api")
    private String url;

    @Parameter(required = false, names = {"-projectKey", "-pk"}, description = "The jira-projectkey")
    private String projectKey;

    @Parameter(required = false, names = {"-authKey", "-a"}, description = "authKey for the rest authentication")
    private String authKey;

    @Parameter(required = false, names = {"-outputFile"}, description = "output file")
    private String outputFile;
}
