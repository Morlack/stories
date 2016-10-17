package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.JCommander;
import org.craftsmenlabs.stories.api.models.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        App application = new App();
        application.startApplication(args);
    }

    public void startApplication(String[] args) {
        logger.info("Starting stories plugin.");
        CommandlineParameters parameters = new CommandlineParameters();
        new JCommander(parameters, args);
        print(parameters);

        PluginExecutor pluginExecutor = new PluginExecutor();
        Rating rating = pluginExecutor.execute(parameters);
        if (rating == Rating.SUCCES)
        {
            logger.info("Storyvalidator passed!");
            System.exit(0);
        }
        else
        {
            logger.info("Storyvalidator failed!");
            System.exit(-1);
        }

        logger.info("Finished stories plugin.");
    }

    public void print(CommandlineParameters commandlineParameters) {
        logger.info(
                "Path to file containing Jira export: " + commandlineParameters.getStoryFilePath()
                + " with delimiter: "+commandlineParameters.getDelimter()
                + " with dataformat: " + commandlineParameters.getDataFromat()

        );
    }
}
