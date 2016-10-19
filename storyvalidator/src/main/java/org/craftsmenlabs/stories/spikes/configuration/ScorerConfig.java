package org.craftsmenlabs.stories.spikes.configuration;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:ScorerConfig.properties",
        "file:~/ScorerConfig.properties"})
public interface ScorerConfig extends Config{
    @Key("backlog.rating.threshold")
    @DefaultValue("60f")
    float backlogRatingThreshold();

    @Key("issue.rating.threshold")
    @DefaultValue("0.6f")
    float issueRatingThreshold();

    @Key("story.rating.threshold")
    @DefaultValue("0.6f")
    float storyRatingThreshold();

    @Key("criteria.rating.threshold")
    @DefaultValue("0.6f")
    float criteriaRatingThreshold();

    @Key("estimation.rating.threshold")
    @DefaultValue("0.6f")
    float estimationRatingThreshold();
}
