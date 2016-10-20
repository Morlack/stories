package org.craftsmenlabs.stories.spike.importer;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Importer
 */
public class JiraAPIImporter implements Importer
{
	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int DOWNLOAD_TIMEOUT = 60000;
	//static final URL SOURCE_PATHS = JiraAPIImporter.class.getResource("logback.xml");
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
			URL url = createUrl(projectKey, statusKey);

			logger.info("Retrieving data form:" + url.toString());
			HttpURLConnection conn = createConnection(url, authKey);

			//execute call
			if (conn.getResponseCode() != 200)
			{
				logger.error("Failed : HTTP error code : " + conn.getResponseCode() + "\n" + conn
					.getResponseMessage());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + "\n" + conn
					.getResponseMessage());

			}

			returnsResponse = readFromUrlConnection(conn);

			conn.disconnect();

		}
		catch (IOException e)
		{
			logger.error("Unable to create Jira Connection using URL: " + urlResource, e);
			throw new RuntimeException();
		}
		//writeToFile("tmp.json",returnsResponse.toString());

		return returnsResponse;
	}

	protected URL createUrl(String projectKey, String statusKey) throws UnsupportedEncodingException, MalformedURLException
	{
		return new URL(urlResource
			+ "/rest/api/2/search?jql="
			+ "project%3D" + httpEncode(projectKey) + "+AND+"
			+ "type%3DStory+AND+"
			+ "status%3D\"" + httpEncode(statusKey) + "\""
			+ "&maxResults=100000");
	}

	protected HttpURLConnection createConnection(URL url, String authToken) throws IOException
	{
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Basic " + authToken);
		conn.setConnectTimeout(CONNECTION_TIMEOUT);
		conn.setReadTimeout(DOWNLOAD_TIMEOUT);
		return conn;
	}

	protected String readFromUrlConnection(URLConnection conn) throws IOException
	{
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

		return sb.toString();
	}

	protected void writeToFile(String filename, String data)
	{
		File f = new File("tmp.json");
		try (FileOutputStream out = new FileOutputStream(filename))
		{
			out.write(data.getBytes());
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String httpEncode(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode, "UTF-8");
	}
}
