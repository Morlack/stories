package org.craftsmenlabs.stories.spikes;

import java.util.ArrayList;
import java.util.List;

public class App
{
	public static void main(String[] args)
	{
		List<String> testItems = new ArrayList<>();
		testItems.add(new String(""
			+ "As a super office user \n"
			+ "I would like to be informed about the alarms in my user \n"
			+ "so I can have the most preferred alarm on top. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
		));
		StoryValidator storyValidator = new StoryValidator();

		float ranking = storyValidator.retrieveRanking(testItems);
		System.out.println("Ranking:" + (ranking * 100) + "%");
	}
}
