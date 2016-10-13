package org.craftsmenlabs.stories.spikes.ranking;

import org.craftsmenlabs.stories.api.models.ValidatorEntry;

import java.util.List;

public class LinearRanking implements Ranking {
    public float createRanking(List<ValidatorEntry> entries) {
        int maxNumber = (entries.size() * (entries.size() + 1)) / 2;

        float scoredPoints = 0f;
        for (ValidatorEntry v : entries) {
            scoredPoints += v.getPointsValuation();
        }
        return scoredPoints / maxNumber;
    }
}
