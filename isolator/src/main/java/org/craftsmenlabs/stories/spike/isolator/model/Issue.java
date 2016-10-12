package org.craftsmenlabs.stories.spike.isolator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Issue {
    private String rank;
    private Userstory userstory;
    private AcceptanceCriteria acceptanceCriteria;
}
