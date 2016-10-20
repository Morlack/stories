package org.craftsmenlabs.stories.plugin.filereader;

import com.beust.jcommander.JCommander;
import org.aeonbits.owner.ConfigFactory;
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

        ApplicationConfig cfg = getApplicationConfig(args);

        PluginExecutor pluginExecutor = new PluginExecutor();
        Rating rating = pluginExecutor.execute(cfg);
        if (rating == Rating.SUCCES)
        {
            System.exit(0);
        }
        else
        {
            System.exit(-1);
        }

        logger.info("Finished stories plugin.");
    }

    private ApplicationConfig getApplicationConfig(String[] args) {
        ApplicationConfig cfg;
        if (args.length > 0) {
//            Properties props = new Properties();
//            for (int i = 0; i <args.length/2; i++) {
//                props.setProperty(args[i], args[i+1]);
//            }
            cfg = ConfigFactory.create(ApplicationConfig.class, System.getProperties());
        } else {
            cfg = ConfigFactory.create(ApplicationConfig.class);
        }
        return cfg;
    }

    public void print(CommandlineParameters commandlineParameters) {
        logger.info(
                "Path to file containing Jira export: " + commandlineParameters.getStoryFilePath()
                + " with delimiter: "+commandlineParameters.getDelimter()
                + " with dataformat: " + commandlineParameters.getDataFromat()

        );
    }
}
