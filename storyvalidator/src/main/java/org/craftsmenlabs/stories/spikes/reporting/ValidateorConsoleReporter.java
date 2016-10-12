package org.craftsmenlabs.stories.spikes.reporting;

import java.util.List;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateorConsoleReporter
{
	private final Logger logger = LoggerFactory.getLogger(ValidateorConsoleReporter.class);

	public void rankingReport(float ranking)
	{
		logger.info("Ranking is: " + (Math.round(ranking * 100)) + "%");
	}

	public void reportOnStories(List<ValidatorEntry> entries)
	{
		for (int i = 0; i < entries.size(); i++)
		{
			logger.info("Item on list: " + (i + 1) + " points:" + entries.get(i).getPointsValuation() + "\t\t" + entries.get(i)
				.getIssue().getUserstory().replace("\n", ""));
		}
	}
}
