package org.craftsmenlabs.stories.spikes;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;
import org.craftsmenlabs.stories.spikes.scoring.Violation;
import org.junit.Test;
import mockit.*;

public class StoryValidatorTest
{
	List<String> testData = Arrays.asList("As a tester \n I", "As a developer", "As a developer");

	//	@Injectable
	//	List<ValidatorEntry> testEntries;
	//
	//	@Injectable
	//	ValidatorEntry validatorEntry;
	//
	//	@Injectable
	//	StringToEntryConverter entryConverter;

	@Tested
	StoryValidator storyValidator;

	@Test
	public void testName() throws Exception
	{
		storyValidator.retrieveRanking(testData);

	}

	@Test
	public void testRankingWorks(@Injectable List<ValidatorEntry> list, @Injectable Iterator iterator) throws Exception
	{
		ValidatorEntry validatorEntry = new ValidatorEntry("", 1, 100, null, new ArrayList<Violation>());

		List<ValidatorEntry> testEntries = Arrays.asList(validatorEntry);

		new Expectations()
		{
			{
				list.size();
				result = 4;

				list.iterator();
				result = testEntries.iterator();
			}
		};
		float ranking = storyValidator.createRanking(list);
		assertThat(ranking).isEqualTo(10.0f);
	}
}
