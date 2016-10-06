package org.craftsmenlabs.stories.spike.isolator.testutil;

import java.util.ArrayList;
import java.util.List;

public class RetrieveTestData
{

	public List<String> getTestDataFromResource(String resourceName)
	{

		List<String> testItems = new ArrayList<>();
		testItems.add(""
			+ "As a super office user \n"
			+ "I would like to be informed about the alarms in my user \n"
			+ "so I can have the most preferred alarm on top. \n"
			+ "\n"
			+ "*Acceptance criteria* \n"
			+ "Given I select an alarm \n"
			+ "When a profile (or profiles) contain an \n"
			+ "Then the system displays a page with the alarm \n"
			+ "\n"
			+ "*Scope* \n"
			+ "* Add weight (eg 0) when profile settings is not used in a alarm"
		);

		testItems.add(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
			+ "\n"
			+ "*Acceptance criteria* \n"
			+ "Given I am on the backend page\n"
			+ "When I select gather statistics\n"
			+ "Then the system displays a pages with the alarms\n"
			+ "\n"
			+ "*Scope* \n"
			+ "* Use rest services for everything"
		);
		return testItems;
	}
}
