package org.craftsmenlabs.stories.spike.isolator;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SentenceSplitterTest {

    List<JiraIssueDTO> testData = RetrieveTestData.getTestDataFromResource(null);
    List<Issue> testResult = RetrieveTestData.getTestResultFromResource();

    @Test
    public void SentenceSplitterTest() {
        assertEquals(new SentenceSplitter().splitSentence(testData.get(0)), testResult.get(0));
    }
}
