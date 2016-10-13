package org.craftsmenlabs.stories.spike.isolator;

import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JiraExportParserTest {

    String testData = RetrieveTestData.getExportedTestDataFromResource();
    List<JiraIssueDTO> jiraIssues = RetrieveTestData.getExportedTestResultFromResource();

    @Test
    public void getIssuesTest(){
        assertEquals(jiraIssues, JiraExportParser.getIssues(testData));
    }
}