package org.craftsmenlabs.stories.spikes.scoring;


import org.craftsmenlabs.stories.api.models.StoryViolation;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.ViolationType;

import java.util.List;

public class StoryScorer implements Scorer {

    @Override
    public float performScorer(ValidatorEntry validatorEntry) {
        String userstory = validatorEntry.getIssue().getUserstory().toLowerCase();
        List<Violation> violations = validatorEntry.getViolations();

        float points = 0f;

        if (validatorEntry == null) {
            return points;
        }
        if (userstory == null) {
            return points;
        }
        if (userstory.length() > 20) {
            points += 0.1f;
        } else {
            violations.add(new StoryViolation(ViolationType.StoryMultipleLinesClauseViolation, "Story should contain multiple lines."));
        }
        if (userstory.length() > 20) {
            points += 0.1f;
        } else {
            violations.add(new StoryViolation(ViolationType.StoryLengthClauseViolation, "Story is to short."));
        }
        if (userstory.contains("as a")) {
            points += 0.2f;
        } else {
            violations.add(new StoryViolation(ViolationType.StoryAsIsClauseViolation, "<As a> section is not described properly."));
        }
        if (userstory.contains("\ni")) {
            points += 0.2f;
        } else {
            violations.add(new StoryViolation(ViolationType.StoryIClauseViolation, "<(new-line)I> section is not described properly."));
        }
        if (userstory.contains("\nso")) {
            points += 0.4f;
        } else {
            violations.add(new StoryViolation(ViolationType.StorySoClauseViolation, "<(new-line)So I> section is not described properly."));
        }
        return points;
    }
}
