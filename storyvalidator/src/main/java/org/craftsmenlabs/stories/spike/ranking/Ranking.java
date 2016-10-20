package org.craftsmenlabs.stories.spike.ranking;

import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;

public interface Ranking {
    float createRanking(BacklogValidatorEntry backlogValidatorEntry);
}
