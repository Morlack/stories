package org.craftsmenlabs.stories.spikes.scoring;

import static org.assertj.core.api.Assertions.assertThat;
import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;
import org.junit.Test;
import mockit.*;

public class StoryScorerTest
{

	@Tested
	StoryScorer storyScorer;

	@Test
	public void testPerformScorer(@Injectable ValidatorEntry entry) throws Exception
	{
		new Expectations()
		{{
			entry.getSourceTextDescription();
			result = "As a super office user \n"
				+ "I would like to be informed about the alarms in my user \\n\"\n"
				+ "so I can have the most preferred alarm on top.";
		}};

		float score = storyScorer.performScorer(entry);
		assertThat(score).isEqualTo(1.0f);
	}

	@Test
	public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable ValidatorEntry entry) throws Exception
	{
		new Expectations()
		{{
			entry.getSourceTextDescription();
			result = "";
		}};

		float score = storyScorer.performScorer(entry);
		assertThat(score).isEqualTo(0.0f);
	}

	@Test
	public void testPerformScorer_ReturnsNullOnEmpty(@Injectable ValidatorEntry entry) throws Exception
	{
		new Expectations()
		{{
			entry.getSourceTextDescription();
			result = null;
		}};

		float score = storyScorer.performScorer(entry);
		assertThat(score).isEqualTo(0.0f);
	}
}
