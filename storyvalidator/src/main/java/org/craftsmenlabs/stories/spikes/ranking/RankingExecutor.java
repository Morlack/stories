package org.craftsmenlabs.stories.spikes.ranking;

import java.util.List;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.configuration.Configuration;

/**
 *
 */
public class RankingExecutor
{
	private Ranking ranking;

	public RankingExecutor()
	{
		Configuration configuration = new Configuration();
		if (configuration.getDesiredRankingStrategy() == null || configuration.getDesiredRankingStrategy().length() == 0)
		{
			ranking = new LinearRanking();
		}
		else
		{
			ranking = chooseRankingStrategy(configuration.getDesiredRankingStrategy());
		}
	}

	public RankingExecutor(String strategy)
	{
		ranking = chooseRankingStrategy(strategy);
	}

	public void executeRanking(List<ValidatorEntry> list)
	{
		ranking.createRanking(list);
	}

	private Ranking chooseRankingStrategy(String strategy)
	{
		switch (strategy)
		{
		case "curved":
			return new CurvedRanking();
		case "linear":
			return new LinearRanking();
		case "binary":
			return new BinaryRanking();
		default:
			return new LinearRanking();
		}
	}
}
