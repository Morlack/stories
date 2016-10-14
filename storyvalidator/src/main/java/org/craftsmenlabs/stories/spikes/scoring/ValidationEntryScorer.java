package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.api.models.ValidatorEntry;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class ValidationEntryScorer implements Scorer{

    @Override
    public float performScorer(ValidatorEntry validatorEntry) {
        StoryScorer storyScorer = new StoryScorer();
        float storyScore = storyScorer.performScorer(validatorEntry);

        AcceptanceCriteriaScorer acceptanceCriteriaScorer = new AcceptanceCriteriaScorer();
        float acScore = acceptanceCriteriaScorer.performScorer(validatorEntry);

        return storyScore + acScore / 2;
    }
}
