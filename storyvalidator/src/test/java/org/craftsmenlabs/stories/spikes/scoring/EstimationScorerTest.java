package org.craftsmenlabs.stories.spikes.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class EstimationScorerTest {

    @Tested
    EstimationScorer estimationScorer;

    @Test
    public void performScorer_ReturnsZeroOnNull(@Injectable IssueValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = null;
        }};

        float score = estimationScorer.performScorer(entry.getIssue().getEstimation()).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsZeroOnZero(@Injectable IssueValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 0f;
        }};

        float score = estimationScorer.performScorer(entry.getIssue().getEstimation()).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsOneOnValidEstimation(@Injectable IssueValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 3f;
        }};

        float score = estimationScorer.performScorer(entry.getIssue().getEstimation()).getPointsValuation();
        assertThat(score).isCloseTo(1f, withinPercentage(1));
    }

}
