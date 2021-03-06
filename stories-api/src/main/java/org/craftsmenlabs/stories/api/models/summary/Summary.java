package org.craftsmenlabs.stories.api.models.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    private float pointsValuation;

    @JsonProperty("rating")
    private Rating rating;

    private long issueCount;
    private long failedIssueCount;
    private long passedIssueCount;
    private long totalIssueViolationsCount;

    private long storyCount;
    private long failedStoryCount;
    private long passedStoryCount;
    private long totalStoryViolationsCount;

    private long criteriaCount;
    private long failedCriteriaCount;
    private long passedCriteriaCount;
    private long totalCriteriaViolationsCount;

    private long bugCount;
    private long failedBugCount;
    private long passedBugCount;

    private long epicCount;
    private long failedEpicCount;
    private long passedEpicCount;

}
