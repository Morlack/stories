package org.craftsmenlabs.stories.spikes.scoring;

import org.aeonbits.owner.ConfigFactory;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;

import java.util.ArrayList;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer {
    static ScorerConfig cfg = ConfigFactory.create(ScorerConfig.class, System.getenv());

    public static EstimationValidatorEntry performScorer(Float estimation) {

        float points;
        if(estimation == null || estimation.compareTo(0f) == 0){
            points = 0f;
        }else{
            points = 1f;
        }
        Rating rating = points >= cfg.estimationRatingThreshold()? Rating.SUCCES : Rating.FAIL;

        return EstimationValidatorEntry
                .builder()
                .estimation(estimation)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .build();
    }

}
