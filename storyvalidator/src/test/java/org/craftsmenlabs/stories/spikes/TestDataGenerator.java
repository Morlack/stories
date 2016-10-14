package org.craftsmenlabs.stories.spikes;

import java.util.*;
import org.craftsmenlabs.stories.api.models.Issue;
import org.craftsmenlabs.stories.api.models.ValidatorEntry;

/**
 *
 */
public class TestDataGenerator
{
	List<String> stories = Arrays.asList("As a tester \nI want to \nso i can do it", "As a developer \nI want", "As a developer");
	List<String> accCrits = Arrays
		.asList("Given ther \n Whennt to \n Thencan do it", "Given ther \n Whennt to \n Thencan do it", "Given ther \n do it");

	public List<Issue> getIssues()
	{
		List<Issue> testData = new ArrayList<>();
		for (int i = 0; i < stories.size(); i++)
		{
			testData.add(Issue.builder().userstory(stories.get(i)).acceptanceCriteria(accCrits.get(i)).build());
		}
		return testData;
	}

	public List<ValidatorEntry> getGoodValidatorItems(int amount)
	{
		List<ValidatorEntry> testData = new ArrayList<>();
		for (int i = 0; i < amount; i++)
		{
			testData.add(new ValidatorEntry(getIssues().get(0), 0.0f, new ArrayList<>()));
		}
		return testData;
	}

	public List<ValidatorEntry> getMixedValidatorItems(int amount)
	{
		List<ValidatorEntry> testData = new ArrayList<>();
		for (int i = 0; i < amount; i++)
		{
			testData.add(new ValidatorEntry(getIssues().get(i % 3), 0.0f, new ArrayList<>()));
		}
		return testData;
	}
}
