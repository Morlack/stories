package org.craftsmenlabs.stories;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="validation")
public class ValidationConfig {
    private ValidatorEntry backlog;
    private ValidatorEntry issue;
    private ValidatorEntry story;
    private ValidatorEntry criteria;
    private ValidatorEntry estimation;


    @Data
    public static class ValidatorEntry {
        private float ratingtreshold;
    }
}
