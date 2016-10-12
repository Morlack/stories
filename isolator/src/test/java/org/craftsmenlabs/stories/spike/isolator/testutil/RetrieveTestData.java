package org.craftsmenlabs.stories.spike.isolator.testutil;

import org.craftsmenlabs.stories.spike.isolator.model.AcceptanceCriteria;
import org.craftsmenlabs.stories.spike.isolator.model.Issue;
import org.craftsmenlabs.stories.spike.isolator.model.JiraIssueDTO;
import org.craftsmenlabs.stories.spike.isolator.model.Userstory;

import java.util.ArrayList;
import java.util.List;

public class RetrieveTestData
{

	public static List<JiraIssueDTO> getTestDataFromResource(String resourceName)
	{
		List<JiraIssueDTO> testItems = new ArrayList<>();
		testItems.add(
				JiraIssueDTO.builder().description(
				""
			+ "As a super office user \n"
			+ "I would like to be informed about the alarms in my user \n"
			+ "so I can have the most preferred alarm on top. \n"
			+ "\n"
			+ "*Acceptance criteria* \n"
			+ "Given I select an alarm \n"
			+ "When a profile (or profiles) contain an \n"
			+ "Then the system displays a page with the alarm. \n"
			+ "\n"
			+ "*Scope* \n"
			+ "* Add weight (eg 0) when profile settings is not used in a alarm "
		).build());

		testItems.add(
			JiraIssueDTO.builder().description(
			   "As a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
			+ "\n"
			+ "*Acceptance criteria* \n"
			+ "Given I am on the backend page\n"
			+ "When I select gather statistics\n"
			+ "Then the system displays a pages with the alarms.\n"
			+ "\n"
			+ "*Scope* \n"
			+ "* Use rest services for everything"
		).build());
		return testItems;
	}

	public static List<Issue> getTestResultFromResource() {
		List<Issue> testItems = new ArrayList<>();

		Issue issue = Issue.builder().
				userstory(new Userstory(
				"As a super office user \n"
				+ "I would like to be informed about the alarms in my user \n"
				+ "so I can have the most preferred alarm on top."
		)).acceptanceCriteria(new AcceptanceCriteria(
				  "*Acceptance criteria* \n"
				+ "Given I select an alarm \n"
				+ "When a profile (or profiles) contain an \n"
				+ "Then the system displays a page with the alarm."
		)).build();

		testItems.add(issue);
		return testItems;
	}


	public static String getExportedTestDataFromResource(){
		return "Project;Key;Summary;Issue Type;Status;Priority;Resolution;Assignee;Reporter;Creator;Created;Last Viewed;Updated;Resolved;Affects Version/s;Fix Version/s;Component/s;Due Date;Votes;Watchers;Images;Original Estimate;Remaining Estimate;Time Spent;Work Ratio;Sub-Tasks;Linked Issues;Environment;Description;Security Level;Progress;_ Progress;_ Time Spent;_ Remaining Estimate;_ Original Estimate;Labels;Release Notes;Fixed in build;Tested version;Severity;Sprint;Epic Link;Rank;Rank (Obsolete);Flagged;Epic/Theme;Story Points;Business Value\n" +
				"Gareth;Gareth-2001;Implement authentication service;Story;Resolved;High;Fixed;TestUser;TestUser;TestUser;11/10/16 12:46;28/09/16 13:19;07/10/16 11:44;07/10/16 11:44;;;Authentication & Authorization;;0;1;;;0;1560;;;;;\"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms.\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything\"\";;100%;100%;300;0;;;n/a;6.1.0build01;;;Sprint 1;Service;0|zgbujw:;9,22E+18;;;2;\n" +
				"Generated at Tue Oct 11 11:04:10 CEST 2016 by TestUser using JIRA;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
	}

	public static JiraIssueDTO getExportedTestResultFromResource(){
		return JiraIssueDTO.builder()
				.key("Gareth-2001")
				.description(
				"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything")
				.build();
	}


	public static Issue getTestIssueFromResource(){
		return Issue.builder()
				.userstory(new Userstory(
					"As a super office user\n" +
					"I would like to be informed about the alarms in my user\n" +
					"so I can have the most preferred alarm on top."
				))
				.acceptanceCriteria(new AcceptanceCriteria(
					"*Acceptance criteria*\n" +
					"Given I select an alarm\n" +
					"When a profile (or profiles) contain an\n" +
					"Then the system displays a page with the alarm."
				))
				.build();
	}
}
