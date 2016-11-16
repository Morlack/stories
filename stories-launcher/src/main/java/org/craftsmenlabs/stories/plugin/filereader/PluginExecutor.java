package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.craftsmenlabs.stories.connectivity.service.enterprise.DashboardConnectivityService;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.importer.TrelloAPIImporter;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.Parser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.plugin.filereader.config.*;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.reporter.ConsoleReporter;
import org.craftsmenlabs.stories.reporter.JsonFileReporter;
import org.craftsmenlabs.stories.reporter.Reporter;
import org.craftsmenlabs.stories.reporter.SummaryConsoleReporter;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PluginExecutor {
	private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
	public ValidationConfig validationConfig;
	public FieldMappingConfig fieldMappingConfig;

	@Autowired
	private ConnectivityService dashboardConnectivity;
	@Autowired
	private SpringReportConfig springReportConfig;
	@Autowired
	private SpringSourceConfig springSourceConfig;
	@Autowired
	private SpringFilterConfig springFilterConfig;
	@Autowired
	private SpringValidationConfig springValidationConfig;
	@Autowired
	private SpringFieldMappingConfig springFieldMappingConfig;

	public Rating startApplication() {
		logger.info("Starting stories plugin.");

		// Prepare configs
		validationConfig = springValidationConfig.convert();
		fieldMappingConfig = springFieldMappingConfig.convert();
		// Validate configs
		this.springReportConfig.validate();
		this.springSourceConfig.validate();

		// Import the data
		Importer importer = getImporter(springSourceConfig.getEnabled());
		String data = importer.getDataAsString();


		// Parse and filter the issues
		Parser parser = getParser(springSourceConfig.getEnabled());
		List<Issue> issues = parser.getIssues(data).stream()
				.filter(issue -> issue.getUserstory() != null)
				.filter(issue -> !issue.getUserstory().isEmpty())
				.collect(Collectors.toList());

		Backlog backlog = new Backlog();
		backlog.setIssues(issues);

		// Perform the backlog validation
		BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig);

		for(Reporter reporter : this.getReporters()) {
			reporter.report(backlogValidatorEntry);
		}

		// Dashboard report?
        StoriesRun storiesRun = StoriesRun.builder()
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .backlogValidatorEntry(backlogValidatorEntry)
				.runConfig(validationConfig)
				.runDateTime(LocalDateTime.now())
				.build();

        dashboardConnectivity.sendData(storiesRun);

		//Multiply by 100%
		return backlogValidatorEntry.getRating();
	}

	/**
	 * Finds and initializes the right importer for the source enabled setting.
	 *
	 * @param enabled source
	 * @return Importer importer
	 */
	public Importer getImporter(String enabled) {
		switch(enabled) {
			case "jira":
				SpringSourceConfig.JiraConfig jiraConfig = springSourceConfig.getJira();
				logger.info("Using JiraAPIImporter for import." + jiraConfig.getUrl());
				return new JiraAPIImporter(jiraConfig.getUrl(),jiraConfig.getProjectKey(), jiraConfig.getAuthKey(), springFilterConfig.getStatus());
			case "trello":
				logger.info("Using TrelloAPIImporter for import.");
				SpringSourceConfig.TrelloConfig trelloConfig = springSourceConfig.getTrello();
				return new TrelloAPIImporter(trelloConfig.getUrl(), trelloConfig.getProjectKey(), trelloConfig.getAuthKey(), trelloConfig.getToken());
			default:
				throw new StoriesException(StoriesException.ERR_SOURCE_ENABLED_MISSING);
		}
	}

	/**
	 * Set the parser based on the data format:
	 * jirajson, trellojson, etc.
	 *
	 * @param enabled enabled source
	 */
	private Parser getParser(String enabled) {
		switch (enabled) {
			case ("jira"):
				return new JiraJsonParser(fieldMappingConfig, springFilterConfig.getStatus());
			case ("trello"):
				return new TrelloJsonParser();
			default:
				throw new StoriesException(StoriesException.ERR_SOURCE_ENABLED_MISSING);
		}
	}

	/**
	 * Finds out which reporters should be used for this run
	 * @return List of reporters
	 */
	private List<Reporter> getReporters() {
		List<Reporter> reporters = Arrays.asList(
				new ConsoleReporter(this.validationConfig),
				new SummaryConsoleReporter()
		);

		// In the future, depending on configuration, we can add additional reports here.
		if(this.springReportConfig.getFile().isEnabled()) {
			reporters.add(new JsonFileReporter(new File(this.springReportConfig.getFile().getLocation())));
		}

		return reporters;
	}
}
