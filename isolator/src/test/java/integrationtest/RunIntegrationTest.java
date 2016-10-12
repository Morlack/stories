package integrationtest;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.JiraExportParser;
import org.craftsmenlabs.stories.spike.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {

    @Test
    public void runIntegrationTest(){
        String testData = RetrieveTestData.getExportedTestDataFromResource();
        Issue testResult = RetrieveTestData.getTestIssueFromResource();

        List<JiraIssueDTO> jiraIssues = JiraExportParser.getIssues(testData);

        SentenceSplitter sentenceSplitter = new SentenceSplitter();
        Issue issue = sentenceSplitter.splitSentence(jiraIssues.get(0));

        assertEquals(testResult, issue);
    }
}
