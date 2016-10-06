package org.craftsmenlabs.stories.spikes.convertor;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.craftsmenlabs.stories.spikes.model.ValidatorEntry;
import org.junit.Test;
import mockit.*;

public class StringToEntryConverterTest
{
	@Injectable
	List<String> stringList;

	@Injectable
	ValidatorEntry validatorEntry;

	@Tested
	private StringToEntryConverter stringToEntryConverter;

	@Test
	public void testParseEntriesHasSameSize() throws Exception
	{
		new Expectations()
		{{
			stringList.size();
			result = 2;

			stringList.get(anyInt);
			result = "TEST";
		}};

		List<ValidatorEntry> validatorEntries = stringToEntryConverter.parseEntries(stringList);
		assertThat(validatorEntries).hasSize(2);
	}

	@Test
	public void testParseEntriesContainsText() throws Exception
	{
		new Expectations()
		{{
			stringList.size();
			result = 1;

			stringList.get(anyInt);
			result = "TEST";
		}};

		List<ValidatorEntry> validatorEntries = stringToEntryConverter.parseEntries(stringList);
		assertThat(validatorEntries.get(0).getSourceTextDescription()).isEqualTo("TEST");
	}

	@Test
	public void testParseFromStringContainsText() throws Exception
	{
		ValidatorEntry validatorEntry = stringToEntryConverter.parseFromString("TEST", 0);
		assertThat(validatorEntry.getSourceTextDescription()).isEqualTo("TEST");
	}
}
