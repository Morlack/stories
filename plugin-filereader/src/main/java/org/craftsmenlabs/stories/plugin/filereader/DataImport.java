package org.craftsmenlabs.stories.plugin.filereader;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Importer
 */
public class DataImport
{

	static final URL SOURCE_PATHS = DataImport.class.getResource("logback.xml");

	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int DOWNLOAD_TIMEOUT = 60000;

	private final Logger logger = LoggerFactory.getLogger(App.class);

	HttpURLConnection conn;

	public String importFrom(String urlResource, String projectKey, String authKey, String statusKey)
	{
		String returnsResponse = "";
		try
		{
			URL url = new URL(urlResource
				+ "/rest/api/2/search?jql=project="
				+ httpEncode(projectKey)
				+ "&maxResults=100000&issuetype=Story&status="
				+ httpEncode(statusKey));
			logger.info("Retrieving data form:" + url.toString());

			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + authKey);
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(DOWNLOAD_TIMEOUT);

			//execute call
			if (conn.getResponseCode() != 200)
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + "\n" + conn
					.getResponseMessage());
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
		catch (MalformedURLException e)
		{

			e.printStackTrace();

		}
		catch (IOException e)
		{

			e.printStackTrace();

		}
		//writeToFile("tmp.json",returnsResponse.toString());

		return returnsResponse;
	}

	public void writeToFile(String filename, String data)
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
