package org.craftsmenlabs.stories.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Issue {
    private String rank;
    private String userstory;
    private String acceptanceCriteria;
    private Map<String, String> properties;
}