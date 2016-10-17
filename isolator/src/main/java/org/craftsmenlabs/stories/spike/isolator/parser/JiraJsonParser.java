package org.craftsmenlabs.stories.spike.isolator.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.spike.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.spike.isolator.model.JiraBacklog;
import org.craftsmenlabs.stories.spike.isolator.model.JiraJsonIssue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JiraJsonParser extends FileParser{


    public List<JiraJsonIssue> getJiraJsonIssues(String input){
        List<JiraJsonIssue> jiraJsonIssues = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JiraBacklog jiraBacklog = mapper.readValue(input, JiraBacklog.class);
            jiraJsonIssues = jiraBacklog.getJiraJsonIssues();
        } catch(JsonParseException | JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jiraJsonIssues;
    }

    public List<Issue> getIssues(File file){
        List<Issue> issues = null;

        try {
            String input = readFileAsString(file.getAbsolutePath());
            issues = getIssues(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return issues;
    }


    public List<Issue> getIssues(String input){
        List<JiraJsonIssue> jiraJsonIssues = getJiraJsonIssues(input);

        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        return jiraJsonIssues.stream()
                .map(jiraJsonIssue -> {
                    Issue issue = sentenceSplitter.splitSentence(jiraJsonIssue.getFields().getDescription());
                    issue.setRank(jiraJsonIssue.getFields().getRank());

                    return issue;
            }).collect(Collectors.toList());
    }

}
