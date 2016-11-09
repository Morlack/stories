package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.*;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Assigns points to an issue, based on all
 * underlying fields, such as user story, acceptance criteria, estimated points
 */
public class IssueScorer {

    public static IssueValidatorEntry performScorer(Issue issue, ValidationConfigCopy validationConfig) {
        if(  issue != null
          && issue.getUserstory() != null
                && issue.getAcceptanceCriteria() != null) {

            UserStoryValidatorEntry userStoryValidatorEntry = StoryScorer.performScorer(issue.getUserstory(), validationConfig);
            AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(issue.getAcceptanceCriteria(), validationConfig);
            EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(issue.getEstimation(), validationConfig);

            float points = (float)
                    Stream.of(userStoryValidatorEntry, acceptanceCriteriaValidatorEntry, estimationValidatorEntry)
                            .filter(entry -> entry.isActive())
                            .mapToDouble(AbstractValidatorEntry::getPointsValuation)
                            .average()
                            .orElse(0.0);

            Rating rating = points >= validationConfig.getIssue().getRatingtreshold() ? Rating.SUCCESS : Rating.FAIL;

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
        }else{
            return IssueValidatorEntry
                    .builder()
                    .issue(issue)
                    .violations(new ArrayList<>())
                    .pointsValuation(0f)
                    .rating(Rating.FAIL)
                    .build();
        }
    }
}
