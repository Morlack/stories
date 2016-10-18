package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.spike.importer.FileImporter;
import org.craftsmenlabs.stories.spike.importer.Importer;
import org.craftsmenlabs.stories.spike.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.spike.isolator.parser.Parser;
import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.craftsmenlabs.stories.spikes.ranking.CurvedRanking;
import org.craftsmenlabs.stories.spikes.reporting.ValidateorConsoleReporter;
import org.craftsmenlabs.stories.spikes.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PluginExecutor {

    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private StoryValidator storyValidator;
    private ValidateorConsoleReporter validationConsoleReporter = new ValidateorConsoleReporter();

    String STATUS = "To Do";

    public PluginExecutor() {
        storyValidator = new StoryValidator();
    }

    public Rating execute(CommandlineParameters parameters) {
        Importer importer = getImporter(parameters);
        String data = importer.getDataAsString();
        Parser parser = getParser(parameters.getDataFromat());

        List<Issue> issues =
                parser.getIssues(data)
                        .stream()
                        .filter(issue -> issue.getUserstory() != null )
                        .filter(issue -> !issue.getUserstory().isEmpty())
                        .collect(Collectors.toList());

        Backlog backlog = new Backlog();
        backlog.setIssues(issues);

        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking());

        validationConsoleReporter.reportOnStories(backlogValidatorEntry.getIssueValidatorEntries());
        validationConsoleReporter.rankingReport(backlogValidatorEntry.getPointsValuation());

        //Multiply by 100%
        return storyValidator.rateRanking(backlogValidatorEntry.getPointsValuation() * 100);
    }

    public Importer getImporter(CommandlineParameters parameters){
        if(restApiParametersAreSet(parameters)){
            logger.info("rest Api parameters are set, using JiraAPIImporter");
            return new JiraAPIImporter(parameters.getUrl(), parameters.getProjectKey(), parameters.getAuthKey(), STATUS);
        }else{
            logger.info("No rest Api parameters are set, using FileImporter on file: " + parameters.getStoryFilePath());
            return new FileImporter(parameters.getStoryFilePath());
        }
    }


    private boolean restApiParametersAreSet(CommandlineParameters parameters) {
        return parameters.getUrl() != null &&
                !parameters.getUrl().isEmpty() &&
                parameters.getAuthKey() != null &&
                !parameters.getAuthKey().isEmpty();
    }


    /**
     * Set the parser based on the data format:
     * jirajson, jiracsv, trellojson, etc.
     * @param dataFormat the format of the data
     */
    private Parser getParser(String dataFormat) {
        Parser parser;
        switch( dataFormat ){
            case("jiracsv"):
                parser = new JiraCSVParser();
                break;
            case("jirajson"):
                parser = new JiraJsonParser();
                break;
            default:
                logger.warn("No dataformat specified, please use the parameter -df to enter a dataformat, " +
                        "such as {jirajson, jiracsv}. By default I will now use jirajson.");
                parser = new JiraJsonParser();
                break;
        }
        return parser;
    }
}
