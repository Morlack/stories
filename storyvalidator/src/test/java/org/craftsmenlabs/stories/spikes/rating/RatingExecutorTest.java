package org.craftsmenlabs.stories.spikes.rating;

import static org.assertj.core.api.Assertions.assertThat;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.spikes.configuration.Configuration;
import org.junit.Test;
import mockit.*;

/**
 *
 */
public class RatingExecutorTest
{

	@Mocked
	Configuration configuration;
	@Tested
	RatingExecutor ratingExecutor;

	@Test
	public void testRetrieveRatingWhenStable() throws Exception
	{
		new Expectations()
		{{
			configuration.getDesiredMiniumStableRanking();
			result = 80;
		}};
		Rating r = ratingExecutor.retrieveRating(80f);
		assertThat(r).isEqualTo(Rating.SUCCES);
	}

	@Test
	public void testRetrieveRatingWhenFail() throws Exception
	{
		new Expectations()
		{{
			configuration.getDesiredMiniumStableRanking();
			result = 80;
		}};
		Rating r = ratingExecutor.retrieveRating(70f);
		assertThat(r).isEqualTo(Rating.FAIL);
	}

}
