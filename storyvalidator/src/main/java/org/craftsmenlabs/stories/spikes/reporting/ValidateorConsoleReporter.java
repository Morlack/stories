package org.craftsmenlabs.stories.spikes.reporting;

import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.api.models.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ValidateorConsoleReporter
{
	private final Logger logger = LoggerFactory.getLogger(ValidateorConsoleReporter.class);

	public void rankingReport(float ranking)
	{
		logger.info("Overall score is: " + (Math.round(ranking * 100)) + "%");
	}

	public void reportOnStories(List<ValidatorEntry> entries)
	{
		for (int i = 0; i < entries.size(); i++)
		{

			float pointsValuation = entries.get(i).getPointsValuation();
			String userstory = entries.get(i).getIssue().getUserstory();

			logger.info("Item on list: " + (i + 1) + " points:" + pointsValuation + "\t\t" + userstory.replace("\n", ""));
			if(entries.get(i).getViolations()!=null && entries.get(i).getViolations().size()>0){
				for(Violation violation : entries.get(i).getViolations()){
					logger.info("Story validation violations found of type:"+violation.getViolationType());
				}
			}
		}
	}
}
