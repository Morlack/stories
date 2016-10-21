package org.craftsmenlabs.stories.ranking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import java.util.List;
import org.craftsmenlabs.stories.TestDataGenerator;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.junit.Before;
import org.junit.Test;
import mockit.Tested;

public class CurvedRankingTest implements RankingTest
{
	private ScorerConfigCopy scorerConfigCopy;

	@Tested
	private CurvedRanking ranking;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Before
	public void setUp() throws Exception
	{
		scorerConfigCopy = new ScorerConfigCopy();
		scorerConfigCopy.setStory(new ScorerConfigCopy.ValidatorEntryCopy());
		scorerConfigCopy.setCriteria(new ScorerConfigCopy.ValidatorEntryCopy());
		scorerConfigCopy.setEstimation(new ScorerConfigCopy.ValidatorEntryCopy());
		scorerConfigCopy.setIssue(new ScorerConfigCopy.ValidatorEntryCopy());
		scorerConfigCopy.setBacklog(new ScorerConfigCopy.ValidatorEntryCopy());
	}

	@Override
	@Test
	public void testRankingHandlesNullWorks() throws Exception
	{
		float rank = ranking.createRanking(null);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Override
	@Test
	public void testRankingHandlesEmptyWorks() throws Exception
	{
		BacklogValidatorEntry backlogValidatorEntryWithEmptyList =
			BacklogValidatorEntry.builder().issueValidatorEntries(null).build();
		float rank = ranking.createRanking(backlogValidatorEntryWithEmptyList);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Override @Test
	public void testRankingIsZeroWithOnlyUnscoredItemsWorks() throws Exception
	{
		BacklogValidatorEntry goodBacklog = testDataGenerator.getGoodBacklog(10);
		float rank = ranking.createRanking(goodBacklog);
		assertThat(rank).isEqualTo(0.0f);
	}

	@Override @Test
	public void testRankingIsOneWithPerfectItemsWorks() throws Exception
	{
		List<Issue> issues = testDataGenerator.getGoodIssues(20);
		Backlog b = new Backlog();
		b.setIssues(issues);
		BacklogValidatorEntry testEntries = BacklogScorer.performScorer(b, new CurvedRanking(), scorerConfigCopy);
		assertThat(testEntries.getPointsValuation()).isCloseTo(1.0f, withinPercentage(1.0));
	}

	@Override
	@Test
	public void testRankingRankWithMixedSethWorks() throws Exception
	{

		Backlog b = getBacklog(testDataGenerator.getMixedValidatorItems(20));
		BacklogValidatorEntry testEntries = BacklogScorer.performScorer(b, new CurvedRanking(), scorerConfigCopy);
		assertThat(testEntries.getPointsValuation()).isCloseTo(0.707f, withinPercentage(1));
	}

	@Override
	@Test
	public void testRankingIncreasesOnGoodInputWorks() throws Exception
	{

		Backlog test1 = getBacklog(testDataGenerator.getMixedValidatorItems(20));
		BacklogValidatorEntry testEntries = BacklogScorer.performScorer(test1, new CurvedRanking(), scorerConfigCopy);
		float rank1 = testEntries.getPointsValuation();
		assertThat(rank1).isCloseTo(0.707f, withinPercentage(1));

		Backlog test2 = getBacklog(testDataGenerator.getGoodIssues(3));
		BacklogValidatorEntry testEntries2 = BacklogScorer.performScorer(test2, new CurvedRanking(), scorerConfigCopy);
		assertThat(testEntries2.getPointsValuation()).isCloseTo(1.0f, withinPercentage(1));

		Backlog testAll = getBacklog(testDataGenerator.getGoodIssues(3));
		testAll.getIssues().addAll(testDataGenerator.getMixedValidatorItems(20));

		BacklogValidatorEntry testEntriesAll = BacklogScorer.performScorer(testAll, new CurvedRanking(), scorerConfigCopy);

		float rank3 = testEntriesAll.getPointsValuation();
		assertThat(rank3).isCloseTo(0.763f, withinPercentage(1));
		assertThat(rank1).isLessThan(rank3);
	}

	@Override
	@Test
	public void testRankingDecreasesOnBadInputWorks() throws Exception
	{
		Backlog test1 = getBacklog(testDataGenerator.getMixedValidatorItems(20));
		BacklogValidatorEntry testEntries = BacklogScorer.performScorer(test1, new CurvedRanking(), scorerConfigCopy);
		float rank1 = testEntries.getPointsValuation();
		assertThat(rank1).isCloseTo(0.707f, withinPercentage(1));

		Backlog test2 = getBacklog(testDataGenerator.getBadValidatorItems(3));
		BacklogValidatorEntry testEntries2 = BacklogScorer.performScorer(test2, new CurvedRanking(), scorerConfigCopy);
		assertThat(testEntries2.getPointsValuation()).isCloseTo(0.0f, withinPercentage(1));

		Backlog testAll = getBacklog(testDataGenerator.getBadValidatorItems(3));
		testAll.getIssues().addAll(testDataGenerator.getMixedValidatorItems(20));

		BacklogValidatorEntry testEntriesAll = BacklogScorer.performScorer(testAll, new CurvedRanking(), scorerConfigCopy);

		float rank3 = testEntriesAll.getPointsValuation();
		assertThat(rank3).isCloseTo(0.57401336f, withinPercentage(1));
		assertThat(rank1).isGreaterThan(rank3);

	}

	@Override
	@Test
	public void testRankingDecreasesMinimalOnBadBottomInputWorks() throws Exception
	{
		Backlog test1 = getBacklog(testDataGenerator.getMixedValidatorItems(20));
		BacklogValidatorEntry testEntries = BacklogScorer.performScorer(test1, new CurvedRanking(), scorerConfigCopy);
		float rank1 = testEntries.getPointsValuation();
		assertThat(rank1).isCloseTo(0.707f, withinPercentage(1));

		Backlog test2 = getBacklog(testDataGenerator.getBadValidatorItems(3));
		BacklogValidatorEntry testEntries2 = BacklogScorer.performScorer(test2, new CurvedRanking(), scorerConfigCopy);
		assertThat(testEntries2.getPointsValuation()).isCloseTo(0.0f, withinPercentage(1));

		Backlog testAll = getBacklog(testDataGenerator.getMixedValidatorItems(20));
		testAll.getIssues().addAll(testDataGenerator.getBadValidatorItems(3));

		BacklogValidatorEntry testEntriesAll = BacklogScorer.performScorer(testAll, new CurvedRanking(), scorerConfigCopy);

		float rank3 = testEntriesAll.getPointsValuation();
		assertThat(rank3).isCloseTo(0.686f, withinPercentage(1));
		assertThat(rank1).isGreaterThan(rank3);
	}

	@Test
	public void testCurve() throws Exception
	{
		float curve = ranking.curvedQuotient(10, 20);
		assertThat(curve).isEqualTo(0.75f);
	}

	private Backlog getBacklog(List<Issue> issues)
	{
		Backlog b = new Backlog();
		b.setIssues(issues);
		return b;
	}
}

