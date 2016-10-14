package org.craftsmenlabs.stories.spikes.scoring;

import java.util.List;
import org.craftsmenlabs.stories.api.models.*;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer implements Scorer{

    @Override
    public float performScorer(ValidatorEntry validatorEntry) {
        String criteria = validatorEntry.getIssue().getAcceptanceCriteria();
        //TODO: add to lower case?
        //String criteria = validatorEntry.getIssue().getAcceptanceCriteria().toLowerCase();
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

        return points;

    }
}
