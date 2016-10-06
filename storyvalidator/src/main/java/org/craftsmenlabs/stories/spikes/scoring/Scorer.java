package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;

public interface Scorer
{
	float performScorer(ValidatorEntry validatorEntry);
}
