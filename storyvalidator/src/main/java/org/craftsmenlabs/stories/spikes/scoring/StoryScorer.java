package org.craftsmenlabs.stories.spikes.scoring;


import org.craftsmenlabs.stories.api.models.StoryViolation;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.api.models.ViolationType;

public class StoryScorer implements Scorer {
    public float performScorer(ValidatorEntry validatorEntry) {
        float points = 0f;

        if (validatorEntry == null) {
            return points;
        }
        if (validatorEntry.getIssue().getUserstory() == null) {
            return points;
        }
        if (validatorEntry.getIssue().getUserstory().length() > 20) {
            points += 0.1f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StoryMultipleLinesClauseViolation, "Story should contain multiple lines."));
        }
        if (validatorEntry.getIssue().getUserstory().length() > 20) {
            points += 0.1f;
        } else {
            validatorEntry.getViolations().add(new StoryViolation(ViolationType.StoryLengthClauseViolation, "Story is to short."));
        }
        if (validatorEntry.getIssue().getUserstory().toLowerCase().contains("as a")) {
            points += 0.2f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StoryAsIsClauseViolation, "<As a> is section is described properly."));
        }
        if (validatorEntry.getIssue().getUserstory().toLowerCase().contains("\ni")) {
            points += 0.2f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StoryIClauseViolation, "<(new-line)I> section is described properly."));
        }
        if (validatorEntry.getIssue().getUserstory().toLowerCase().contains("\nso")) {
            points += 0.4f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StorySoClauseViolation, "<(new-line)So I> section is described properly."));
        }
        return points;
    }
}
