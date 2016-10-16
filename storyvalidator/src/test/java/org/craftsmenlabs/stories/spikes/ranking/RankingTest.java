package org.craftsmenlabs.stories.spikes.ranking;

import org.junit.Test;

/**
 *
 */
public interface RankingTest
{
	@Test void testRankingHandlesNullWorks() throws Exception;

	@Test void testRankingHandlesEmptyWorks() throws Exception;

	@Test void testRankingIsZeroWithOnlyUnscoredItemsWorks() throws Exception;

	@Test void testRankingIsOneWithPerfectItemsWorks() throws Exception;

	@Test void testRankingRankWithMixedSethWorks() throws Exception;

	@Test void testRankingIncreasesOnGoodInputWorks() throws Exception;

	@Test void testRankingDecreasesOnBadInputWorks() throws Exception;

	@Test void testRankingDecreasesMinimalOnBadBottomInputWorks() throws Exception;
}
