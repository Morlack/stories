package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;

public class StoryScorer implements Scorer
{
	public float performScorer(ValidatorEntry validatorEntry)
	{
		float points = 0f;

		if (validatorEntry == null)
		{
			return points;
		}
		if (validatorEntry.getSourceTextDescription() == null)
		{
			return points;
		}

		if (validatorEntry.getSourceTextDescription().toLowerCase().contains("as a"))
		{
			points += 0.2f;
		}
		if (validatorEntry.getSourceTextDescription().toLowerCase().contains("\ni"))
		{
			points += 0.2f;
		}
		if (validatorEntry.getSourceTextDescription().toLowerCase().contains("\nso"))
		{
			points += 0.6f;
		}
		return points;
	}
}
