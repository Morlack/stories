package org.craftsmenlabs.stories.spikes.configuration;

import static io.github.benas.easyproperties.PropertiesInjectorBuilder.aNewPropertiesInjector;
import io.github.benas.easyproperties.annotations.Property;
import lombok.Data;

@Data
public class Configuration
{
	@Property(source = "configuration.properties", key = "ranking.desiredMiniumStableRanking")
	private int desiredMiniumStableRanking;

	@Property(source = "configuration.properties", key = "ranking.desiredMinimumUnstableRanking")
	private int desiredMinimumUnstableRanking;

	@Property(source = "configuration.properties", key = "ranking.desiredRankingStrategy")
	private String desiredRankingStrategy;

	public Configuration(){
		aNewPropertiesInjector().injectProperties(this);
	}
}
