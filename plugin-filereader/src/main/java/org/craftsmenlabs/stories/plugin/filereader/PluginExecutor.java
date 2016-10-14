package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spike.isolator.JiraExportParser;
import org.craftsmenlabs.stories.spike.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.spike.isolator.model.JiraCSVIssueDTO;
import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.craftsmenlabs.stories.spikes.reporting.ValidateorConsoleReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PluginExecutor {

    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private StoryValidator storyValidator;
    private ValidateorConsoleReporter validationConsoleReporter = new ValidateorConsoleReporter();

    public PluginExecutor() {
        storyValidator = new StoryValidator();
    }

    public void execute(CommandlineParameters parameters) {
        List<ValidatorEntry> entries = storyValidator.convertToValidatorEntries(getIssues(parameters.getStoryFilePath()));

        float ranking = storyValidator.rankStories(entries);

        validationConsoleReporter.reportOnStories(entries);
        validationConsoleReporter.rankingReport(ranking);
    }

    protected List<Issue> getIssues(String filePath) {
        String input = null;
        try {
            input = JiraExportParser.readFileAsString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        List<JiraCSVIssueDTO> jiraCSVIssueDTOs = JiraExportParser.getIssues(input);

        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        return jiraCSVIssueDTOs.stream()
                .map(jiraIssueDTO -> sentenceSplitter.splitSentence(jiraIssueDTO))
                .collect(Collectors.toList());
    }


//    protected List<String> getIssues(String fileLocation, String delimiter) {
//        //TODO: Something with reading the csv
//
//        List<String> tempItemsUntilTodoIsFixed = new ArrayList<>();
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "As a super office user \n"
//                + "I would like to be informed about the alarms in my user \n"
//                + "so I can have the most preferred alarm on top. \n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "As a marketing manager \n"
//                + "I would like to be informed about the total amount of alarms in my userbase\n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "sdfAs a super office user \n"
//                + "I would like to be informed about the alarms in my user \n"
//                + "so I can have the most preferred alarm on top. \n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "As a marketing manager \n"
//                + "I would like to be informed about the total amount of alarms in my userbase\n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "As a super office user \n"
//                + "so I can have the most preferred alarm on top. \n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "I would like to be informed about the total amount of alarms in my userbase\n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "As a super office user \n"
//                + "I would like to be informed about the alarms in my user \n"
//                + "so I can have the most preferred alarm on top. \n"
//        ));
//        tempItemsUntilTodoIsFixed.add(new String(""
//                + "As a marketing manager \n"
//                + "I would like to be informed about the total amount of alarms in my userbase\n"
//        ));
//        return tempItemsUntilTodoIsFixed;
//    }

}
