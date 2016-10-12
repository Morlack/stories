package org.craftsmenlabs.stories.spikes;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.testutil.RetrieveStoryTestData;
import org.junit.Test;
import mockit.Tested;

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
		List<ValidatorEntry> validatorEntries = storyValidator.convertToValidatorEntries(testData);
		assertThat(validatorEntries.size()).isEqualTo(3);
	}

	@Test
	public void testStoryValidatorOnRealData() throws Exception
	{
		RetrieveStoryTestData testDataGenerator = new RetrieveStoryTestData();
		StoryValidator storyValidator = new StoryValidator();

		float ranking = storyValidator.rankStories(storyValidator.convertToValidatorEntries(testDataGenerator.getTestDataFromResource()));
		assertThat(ranking).isEqualTo(0.825f);

	}
}
