package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;

import java.util.ArrayList;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer {

    public static EstimationValidatorEntry performScorer(Float estimation) {

        float points;
        if(estimation == null || estimation.compareTo(0f) == 0){
            points = 0f;
        }else{
            points = 1f;
        }

        return EstimationValidatorEntry
                .builder()
                .estimation(estimation)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .build();
    }

}
