package integrationtest;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.JiraExportParser;
import org.craftsmenlabs.stories.spike.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.spike.isolator.model.JiraCSVIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {

    @Test
    public void runIntegrationTest(){
        String testData = RetrieveTestData.getExportedTestDataFromResource();
        Issue testResult = RetrieveTestData.getTestIssueFromResource();

        List<JiraCSVIssueDTO> jiraIssues = JiraExportParser.getIssues(testData);

        SentenceSplitter sentenceSplitter = new SentenceSplitter();
        Issue issue1 = sentenceSplitter.splitSentence(jiraIssues.get(0));
        Issue issue2 = sentenceSplitter.splitSentence(jiraIssues.get(1));

        assertEquals(testResult, issue1);
        assertEquals(testResult, issue2);
    }
}
