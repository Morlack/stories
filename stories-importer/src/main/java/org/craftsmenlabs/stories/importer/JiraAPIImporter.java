package org.craftsmenlabs.stories.importer;

import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Importer
 */
public class JiraAPIImporter implements Importer
{
	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int DOWNLOAD_TIMEOUT = 60000;

	private final Logger logger = LoggerFactory.getLogger(JiraAPIImporter.class);

	private String urlResource;
	private String projectKey;
	private String authKey;
	private String statusKey;

	public JiraAPIImporter(String urlResource, String projectKey, String authKey, String statusKey)
	{
		this.urlResource = urlResource;
		this.projectKey = projectKey;
		this.authKey = authKey;
		this.statusKey = statusKey;
	}

	public String getDataAsString()
	{
		String returnsResponse = "";
		try
		{
			URL url = new URL(urlResource
				+ "/rest/api/2/search?jql="
				+ "project%3D" + httpEncode(projectKey) + "+AND+"
				+ "type%3DStory+AND+"
				+ "status%3D\"" + httpEncode(statusKey) + "\""
				+ "&maxResults=100000");
			logger.info("Retrieving data form:" + url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + authKey);
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(DOWNLOAD_TIMEOUT);

			//execute call
			if (conn.getResponseCode() != 200)
			{
				logger.error("Failed to connect to "
					+ url
					+ ". Connection returned HTTP code: "
					+ conn.getResponseCode()
					+ "\n"
					+ conn
					.getResponseMessage());

				abortOnError();
			}

			// Buffer the result into a string
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null)
			{
				sb.append(line);
			}
			rd.close();

			returnsResponse = sb.toString();

			conn.disconnect();

		}
		catch (IOException e)
		{
			logger.error("Failed to connect to create a proper connection URL with parameters:" + getParameters());
			abortOnError();
		}

		return returnsResponse;
	}

	private void abortOnError()
	{
        throw new StoriesException("Failed to connect to " + urlResource);
    }

	private String httpEncode(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode, "UTF-8");
	}

	private String getParameters()
	{
		return "URL" + urlResource
			+ " AUTH:" + authKey
			+ " PROJECT:" + projectKey
			+ " STATUSKEY:" + statusKey;

	}
}
