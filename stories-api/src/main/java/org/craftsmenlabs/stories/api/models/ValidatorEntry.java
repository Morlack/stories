package org.craftsmenlabs.stories.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidatorEntry {
    private String sourceTextDescription; //via issue
    private int backlogPosition; //via issue
    private Map<String, String> properties;  //via issue

    private int pointsValuation = 0;
    private List<Violation> violations;
}
