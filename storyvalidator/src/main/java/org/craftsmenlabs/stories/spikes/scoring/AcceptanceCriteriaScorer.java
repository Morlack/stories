package org.craftsmenlabs.stories.spikes.scoring;

import org.aeonbits.owner.ConfigFactory;
import org.craftsmenlabs.stories.api.models.CriteriaViolation;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.ViolationType;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer {

    public static final int MINIMUM_LENGTH_OF_ACC_CRITERIA = 10;
    static ScorerConfig cfg = ConfigFactory.create(ScorerConfig.class, System.getenv());

    public static AcceptanceCriteriaValidatorEntry performScorer(String criteria) {

        List<Violation> violations = new ArrayList<>();
        float points = 0f;

        if (criteria != null && criteria.length() >= MINIMUM_LENGTH_OF_ACC_CRITERIA) {
            criteria = criteria.toLowerCase();

            if (criteria.contains("given")) {
                points += 0.3333;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
            }

            if (criteria.contains("when")) {
                points += 0.3333;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));

            }

            if (criteria.contains("then")) {
                points += 0.3333;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));

            }
        }

        Rating rating = points >= cfg.criteriaRatingThreshold()? Rating.SUCCES : Rating.FAIL;

        return AcceptanceCriteriaValidatorEntry
                .builder()
                .acceptanceCriteria(criteria)
                .violations(violations)
                .pointsValuation(points)
                .build();
    }
}
