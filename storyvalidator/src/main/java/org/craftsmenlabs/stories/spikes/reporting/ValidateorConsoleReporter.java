package org.craftsmenlabs.stories.spikes.reporting;

import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.validatorentry.*;
import org.craftsmenlabs.stories.spikes.rating.RatingExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.List;

public class ValidateorConsoleReporter
{
	private final Logger logger = LoggerFactory.getLogger(ValidateorConsoleReporter.class);

	public void rankingReport(float ranking)
	{
		logger.info("Overall score is: " + (Math.round(ranking * 100)) + "%");
	}

    public void reportOnBacklog(BacklogValidatorEntry backlogValidatorEntry){
        //header
        logger.info("\n\n\n" + storynator + " \n\n\n");
        logger.info("------------------------------------------------------------");
        logger.info("--               verbose output                           --");
        logger.info("------------------------------------------------------------");

        //verbose output
        List<IssueValidatorEntry> entries = backlogValidatorEntry.getIssueValidatorEntries();
        entries.forEach(issue -> reportOnIssue(issue));

        //Summary
        RatingExecutor ratingExecutor = new RatingExecutor();
        logger.info("\n\n\n" + storynator + " \n\n\n");
        logger.info("------------------------------------------------------------");
        logger.info("--                  Storynator report                     --");
        logger.info("------------------------------------------------------------");

        logger.info("Processed a total of " + entries.size() + " issues.");
        logger.info("Total score of " + new DecimalFormat("#.##").format(backlogValidatorEntry.getPointsValuation()*100f) + "% ");
        logger.info("Rated: " + ratingExecutor.retrieveRating(backlogValidatorEntry.getPointsValuation()));
    }


    public void reportOnIssue(IssueValidatorEntry issue){
        logger.info("------------------------------------------------------------");
        logger.info("Issue "
                        + issue.getIssue().getKey()
                        + " Total (" + issue.getPointsValuation() + ") \t"
                        + " US("+ issue.getUserStoryValidatorEntry().getPointsValuation() +")\t"
                        + " AC("+ issue.getAcceptanceCriteriaValidatorEntry().getPointsValuation() +")\t"
                  );
        issue.getViolations()
                .forEach(violation ->
                        logger.info("Violation found:" + violation.toColoredString()));

        reportOnUserstory(issue.getUserStoryValidatorEntry());
        reportOnAcceptanceCriteria(issue.getAcceptanceCriteriaValidatorEntry());
        reportOnEstimation(issue.getEstimationValidatorEntry());
    }


    public void reportOnUserstory(UserStoryValidatorEntry entry){
        String userstory = entry.getUserStory().replace("\n", " ").replace("\r", "");
        logger.info("\t Userstory: (" + entry.getPointsValuation() + ") " + userstory);

        reportOnViolations(entry.getViolations());
    }


    public void reportOnAcceptanceCriteria(AcceptanceCriteriaValidatorEntry entry){
        String criteria = entry.getAcceptanceCriteria().replace("\n", " ").replace("\r", "");
        logger.info("\t Criteria: (" + entry.getPointsValuation() + ") " + criteria );

        reportOnViolations(entry.getViolations());
    }

    public void reportOnEstimation(EstimationValidatorEntry entry){
        Float estimation = entry.getEstimation();
        logger.info("\t Estimation: (" +entry.getPointsValuation() + ")" + estimation );

        reportOnViolations(entry.getViolations());
    }


    public void reportOnViolations(List<Violation> violations){
        violations.forEach(violation ->
                logger.info("\t\t Violation found: " + violation.toColoredString()));
    }

    String storynator = "   ,d88~/\\   d8                                                d8                   \n" +
            "   8888/   _d88__  e88~-_  888-~\\ Y88b  / 888-~88e   /~~~8e  _d88__  e88~-_  888-~\\ \n" +
            "   `Y88b    888   d888   i 888     Y888/  888  888       88b  888   d888   i 888    \n" +
            "    `Y88b,  888   8888   | 888      Y8/   888  888  e88~-888  888   8888   | 888    \n" +
            "     /8888  888   Y888   ' 888       Y    888  888 C888  888  888   Y888   ' 888    \n" +
            "   \\/_88P'  \"88_/  \"88_-~  888      /     888  888  \"88_-888  \"88_/  \"88_-~  888    \n" +
            "                                  _/";

}
