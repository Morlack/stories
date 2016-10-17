package org.craftsmenlabs.stories.spikes;

import org.craftsmenlabs.stories.spikes.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App
{
	private final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args)
	{
		StoryValidator storyValidator = new StoryValidator();

		float ranking = 0f;//storyValidator.retrieveRanking(testItems);
		System.out.println("Ranking:" + (ranking * 100) + "%");

		Configuration configuration = new Configuration();

	}
}
