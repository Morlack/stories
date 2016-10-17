package org.craftsmenlabs.stories.spike.importer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

public class JiraAPIImporter {
    String BASE_URL;
    String USER;
    String PASSWORD;

    public JiraAPIImporter(String url, String username, String password) {
        BASE_URL = url;
        USER = username;
        PASSWORD = password;
    }

    public String read(){
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(BASE_URL);

        return target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, USER)
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, PASSWORD)
                .get()
                .readEntity(String.class);
    }
}
