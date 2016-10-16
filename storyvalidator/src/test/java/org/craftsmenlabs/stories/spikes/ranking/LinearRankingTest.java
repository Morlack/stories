package org.craftsmenlabs.stories.spikes.ranking;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.craftsmenlabs.stories.spikes.TestDataGenerator;
import org.junit.Test;
import mockit.Tested;

/**
 *
 */
public class LinearRankingTest {

    TestDataGenerator testDataGenerator = new TestDataGenerator();

    StoryValidator _storyValidator = new StoryValidator();

    @Tested
    LinearRanking ranking;

    @Test
    public void testRankingIsZeroWithUnscoredItemsWorks() throws Exception
    {
        List<ValidatorEntry> testEntries = testDataGenerator.getGoodValidatorItems(10);
        float rank = ranking.createRanking(testEntries);
        assertThat(rank).isEqualTo(0.0f);
    }

    @Test
    public void testRankingIsOneWithPerfectItemsWorks() throws Exception
    {
        List<ValidatorEntry> testEntries = _storyValidator.scoreStories(testDataGenerator.getGoodValidatorItems(20));
        float rank = ranking.createRanking(testEntries);
        assertThat(rank).isEqualTo(1.0f);
    }
}
