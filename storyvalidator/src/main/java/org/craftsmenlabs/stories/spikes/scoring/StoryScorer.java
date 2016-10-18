package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.api.models.StoryViolation;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.ViolationType;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;

import java.util.ArrayList;
import java.util.List;

public class StoryScorer {

    public static UserStoryValidatorEntry performScorer(String userStory) {

        List<Violation> violations = new ArrayList<>();

        float points = 0.0f;

        if (userStory == null || userStory.isEmpty())
        {
            violations.add(new StoryViolation(ViolationType.StoryEmptyViolation, "This story is empty."));
        }else {
            userStory = userStory.toLowerCase();
            if (userStory.length() > 20) {
                points += 0.2f;
            } else {
                violations.add(new StoryViolation(ViolationType.StoryLengthClauseViolation, "Story is to short."));
            }
            if (userStory.contains("as a")) {
                points += 0.2f;
            } else {
                violations.add(new StoryViolation(ViolationType.StoryAsIsClauseViolation, "<As a> section is not described properly."));
            }
            if (userStory.contains("i")) {
                points += 0.2f;
            } else {
                violations.add(new StoryViolation(ViolationType.StoryIClauseViolation, "<I want> section is not described properly."));
            }
            if (userStory.contains("so")) {
                points += 0.4f;
            } else {
                violations.add(new StoryViolation(ViolationType.StorySoClauseViolation, "<So that> section is not described properly."));
            }
        }
        return UserStoryValidatorEntry
                .builder()
                .userStory(userStory)
                .pointsValuation(points)
                .violations(violations)
                .build();
    }

}
