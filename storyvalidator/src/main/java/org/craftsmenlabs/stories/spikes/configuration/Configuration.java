package org.craftsmenlabs.stories.spikes.configuration;

import io.github.benas.easyproperties.annotations.Property;
import static io.github.benas.easyproperties.PropertiesInjectorBuilder.aNewPropertiesInjector;
import lombok.Data;

@Data
public class Configuration
{
	@Property(source = "configuration.properties", key = "ranking.desiredMiniumStableRanking")
	private int desiredMiniumStableRanking;

	@Property(source = "configuration.properties", key = "ranking.desiredMinimumUnstableRanking")
	private int desiredMinimumUnstableRanking;

	public Configuration(){
		aNewPropertiesInjector().injectProperties(this);
	}
}
