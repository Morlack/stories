package org.craftsmenlabs.stories.plugin.filereader.config.validation;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.validation.types.FillableValidatorConfig;

@Data
public class EpicValidatorConfig extends FillableValidatorConfig {
    public ValidationConfig.EpicValidatorEntry convert() {
        ValidationConfig.EpicValidatorEntry epic = new ValidationConfig.EpicValidatorEntry();
        super.complement(epic);
        return epic;
    }
}
