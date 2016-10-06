package org.craftsmenlabs.stories.spikes.model;

import java.util.List;
import java.util.Map;
import org.craftsmenlabs.stories.spikes.scoring.Violation;
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
	private List<Violation> violations;
}
