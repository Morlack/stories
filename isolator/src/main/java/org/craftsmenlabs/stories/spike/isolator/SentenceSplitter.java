package org.craftsmenlabs.stories.spike.isolator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * The Isolator can map a string to a card
 */
public class SentenceSplitter
{
	private final String SENTENCEMODEL = "en-sent.bin";
	private SentenceDetectorME sentenceDetector;

	public SentenceSplitter()
	{
		loadSentenceDetector();
	}

	public void loadSentenceDetector()
	{
		InputStream modelIn = null;

		modelIn = SentenceSplitter.class.getClassLoader().getResourceAsStream(SENTENCEMODEL);
		try
		{
			SentenceModel model = new SentenceModel(modelIn);
			sentenceDetector = new SentenceDetectorME(model);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (modelIn != null)
			{
				try
				{
					modelIn.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public Issue splitSentence(JiraIssueDTO input)
	{
		final String[] sentences = sentenceDetector.sentDetect(input.getDescription());

		//currently:
		// - first sentence => user story
		// - second sentence => acceptance criteria
		// by convention
		final String userstory = sentences[0];
		final String acceptanceCriteria = sentences[1];

		return Issue.builder()
			.userstory(userstory)
			.acceptanceCriteria(acceptanceCriteria)
			.rank(input.getRank())
			.build();
	}
}