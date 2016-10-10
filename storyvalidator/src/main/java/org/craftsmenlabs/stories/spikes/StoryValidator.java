package org.craftsmenlabs.stories.spikes;

import org.craftsmenlabs.stories.spikes.convertor.StringToEntryConverter;
import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.scoring.StoryScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StoryValidator
{

	private final Logger logger = LoggerFactory.getLogger(StoryValidator.class);

	private StoryScorer regexScorer = new StoryScorer();
	private StringToEntryConverter entryConverter = new StringToEntryConverter();

	public float retrieveRanking(List<String> testData)
	{
		List<ValidatorEntry> entries = entryConverter.parseEntries(testData);

		for (ValidatorEntry item : entries)
		{
			int possibleScore = (entries.size() - (item.getBacklogPosition() - 1));
			item.setPointsValuation((int)(regexScorer.performScorer(item) * possibleScore));
		}
		listEntries(entries);

		return createRanking(entries);
	}

	protected float createRanking(List<ValidatorEntry> entries)
	{
		int maxNumber = (entries.size() * (entries.size() + 1)) / 2;

		float scoredPoints = 0f;
		for (ValidatorEntry v : entries)
		{
			scoredPoints += v.getPointsValuation();
		}
		return scoredPoints / maxNumber;
	}

	private void listEntries(List<ValidatorEntry> entries)
	{
		for (int i = 0; i < entries.size(); i++)
		{
			logger.info("N: " + (i + 1) + " points:" + entries.get(i).getPointsValuation() + "\t\t" + entries.get(i)
				.getSourceTextDescription());
		}
	}
}
