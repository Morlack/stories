package org.craftsmenlabs.stories.spikes.rating;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.spikes.configuration.Configuration;

/**
 *
 */
public class RatingExecutor
{
	Configuration configuration = new Configuration();

	public Rating retrieveRating(float ranking)
	{
		if (ranking >= configuration.getDesiredMiniumStableRanking())
		{
			return Rating.SUCCES;
		}
		else
		{
			return Rating.FAIL;
		}
	}
}
