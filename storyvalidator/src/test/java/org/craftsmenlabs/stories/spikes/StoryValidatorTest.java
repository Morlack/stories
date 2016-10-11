package org.craftsmenlabs.stories.spikes;

import mockit.Tested;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StoryValidatorTest
{
	List<String> testData = Arrays.asList("As a tester \n I", "As a developer", "As a developer");

	@Tested
	StoryValidator storyValidator;

	@Test
	public void retrieveRankingTest() throws Exception
	{
		storyValidator.retrieveRanking(testData);
	}
}
