package org.craftsmenlabs.stories.spike.isolator;

import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SentenceSplitterTest {

    List<String> testData = RetrieveTestData.getTestDataFromResource(null);
    List<List<String>> testResult = RetrieveTestData.getTestResultFromResource();

    @Test
    public void SentenceSplitterTest() {
        assertEquals(new SentenceSplitter().detectSentence(testData.get(0)).get().collect(Collectors.toList()), testResult.get(0));
    }
}
