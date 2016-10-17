package org.craftsmenlabs.stories.spikes;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.ranking.CurvedRanking;
import org.craftsmenlabs.stories.spikes.ranking.Ranking;
import org.craftsmenlabs.stories.spikes.rating.RatingExecutor;
import org.craftsmenlabs.stories.spikes.scoring.StoryScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoryValidator
{

	private final Logger logger = LoggerFactory.getLogger(StoryValidator.class);
	private StoryScorer storyScorer = new StoryScorer();
	private Ranking ranking = new CurvedRanking();
	private RatingExecutor ratingExecutor = new RatingExecutor();

	public List<ValidatorEntry> convertToValidatorEntries(List<Issue> issues)
	{
		if (issues == null || issues.size() == 0)
		{
			throw new IllegalArgumentException("The list of items to convert should be filled.");
		}
		List<ValidatorEntry> entries = issues.stream()
			//                .sorted(Comparator.comparing(Issue::getRank))
			.map(issue ->
				ValidatorEntry.builder()
					.issue(issue)
					.violations(new ArrayList<>())
					.pointsValuation(0f)
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
			throw new IllegalArgumentException("The list of items to convert should be filled.");
		}
		return ranking.createRanking(entries);
	}

	public Rating rateRanking(float ranking)
	{
		return ratingExecutor.retrieveRating(ranking);
	}
}
