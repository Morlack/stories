package org.craftsmenlabs.stories.spike.isolator.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.spike.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.spike.isolator.model.JiraBacklog;
import org.craftsmenlabs.stories.spike.isolator.model.JiraJsonIssue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JiraJsonParser implements Parser {


    public List<Issue> getIssues(String input){
        List<JiraJsonIssue> jiraJsonIssues = getJiraJsonIssues(input);

        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        return jiraJsonIssues.stream()
                .map(jiraJsonIssue -> {
                    Issue issue = sentenceSplitter.splitSentence(jiraJsonIssue.getFields().getDescription());
                    issue.setKey(jiraJsonIssue.getKey());
                    issue.setRank(jiraJsonIssue.getFields().getRank());
                    issue.setEstimation(jiraJsonIssue.getFields().getEstimation());

                    return issue;
            }).collect(Collectors.toList());
    }

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
}
