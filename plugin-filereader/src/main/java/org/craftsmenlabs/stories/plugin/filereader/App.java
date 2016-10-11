package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.JCommander;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        CommandlineParameters parameters = new CommandlineParameters();
        new JCommander(parameters, args);
        print(parameters);

        //go go gadget do something with this
        PluginExecutor pluginExecutor = new PluginExecutor(parameters);
        pluginExecutor.execute(parameters);


    }

    public static void print(CommandlineParameters commandlineParameters) {
        System.out.println("Path:" + commandlineParameters.getStoryFilePath());
        System.out.println("Delimiter:" + commandlineParameters.getDelimter());
    }
}
