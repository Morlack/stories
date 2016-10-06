package org.craftsmenlabs.stories.spikes.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatorEntry
{
	private String sourceTextDescription;
	private int backlogPosition;
	private int pointsValuation = 0;
	private Map<String, String> properties;
}
