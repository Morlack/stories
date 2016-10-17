package org.craftsmenlabs.stories.spikes.ranking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import java.util.List;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.craftsmenlabs.stories.spikes.TestDataGenerator;
import org.junit.Test;
import mockit.Tested;

public class BinairyRankingTest implements RankingTest
{

	@Tested
	BinaryRanking ranking;

	TestDataGenerator testDataGenerator = new TestDataGenerator();

	StoryValidator _storyValidator = new StoryValidator();

	@Override @Test public void testRankingHandlesNullWorks() throws Exception
	{
		float rank = ranking.createRanking(null);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Override @Test public void testRankingHandlesEmptyWorks() throws Exception
	{
		float rank = ranking.createRanking(null);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Override @Test
	public void testRankingIsZeroWithOnlyUnscoredItemsWorks() throws Exception
	{
		List<ValidatorEntry> testEntries = testDataGenerator.getMixedValidatorItems(20);
		float rank = ranking.createRanking(testEntries);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Override @Test
	public void testRankingIsOneWithPerfectItemsWorks() throws Exception
	{
		List<ValidatorEntry> testEntries = _storyValidator.scoreStories(testDataGenerator.getGoodValidatorItems(20));
		float rank = ranking.createRanking(testEntries);
		assertThat(rank).isEqualTo(1.0f);
	}

	@Override @Test
	public void testRankingRankWithMixedSethWorks() throws Exception
	{
		List<ValidatorEntry> testEntries = _storyValidator.scoreStories(testDataGenerator.getMixedValidatorItems(20));
		float rank = ranking.createRanking(testEntries);
		assertThat(rank).isCloseTo(0.0f, withinPercentage(1));
	}

	@Override @Test
	public void testRankingIncreasesOnGoodInputWorks() throws Exception
	{
		List<ValidatorEntry> testEntries = _storyValidator.scoreStories(testDataGenerator.getGoodValidatorItems(20));
		float rank = ranking.createRanking(testEntries);
		assertThat(rank).isCloseTo(1.0f, withinPercentage(1));

		List<ValidatorEntry> testEntries2 = _storyValidator.scoreStories(testDataGenerator.getGoodValidatorItems(20));
		float rank2 = ranking.createRanking(testEntries2);
		assertThat(rank2).isEqualTo(1.0f);

		testEntries2.addAll(testEntries);
		float rank3 = ranking.createRanking(testEntries2);
		assertThat(rank3).isCloseTo(1.0f, withinPercentage(1));
		assertThat(rank).isEqualTo(rank3);
	}

	@Override @Test
	public void testRankingDecreasesOnBadInputWorks() throws Exception
	{
		List<ValidatorEntry> testEntries = _storyValidator.scoreStories(testDataGenerator.getGoodValidatorItems(20));
		float rank = ranking.createRanking(testEntries);
		assertThat(rank).isCloseTo(1.0f, withinPercentage(1));

		List<ValidatorEntry> testEntries2 = testDataGenerator.getMixedValidatorItems(3);
		float rank2 = ranking.createRanking(testEntries2);
		assertThat(rank2).isEqualTo(0.0f);

		testEntries2.addAll(testEntries);
		float rank3 = ranking.createRanking(testEntries2);
		assertThat(rank3).isCloseTo(0.0f, withinPercentage(1));
		assertThat(rank).isGreaterThan(rank3);
	}

	@Override @Test
	public void testRankingDecreasesMinimalOnBadBottomInputWorks() throws Exception
	{
		List<ValidatorEntry> testEntries = _storyValidator.scoreStories(testDataGenerator.getGoodValidatorItems(20));
		float rank = ranking.createRanking(testEntries);
		assertThat(rank).isCloseTo(1.0f, withinPercentage(1));

		List<ValidatorEntry> unrankedEntries = testDataGenerator.getMixedValidatorItems(3);
		testEntries.addAll(unrankedEntries);

		float rank3 = ranking.createRanking(testEntries);
		assertThat(rank3).isCloseTo(1.0f, withinPercentage(1));
		assertThat(rank).isEqualTo(rank3);
	}
}