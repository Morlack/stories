package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class IssueValidatorEntry {
    private Issue issue;
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;
    private boolean isActive;

    private UserStoryValidatorEntry userStoryValidatorEntry;
    private AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry;
    private EstimationValidatorEntry estimationValidatorEntry;
}