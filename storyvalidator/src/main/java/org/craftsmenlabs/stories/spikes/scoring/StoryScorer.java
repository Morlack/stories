package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;

public class StoryScorer implements Scorer {
    public float performScorer(ValidatorEntry validatorEntry) {
        float points = 0f;

        if (validatorEntry == null) {
            return points;
        }
        if (validatorEntry.getSourceTextDescription() == null) {
            return points;
        }
        if (validatorEntry.getSourceTextDescription().length() > 20) {
            points += 0.1f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StoryMultipleLinesClauseViolation, "Story should contain multiple lines."));
        }
        if (validatorEntry.getSourceTextDescription().length() > 20) {
            points += 0.1f;
        } else {
            validatorEntry.getViolations().add(new StoryViolation(ViolationType.StoryLengthClauseViolation, "Story is to short."));
        }
        if (validatorEntry.getSourceTextDescription().toLowerCase().contains("as a")) {
            points += 0.2f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StoryAsIsClauseViolation, "<As a> is section is described properly."));
        }
        if (validatorEntry.getSourceTextDescription().toLowerCase().contains("\ni")) {
            points += 0.2f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StoryIClauseViolation, "<(new-line)I> section is described properly."));
        }
        if (validatorEntry.getSourceTextDescription().toLowerCase().contains("\nso")) {
            points += 0.4f;
        } else {
            validatorEntry.getViolations()
                    .add(new StoryViolation(ViolationType.StorySoClauseViolation, "<(new-line)So I> section is described properly."));
        }
        return points;
    }
}
