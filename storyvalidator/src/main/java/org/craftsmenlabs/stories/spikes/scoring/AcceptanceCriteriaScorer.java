package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.api.models.CriteriaViolation;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.ViolationType;

import java.util.List;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer implements Scorer{

    @Override
    public float performScorer(ValidatorEntry validatorEntry) {
        String criteria = validatorEntry.getIssue().getAcceptanceCriteria();
        List<Violation> violations = validatorEntry.getViolations();
        float points = 0f;

        if(criteria == null){
            return points;
        }else{
            criteria = criteria.toLowerCase();
        }


        if(criteria.contains("given")){
            points += 0.3333;
        }else{
            violations.add(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
        }

        if(criteria.contains("when")){
            points += 0.3333;
        }else{
            violations.add(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));

        }

        if(criteria.contains("then")){
            points += 0.3333;
        }else{
            violations.add(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));

        }

        validatorEntry.setPointsValuation((int) (points * 100));
        return points;

    }
}
