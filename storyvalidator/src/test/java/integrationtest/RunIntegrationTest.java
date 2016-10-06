package integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import org.craftsmenlabs.stories.spikes.StoryValidator;
import org.craftsmenlabs.stories.spikes.testutil.RetrieveStoryTestData;
import org.junit.Test;

public class RunIntegrationTest
{
	RetrieveStoryTestData testDataGenerator = new RetrieveStoryTestData();
	StoryValidator storyValidator = new StoryValidator();

	@Test
	public void testName() throws Exception
	{
		float ranking = storyValidator.retrieveRanking(testDataGenerator.getTestDataFromResource());
		assertThat(ranking).isEqualTo(0.825f);
	}
}
