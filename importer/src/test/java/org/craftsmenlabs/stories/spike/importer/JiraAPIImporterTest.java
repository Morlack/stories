package org.craftsmenlabs.stories.spike.importer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.net.*;
import mockit.*;

/**
 *
 */
public class JiraAPIImporterTest
{
	//@Tested
	JiraAPIImporter _jiraAPIImporter;

	@Test(expected = RuntimeException.class)
	public void testimportFrom() throws Exception
	{
		_jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
		String dataImport = _jiraAPIImporter.getDataAsString();
		assertThat(dataImport).contains("");

	}
}
