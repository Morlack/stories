package org.craftsmenlabs.stories.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ValidatorEntry {
    private Issue issue;
    private int pointsValuation = 0;
    private List<Violation> violations;
}