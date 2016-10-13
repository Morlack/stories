package org.craftsmenlabs.stories.spikes.ranking;



import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.scoring.ValidationEntryScorer;

import java.util.List;

public class BinaryRanking implements Ranking{

    public float createRanking(List<ValidatorEntry> entries) {
        ValidationEntryScorer validationEntryScorer = new ValidationEntryScorer();

        boolean isTop10Ok = entries.subList(0,  10).stream().allMatch(entry -> validationEntryScorer.performScorer(entry) > 0.95f);
        boolean is10_20Ok = entries.subList(10, 20).stream().allMatch(entry -> validationEntryScorer.performScorer(entry) > 0.70f);

        return isTop10Ok && is10_20Ok? 1f : 0f;
    }
}
