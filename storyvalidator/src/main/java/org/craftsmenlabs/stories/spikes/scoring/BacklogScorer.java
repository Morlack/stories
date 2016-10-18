package org.craftsmenlabs.stories.spikes.scoring;

import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.spikes.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BacklogScorer {

    public static BacklogValidatorEntry performScorer(Backlog backlog, Ranking ranking) {
        List<IssueValidatorEntry> issueValidatorEntries =
                backlog.getIssues().stream()
                        .map(IssueScorer::performScorer)
                        .collect(Collectors.toList());

        BacklogValidatorEntry backlogValidatorEntry =
                BacklogValidatorEntry.builder()
                        .backlog(backlog)
                        .violations(new ArrayList<>())
                        .issueValidatorEntries(issueValidatorEntries)
                        .build();

        Float points = ranking.createRanking(backlogValidatorEntry);
        backlogValidatorEntry.setPointsValuation(points);

        return backlogValidatorEntry;
    }
}
