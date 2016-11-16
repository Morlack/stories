package demo.data.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Files;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.FieldMappingConfigCopy;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.plugin.filereader.ApplicationConfig;
import org.craftsmenlabs.stories.plugin.filereader.BootApp;
import org.craftsmenlabs.stories.plugin.filereader.FieldMappingConfig;
import org.craftsmenlabs.stories.plugin.filereader.ValidationConfig;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a make shift jira file importer for demo purposes,
 * not a test
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootApp.class})
@TestPropertySource(locations = "classpath:application-test.yml")
public class importJiraJson {

    @Autowired
    private ConnectivityService dashboardConnectivity;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private ValidationConfig validationConfig;
    @Autowired
    private FieldMappingConfig fieldMappingConfig;


    private ValidationConfigCopy validationConfigCopy;
    private FieldMappingConfigCopy fieldMappingConfigCopy;

    @Test
    public void importData() {
        Files.fileNamesIn(applicationConfig.getInputfile(), false).stream()
                .map(File::new)
                .filter(file -> !file.getName().startsWith("."))
                .forEach(this::importFile);
    }


    //accept file in the format: projectToken_2016-02-13_16-00.json
    public void importFile(File file) {

        String[] split = file.getName().split("_|\\.");
        String projectToken = split[0];
        String date = split[1];
        List<Integer> time = Arrays.stream(split[2].split("-"))
                .map(Integer::parseInt).collect(Collectors.toList());

        LocalDateTime dateTime = LocalDate.parse(date).atTime(time.get(0), time.get(1));

        validationConfigCopy = validationConfig.clone();
        fieldMappingConfigCopy = fieldMappingConfig.clone();

        ObjectMapper mapper = new ObjectMapper();
        JiraBacklog jiraBacklog = null;
        try {
            jiraBacklog = mapper.readValue(file, JiraBacklog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JiraJsonParser jiraJsonParser = new JiraJsonParser(fieldMappingConfigCopy, applicationConfig.getStatus());
        List<JiraJsonIssue> jiraJsonIssues = jiraBacklog.getJiraJsonIssues();
        List<Issue> issues = jiraJsonParser.getIssues(jiraJsonIssues);

        Backlog backlog = new Backlog();
        backlog.setIssues(issues);

        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfigCopy);

        StoriesRun storiesRun = StoriesRun.builder()
                .projectToken(projectToken)
                .runDateTime(dateTime)
                .backlogValidatorEntry(backlogValidatorEntry)
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .runConfig(validationConfigCopy)
                .build();

        dashboardConnectivity.sendData(storiesRun);
    }
}
