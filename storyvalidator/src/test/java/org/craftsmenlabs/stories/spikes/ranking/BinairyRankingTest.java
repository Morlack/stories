package org.craftsmenlabs.stories.spikes.ranking;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.TestDataGenerator;
import org.junit.Test;
import mockit.Injectable;
import mockit.Tested;

public class BinairyRankingTest
{

	@Tested
	BinaryRanking _binaryRanking;
	TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void rankStoriesWithBinaryRankingTest(@Injectable LinearRanking ranking) throws Exception
	{
		List<ValidatorEntry> items = testDataGenerator.getGoodValidatorItems(25);

		float rank = _binaryRanking.createRanking(items);
		assertThat(rank).isEqualTo(1.0f);
	}

	@Test
	public void rankStoriesWithAddedOneBadOneOnTop_Should_result_in_ZeroTest(@Injectable LinearRanking ranking) throws Exception
	{
		List<ValidatorEntry> items = new ArrayList<>();
		items.add(new ValidatorEntry(testDataGenerator.getIssues().get(2), 0.0f, new ArrayList<>()));
		items.addAll(testDataGenerator.getGoodValidatorItems(20));

		float rank = _binaryRanking.createRanking(items);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Test
	public void rankStoriesWithBinaryRankingTest_ReturnsZeroOnEmptyList() throws Exception
	{
		float rank = _binaryRanking.createRanking(Collections.emptyList());
		assertThat(rank).isEqualTo(0.0f);
	}
}
