package org.craftsmenlabs.stories.spike.isolator;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.spike.isolator.model.JiraJsonIssue;
import org.junit.Test;

public class JiraJsonParserTest {

    @Test
    public void getIssuesTest(){
        File file = new File("/Users/youritjang/IdeaProjects/stories/temp/issuesfile-NEVER-SUBMIT-TO-GITHUB.json");
        try {
            String input = FileUtils.readFileToString(file, Charset.defaultCharset());
            List<JiraJsonIssue> jiraJsonIssues = JiraJsonParser.getIssues(input);

            System.out.println("Size: " + jiraJsonIssues.size());

//            jiraJsonIssues.stream().map(jiraJsonIssue -> jiraJsonIssue.get)

            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
