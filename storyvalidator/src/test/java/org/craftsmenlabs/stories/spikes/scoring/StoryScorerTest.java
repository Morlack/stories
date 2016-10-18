package org.craftsmenlabs.stories.spikes.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StoryScorerTest {

    @Tested
    StoryScorer storyScorer;

    @Test
    public void testPerformScorer(@Injectable IssueValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = "As a super office user \n"
                + "I would like to be informed about the alarms in my user \\n\"\n"
                + "so I can have the most preferred alarm on top.";
        }};

        float score = storyScorer.performScorer(entry.getIssue().getUserstory()).getPointsValuation();
        assertThat(score).isEqualTo(1.0f);
    }

    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable IssueValidatorEntry entry) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = "";
        }};

        float score = storyScorer.performScorer(entry.getIssue().getUserstory()).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorer_ReturnsNullOnEmpty(@Injectable IssueValidatorEntry entry) throws Exception {
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = null;
        }};

        float score = storyScorer.performScorer(entry.getIssue().getUserstory()).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }
}
