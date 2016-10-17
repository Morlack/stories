package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spike.isolator.parser.FileParser;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.craftsmenlabs.stories.spikes.reporting.ValidateorConsoleReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PluginExecutor {

    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private StoryValidator storyValidator;
    private ValidateorConsoleReporter validationConsoleReporter = new ValidateorConsoleReporter();
    FileParser fileParser;

    public PluginExecutor() {
        storyValidator = new StoryValidator();
    }

    public Rating execute(CommandlineParameters parameters) {
        setFileParser(parameters.getDataFromat());

        List<Issue> issues = fileParser.getIssues(new File(parameters.getStoryFilePath()));

        issues = issues.stream()
                .filter(issue -> issue.getUserstory() != null )
                .filter(issue -> !issue.getUserstory().isEmpty())
                .collect(Collectors.toList());

        List<ValidatorEntry> entries = storyValidator.convertToValidatorEntries(issues);
        List<ValidatorEntry> scoredEntries = storyValidator.scoreStories(entries);

        float ranking = storyValidator.rankStories(scoredEntries);

        validationConsoleReporter.reportOnStories(entries);
        validationConsoleReporter.rankingReport(ranking);

        //Multiply by 100%
        return storyValidator.rateRanking(ranking * 100);
    }

    /**
     * Set the fileParser based on the data format:
     * jirajson, jiracsv, trellojson, etc.
     * @param dataFormat
     */
    private void setFileParser(String dataFormat) {
        switch( dataFormat ){
            case("jirajson"):
                fileParser = new JiraJsonParser();
                break;
            case("jiracsv"):
                fileParser = new JiraCSVParser();
                break;
        }
    }
}
