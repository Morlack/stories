package org.craftsmenlabs.stories.spike.isolator;

import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraJsonIssue;
import org.craftsmenlabs.stories.spike.isolator.parser.JiraJsonParser;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class JiraJsonParserTest {

    JiraJsonParser jiraJsonParser = new JiraJsonParser();

    @Ignore("Test to be implemented")
    @Test
    public void getJiraJsonIssuesTest() {
        File file = new File("/Users/youritjang/IdeaProjects/stories/temp/issuesfile.json");
        try {
            String input = FileUtils.readFileToString(file, Charset.defaultCharset());
            List<JiraJsonIssue> jiraJsonIssues = jiraJsonParser.getJiraJsonIssues(input);

            jiraJsonIssues.stream()
                    .filter(jiraJsonIssue -> jiraJsonIssue.getFields().getIssuetype().name.equals("Story"))
                    .forEach(jiraJsonIssue -> System.out.println(jiraJsonIssue));

            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Ignore("Test to be implemented")
    @Test
    public void getIssuesTest() {
        File file = new File("/Users/youritjang/IdeaProjects/stories/temp/issuesfile.json");
        try {
            String input = FileUtils.readFileToString(file, Charset.defaultCharset());

            jiraJsonParser.getIssues(input)
                    .stream()
                    .sorted(Comparator.comparing(Issue::getRank))
                    .filter(issue -> issue.getUserstory() != null && !issue.getUserstory().isEmpty())
                    .forEach(issue -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Key= ");
                        sb.append(issue.getKey());
                        sb.append(", ");
                        sb.append("Rank= ");
                        sb.append(issue.getRank());
                        sb.append(", ");
                        sb.append("Est= ");
                        sb.append(issue.getEstimation());
                        sb.append(" ");
                        sb.append("\n");
                        sb.append(issue.getUserstory());
                        System.out.println(sb.toString());
            });

            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
