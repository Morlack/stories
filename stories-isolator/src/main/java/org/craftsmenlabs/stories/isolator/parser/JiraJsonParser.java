package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class JiraJsonParser {

    private final Logger logger = LoggerFactory.getLogger(JiraJsonParser.class);
    private FieldMappingConfig fieldMapping;
    private FilterConfig filterConfig;

    public JiraJsonParser(FieldMappingConfig fieldMapping, FilterConfig filterConfig) {
        this.fieldMapping = fieldMapping;
        this.filterConfig = filterConfig;
    }

    public List<Issue> parse(JiraBacklog backlog) {
        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        if(backlog == null) {
            return null;
        }

        return backlog.getJiraJsonIssues().stream()
                .filter(jiraJsonIssue -> jiraJsonIssue.getFields().getStatus().getStatusCategory().getName().equals(filterConfig.getStatus()))
                .map(jiraJsonIssue -> {
                    Issue issue;

                    if (hasNoValidDescription(jiraJsonIssue)) {
                        issue = new Issue();
                    } else {
                        issue = sentenceSplitter.splitSentence(jiraJsonIssue.getFields().getDescription());
                    }

                    issue.setSummary(jiraJsonIssue.getFields().getSummary());
                    issue.setKey(jiraJsonIssue.getKey());
                    issue.setIssueType(jiraJsonIssue.getFields().getIssuetype().getName());

                    String criteriaKey = fieldMapping.getIssue().getAcceptenceCriteria();
                    if(StringUtils.isNotEmpty(criteriaKey) && jiraJsonIssue.getFields().getAdditionalProperties().containsKey(criteriaKey)) {
                        // We should get the acceptence Criteria from this field
                        issue.setAcceptanceCriteria((String) jiraJsonIssue.getFields().getAdditionalProperties().get(criteriaKey));
                    }

                    Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();
                    Map<String, String> stringProps = new HashMap<>();
                    for (Entry<String, Object> entries : additionalProps.entrySet()) {
                        if (entries.getValue() != null) {
                            stringProps.put(entries.getKey(), entries.getValue().toString());
                        } else {
                            stringProps.put(entries.getKey(), "");
                        }
                    }

                    String rank = stringProps.get(fieldMapping.getIssue().getRank());
                    if (rank == null || StringUtils.isEmpty(rank)) {
                        throw new StoriesException(
                                "The rank field mapping was not defined in your application yaml or parameters. " +
                                "Is the field mapping configured correctly?");
                    }
                    issue.setRank(rank);

                    float estimation = 0f;
                    try {
                        if (stringProps.get(fieldMapping.getIssue().getEstimation()) != null) {
                            estimation = Float.parseFloat(stringProps.get(fieldMapping.getIssue().getEstimation()));
                        }
                    } catch (NumberFormatException nfe) {
                        logger.warn("Parsing of estimation to float failed. By default set to 0.0");
                    }
                    issue.setEstimation(estimation);

                    return issue;
                }).collect(Collectors.toList());
    }

    public boolean hasNoValidDescription(JiraJsonIssue jiraJsonIssue) {
        return jiraJsonIssue.getFields().getDescription() == null || jiraJsonIssue.getFields().getDescription().isEmpty();
    }
}
