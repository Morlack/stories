package org.craftsmenlabs.stories.spikes.scoring;

import lombok.Value;

@Value
public class StoryViolation implements Violation
{
	ViolationType violationType;
	String cause;
}
