package org.craftsmenlabs.stories.spikes.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.api.models.Violation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class AcceptanceCriteriaScorerTest {

    @Tested
    AcceptanceCriteriaScorer criteriaScorer;

    @Test
    public void testPerformScorer(@Injectable ValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "Given \n"
                    + "When \n"
                    + "Then .";
        }};

        float score = criteriaScorer.performScorer(entry);
        assertThat(score).isCloseTo(1.0f, withinPercentage(5));
    }

    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable ValidatorEntry entry) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "";
            entry.getViolations();
            result = v;
        }};

        float score = criteriaScorer.performScorer(entry);
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsNullOnEmpty(@Injectable ValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = null;
        }};

        float score = criteriaScorer.performScorer(entry);
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }
}
