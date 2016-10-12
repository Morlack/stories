package org.craftsmenlabs.stories.spike.isolator.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Issue {
    private Userstory userstory;
    private AcceptanceCriteria acceptanceCriteria;
}
