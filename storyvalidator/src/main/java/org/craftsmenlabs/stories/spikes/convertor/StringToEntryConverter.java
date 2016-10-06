package org.craftsmenlabs.stories.spikes.convertor;

import java.util.ArrayList;
import java.util.List;
import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;

public class StringToEntryConverter
{
	public List<ValidatorEntry> parseEntries(List<String> testData)
	{
		List<ValidatorEntry> list = new ArrayList<>();
		for (int i = 0; i < testData.size(); i++)
		{
			list.add(parseFromString(testData.get(i), i + 1));
		}
		return list;
	}

	public ValidatorEntry parseFromString(String data, int position)
	{
		return new ValidatorEntry(data, position, 0, null);
	}
}
