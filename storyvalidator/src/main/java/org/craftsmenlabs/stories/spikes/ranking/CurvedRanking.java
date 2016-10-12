package org.craftsmenlabs.stories.spikes.ranking;

import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;

import java.util.List;

public class CurvedRanking {

    public static final int SMOOTH_CURVE = 2;

    public float createRanking(List<ValidatorEntry> entries) {
        int maxNumber = (entries.size() * (entries.size() + 1)) / 2;

        float scoredPoints = 0f;
        for (ValidatorEntry v : entries) {
            scoredPoints += v.getPointsValuation();
        }
        return scoredPoints / maxNumber;
    }

    public float curvedQuotient(float position, float amountOfItems) {
        float part = position / amountOfItems;
        return 1 - (float) (Math.pow(part, SMOOTH_CURVE));
    }
}
