package org.craftsmenlabs.stories.spike.isolator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.spike.isolator.model.JiraBacklog;
import org.craftsmenlabs.stories.spike.isolator.model.JiraJsonIssue;

import java.io.IOException;
import java.util.List;

public class JiraJsonParser {

    public static List<JiraJsonIssue> getIssues(String input){
        String s = null;
        List<JiraJsonIssue> jiraJsonIssues = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            JiraBacklog jiraBacklog = mapper.readValue(input, JiraBacklog.class);
            jiraJsonIssues = jiraBacklog.getJiraJsonIssues();
        } catch(JsonParseException e){
            e.printStackTrace();
        } catch(JsonMappingException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return jiraJsonIssues;
    }



}
