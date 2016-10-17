package org.craftsmenlabs.stories.spike.isolator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JiraCSVIssueDTO {
    private String key;
    private String description;
    private String rank;
}