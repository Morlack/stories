package org.craftsmenlabs.stories.api.models;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ValidatorEntry {
    private Issue issue;
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
}