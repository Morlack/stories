package org.craftsmenlabs.stories.spikes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.ranking.BinaryRanking;
import org.craftsmenlabs.stories.spikes.ranking.Ranking;
import org.craftsmenlabs.stories.spikes.scoring.StoryScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoryValidator
{

	private final Logger logger = LoggerFactory.getLogger(StoryValidator.class);
	private StoryScorer storyScorer = new StoryScorer();
	private Ranking ranking = new BinaryRanking();

	public List<ValidatorEntry> convertToValidatorEntries(List<Issue> issues)
	{
		if (issues == null || issues.size() == 0)
		{
			throw new IllegalArgumentException("The list of items to convert should not be filled.");
		}
		List<ValidatorEntry> entries = issues.stream()
			//                .sorted(Comparator.comparing(Issue::getRank))
			.map(issue ->
				ValidatorEntry.builder()
					.issue(issue)
					.violations(new ArrayList<>())
					.build())
			.collect(Collectors.toList());
		return entries;
	}

	public List<ValidatorEntry> scoreStories(List<ValidatorEntry> entries)
	{
		for (ValidatorEntry entry : entries)
		{
			entry.setPointsValuation(storyScorer.performScorer(entry));
		}
		return entries;
	}

	public float rankStories(List<ValidatorEntry> entries)
	{
		if (entries == null || entries.size() == 0)
		{
			throw new IllegalArgumentException("The list of items to convert should not be filled.");
		}
		return ranking.createRanking(entries);
	}

}
