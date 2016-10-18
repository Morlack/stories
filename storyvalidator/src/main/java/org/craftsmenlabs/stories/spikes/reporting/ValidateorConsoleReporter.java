package org.craftsmenlabs.stories.spikes.reporting;

import java.util.List;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateorConsoleReporter
{
	private final Logger logger = LoggerFactory.getLogger(ValidateorConsoleReporter.class);

	public void rankingReport(float ranking)
	{
		logger.info("Overall score is: " + (Math.round(ranking * 100)) + " points!");
	}

	public void reportOnStories(List<IssueValidatorEntry> entries)
	{
		for (int i = 0; i < entries.size(); i++)
		{
            IssueValidatorEntry entry = entries.get(i);
			float pointsValuation = entry.getPointsValuation();
			String userstory = entry.getIssue().getUserstory().replace("\n", "");

			logger.info(
			        "Item on list: " + (i + 1)
                 + " points:" + pointsValuation + "\t\t"
                 + " key: " + entry.getIssue().getKey() + "\t"
                 + userstory );

            entries.get(i)
                    .getViolations()
                    .forEach(violation ->
                            logger.info("Violation found:" + violation.toColoredString()));
		}
	}
}
