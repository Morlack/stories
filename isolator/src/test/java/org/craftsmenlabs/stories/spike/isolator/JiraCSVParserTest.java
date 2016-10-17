package org.craftsmenlabs.stories.spike.isolator;

import org.craftsmenlabs.stories.spike.isolator.model.JiraCSVIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JiraCSVParserTest {

    String testData = RetrieveTestData.getExportedTestDataFromResource();
    List<JiraCSVIssueDTO> jiraIssues = RetrieveTestData.getExportedTestResultFromResource();

    @Ignore("Test is ignored, because of deprication")
    @Test
    public void getIssuesTest(){
        JiraCSVParser jiraCSVParser = new JiraCSVParser();
        assertEquals(jiraIssues, jiraCSVParser.getIssues(testData));
    }
}