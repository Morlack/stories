package org.craftsmenlabs.stories.spikes.ranking;

import java.util.List;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;

public class CurvedRanking implements Ranking
{

	public static final int SMOOTH_CURVE = 2;

	public float createRanking(List<ValidatorEntry> entries)
	{
		if (entries == null || entries.size() == 0)
		{
			return 0.0f;
		}
		float scoredPoints = 0f;
		float couldHaveScored = 0f;
		for (int i = 0; i < entries.size(); i++)
		{
			float curvedQuotient = curvedQuotient(i, entries.size());
			couldHaveScored += curvedQuotient;
			scoredPoints += entries.get(i).getPointsValuation() * curvedQuotient;
		}
		return scoredPoints / couldHaveScored;
	}

	public float curvedQuotient(float position, float amountOfItems)
	{
		float part = position / amountOfItems;
		return 1 - (float)(Math.pow(part, SMOOTH_CURVE));
	}
}
