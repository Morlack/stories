package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;

import java.util.ArrayList;

/**
 * Assigns points to an issue, based on all
 * underlying fields, such as user story, acceptancecriteria, estimated points
 */
public class IssueScorer {

    public static IssueValidatorEntry performScorer(Issue issue) {
        UserStoryValidatorEntry userStoryValidatorEntry = StoryScorer.performScorer(issue.getUserstory());
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(issue.getAcceptanceCriteria());
        EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(issue.getEstimation());

        float pointsValuation =
                (userStoryValidatorEntry.getPointsValuation()
                + acceptanceCriteriaValidatorEntry.getPointsValuation())
                / 2;

        return IssueValidatorEntry
                .builder()
                .issue(issue)
                .violations(new ArrayList<>())
                .pointsValuation(pointsValuation)
                .userStoryValidatorEntry(userStoryValidatorEntry)
                .acceptanceCriteriaValidatorEntry(acceptanceCriteriaValidatorEntry)
                .estimationValidatorEntry(estimationValidatorEntry)
                .build();
    }
}
