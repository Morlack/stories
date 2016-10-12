package org.craftsmenlabs.stories.spike.isolator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;
import mockit.*;
import opennlp.tools.sentdetect.SentenceDetectorME;

public class SentenceSplitterTest{

	@Injectable JiraIssueDTO input;

	@Mocked SentenceDetectorME sentenceDetector;

	@Tested
	SentenceSplitter sentenceSplitter;

	@Test
	public void splitSentenceTest() throws Exception
	{
		new Expectations()
		{{
			input.getDescription();
			result = "";

			input.getRank();
			result = "2";

			sentenceDetector.sentDetect(anyString);
			result = new String[] { "As a", "Given that" };
		}};

		Issue issue = sentenceSplitter.splitSentence(input);
		assertThat(issue.getUserstory()).isEqualTo("As a");
		assertThat(issue.getAcceptanceCriteria()).isEqualTo("Given that");
		assertThat(issue.getRank()).isEqualTo("2");
	}
}
