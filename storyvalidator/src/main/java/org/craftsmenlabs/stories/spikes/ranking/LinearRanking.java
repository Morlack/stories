package org.craftsmenlabs.stories.spikes.ranking;

import java.util.List;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;

public class LinearRanking implements Ranking {
    public float createRanking(List<ValidatorEntry> entries) {

        float scoredPoints = 0f;
        for (ValidatorEntry v : entries) {
            scoredPoints += v.getPointsValuation();
        }
        return scoredPoints / entries.size();
    }
}
