package org.craftsmenlabs.stories.spikes.scoring;


import org.craftsmenlabs.stories.api.models.ValidatorEntry;

public interface Scorer
{
	float performScorer(ValidatorEntry validatorEntry);
}
