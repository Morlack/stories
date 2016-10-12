package org.craftsmenlabs.stories.spike.isolator;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.craftsmenlabs.stories.spike.isolator.model.AcceptanceCriteria;
import org.craftsmenlabs.stories.spike.isolator.model.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.model.Userstory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Isolator can map a string to a card
 */
public class SentenceSplitter
{
    private final String SENTENCEMODEL = "en-sent.bin";
    private SentenceDetectorME sentenceDetector;

    public SentenceSplitter() {
        loadSentenceDetector();
    }

    public void loadSentenceDetector()
    {
        InputStream modelIn = null;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(SENTENCEMODEL).getFile());

        try
        {
            modelIn = new FileInputStream(file);
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
                .userstory(new Userstory(userstory))
                .acceptanceCriteria(new AcceptanceCriteria(acceptanceCriteria))
                .build();
    }
}