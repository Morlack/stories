package org.craftsmenlabs.stories.spikes.scoring;

import org.aeonbits.owner.ConfigFactory;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.craftsmenlabs.stories.spikes.configuration.ScorerConfig;

import java.util.ArrayList;

/**
 * Assigns points to an issue, based on all
 * underlying fields, such as user story, acceptancecriteria, estimated points
 */
public class IssueScorer {
    static ScorerConfig cfg = ConfigFactory.create(ScorerConfig.class, System.getenv());

    public static IssueValidatorEntry performScorer(Issue issue) {
        UserStoryValidatorEntry userStoryValidatorEntry = StoryScorer.performScorer(issue.getUserstory());
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(issue.getAcceptanceCriteria());
        EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(issue.getEstimation());

        float points =
                (userStoryValidatorEntry.getPointsValuation()
                + acceptanceCriteriaValidatorEntry.getPointsValuation())
                / 2;

        Rating rating = points >= cfg.issueRatingThreshold()? Rating.SUCCES : Rating.FAIL;

        return IssueValidatorEntry
                .builder()
                .issue(issue)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .userStoryValidatorEntry(userStoryValidatorEntry)
                .acceptanceCriteriaValidatorEntry(acceptanceCriteriaValidatorEntry)
                .estimationValidatorEntry(estimationValidatorEntry)
                .build();
    }
}
