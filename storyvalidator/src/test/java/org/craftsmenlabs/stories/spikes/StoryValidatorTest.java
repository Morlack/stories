package org.craftsmenlabs.stories.spikes;

import mockit.Tested;
import org.craftsmenlabs.stories.api.models.Issue;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoryValidatorTest
{
	List<String> roles = Arrays.asList("As a tester \n I", "As a developer", "As a developer");
	List<Issue> testData = roles.stream()
            .map(s -> Issue.builder().userstory(s).build())
            .collect(Collectors.toList());

	@Tested
    StoryValidator storyValidator;

	@Test
	public void retrieveRankingTest() throws Exception
	{
		storyValidator.retrieveRanking(testData);
	}
}
