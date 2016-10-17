package org.craftsmenlabs.stories.plugin.filereader;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import mockit.Tested;

/**
 *
 */
public class DataImportTest
{
	@Tested
	DataImport _dataImport;

	@Test
	public void testimportFrom() throws Exception
	{
		String dataImport = _dataImport.importFrom("", "", "", "To Do");
		System.out.println(dataImport);
		assertThat(dataImport).contains("");
	}

}
