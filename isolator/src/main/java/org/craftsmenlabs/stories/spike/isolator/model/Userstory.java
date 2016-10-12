package org.craftsmenlabs.stories.spike.isolator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Userstory {
    private String description;
}
