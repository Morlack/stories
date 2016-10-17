package integrationtest;

import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.spike.isolator.testutil.RetrieveTestData;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {

    JiraCSVParser jiraCSVParser = new JiraCSVParser();

    @Ignore("csv temporarily unmaintained")
    @Test
    public void runIntegrationTest(){
        String testData = RetrieveTestData.getExportedTestDataFromResource();
        Issue testResult = RetrieveTestData.getTestIssueFromResource();

        List<Issue> issues = jiraCSVParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
        assertEquals(testResult, issues.get(1));
    }
}
