package org.craftsmenlabs.stories.api.models;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Issue {
    private String rank;
    private String userstory;
    private String acceptanceCriteria;
    private Map<String, String> properties;
}
