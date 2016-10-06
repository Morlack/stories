package org.craftsmenlabs.stories.spikes.scoring;

public interface Violation
{
	ViolationType getViolationType();

	String getCause();
}
