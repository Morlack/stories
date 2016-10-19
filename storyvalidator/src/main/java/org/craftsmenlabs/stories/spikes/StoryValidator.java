package org.craftsmenlabs.stories.spikes;

import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.spikes.ranking.CurvedRanking;
import org.craftsmenlabs.stories.spikes.ranking.Ranking;
import org.craftsmenlabs.stories.spikes.scoring.BacklogScorer;
import org.craftsmenlabs.stories.spikes.scoring.StoryScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StoryValidator
{

	private final Logger logger = LoggerFactory.getLogger(StoryValidator.class);
	private StoryScorer storyScorer = new StoryScorer();
	private Ranking ranking = new CurvedRanking();

    public BacklogValidatorEntry scoreStories(List<Issue> entries)
    {
        Backlog backlog = new Backlog();
        backlog.setIssues(entries);
        return BacklogScorer.performScorer(backlog, ranking);
    }
}
