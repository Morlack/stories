package org.craftsmenlabs.stories.spike.isolator;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import opennlp.tools.sentdetect.SentenceDetectorME;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraCSVIssueDTO;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SentenceSplitterTest{

	@Injectable
    JiraCSVIssueDTO input;

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
