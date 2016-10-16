package org.craftsmenlabs.stories.spikes.scoring;

import java.util.List;
import org.craftsmenlabs.stories.api.models.*;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer implements Scorer{

    public static final int MINIMUM_LENGTH_OF_ACC_CRITERIA = 10;

    @Override
    public float performScorer(ValidatorEntry validatorEntry) {
        String criteria = validatorEntry.getIssue().getAcceptanceCriteria();

        List<Violation> violations = validatorEntry.getViolations();
        float points = 0f;

        if (criteria == null || criteria.length() < MINIMUM_LENGTH_OF_ACC_CRITERIA)
        {

            return points;
        }else{
            criteria = criteria.toLowerCase();
        }

        if (criteria.toLowerCase().contains("given"))
        {
            points += 0.3333;
        }else{
            violations.add(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
        }

        if (criteria.toLowerCase().contains("when"))
        {
            points += 0.3333;
        }else{
            violations.add(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));

        }

        if (criteria.toLowerCase().contains("then"))
        {
            points += 0.3333;
        }else{
            violations.add(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));

        }

        return points;

    }
}
