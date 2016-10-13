package org.craftsmenlabs.stories.spikes.ranking;

import org.craftsmenlabs.stories.api.models.ValidatorEntry;

import java.util.List;

public interface Ranking {
    float createRanking(List<ValidatorEntry> entries);
}
