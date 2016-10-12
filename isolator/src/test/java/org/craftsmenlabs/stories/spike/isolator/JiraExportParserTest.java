package org.craftsmenlabs.stories.spike.isolator;

import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JiraExportParserTest {

    String testData = RetrieveTestData.getExportedTestDataFromResource();
    JiraIssueDTO jiraIssue = RetrieveTestData.getExportedTestResultFromResource();

    @Test
    public void getIssuesTest(){
        assertEquals(Arrays.asList(jiraIssue), JiraExportParser.getIssues(testData));
    }
}