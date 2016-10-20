package org.craftsmenlabs.stories.plugin.filereader;

import org.aeonbits.owner.Config;

public interface ApplicationConfig_old extends Config {
    @Key("storyFilePath")
    String storyFilePath();

    @Key("dataFormat")
    @DefaultValue("jirajson")
    String dataFromat();

    @Key("url")
    String url();

    @Key("projectKey")
    String projectKey();

    @Key("authKey")
    String authKey();

    @Key("outputFile")
    String outputFile();

    @Key("oooooooooooo")
    String oooooooooooo();
}
