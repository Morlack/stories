package org.craftsmenlabs.stories.spike.isolator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JiraIssueDTO {
    private String key;
    private String description;
}
