package org.craftsmenlabs.stories.spike.isolator.parser;

import org.craftsmenlabs.stories.api.models.Issue;

import java.util.List;

public interface Parser {
    List<Issue> getIssues(String input);
}
