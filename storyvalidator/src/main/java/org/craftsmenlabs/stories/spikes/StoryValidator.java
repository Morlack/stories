package org.craftsmenlabs.stories.spikes;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.ranking.LinearRanking;
import org.craftsmenlabs.stories.spikes.scoring.StoryScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoryValidator
{

	private final Logger logger = LoggerFactory.getLogger(StoryValidator.class);
	private StoryScorer regexScorer = new StoryScorer();
    private LinearRanking linearRanking = new LinearRanking();

	public List<ValidatorEntry> convertToValidatorEntries(List<Issue> issues){
		List<ValidatorEntry> entries = issues.stream()
			//                .sorted(Comparator.comparing(Issue::getRank))
			.map(issue ->
				ValidatorEntry.builder()
					.issue(issue)
					.violations(new ArrayList<>())
					.build())
			.collect(Collectors.toList());
		applyScoring(entries);
		return entries;
	}

	public float rankStories(List<ValidatorEntry> entries){
		return linearRanking.createRanking(entries);
	}

	private void applyScoring(List<ValidatorEntry> entries)
	{
		for (int i = 0; i < entries.size(); i++) {
			int possibleScore = (entries.size() - (i));
			ValidatorEntry item = entries.get(i);
			item.setPointsValuation((int)(regexScorer.performScorer(item) * possibleScore));
		}

	}
}
