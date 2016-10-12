package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PluginExecutor {

    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private StoryValidator storyValidator;

    public PluginExecutor() {
        storyValidator = new StoryValidator();
    }

    public void execute(CommandlineParameters parameters) {
        float ranking = storyValidator.retrieveRanking(getDescriptions(parameters.getStoryFilePath(), parameters.getDelimter()));
        logger.info("Ranking is:" + (Math.round(ranking * 100)) + " %");
    }

    protected List<String> getDescriptions(String fileLocation, String delimiter) {
        //TODO: Something with reading the csv

        List<String> tempItemsUntilTodoIsFixed = new ArrayList<>();
        tempItemsUntilTodoIsFixed.add(new String(""
                + "As a super office user \n"
                + "I would like to be informed about the alarms in my user \n"
                + "so I can have the most preferred alarm on top. \n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "As a marketing manager \n"
                + "I would like to be informed about the total amount of alarms in my userbase\n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "sdfAs a super office user \n"
                + "I would like to be informed about the alarms in my user \n"
                + "so I can have the most preferred alarm on top. \n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "As a marketing manager \n"
                + "I would like to be informed about the total amount of alarms in my userbase\n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "As a super office user \n"
                + "so I can have the most preferred alarm on top. \n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "I would like to be informed about the total amount of alarms in my userbase\n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "As a super office user \n"
                + "I would like to be informed about the alarms in my user \n"
                + "so I can have the most preferred alarm on top. \n"
        ));
        tempItemsUntilTodoIsFixed.add(new String(""
                + "As a marketing manager \n"
                + "I would like to be informed about the total amount of alarms in my userbase\n"
        ));
        return tempItemsUntilTodoIsFixed;
    }

}
