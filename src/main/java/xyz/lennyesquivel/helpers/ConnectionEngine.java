package xyz.lennyesquivel.helpers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ConnectionEngine {

    private String defaultUrl = "http://localhost:5000";
    private String urlInUse = "";
    private final HttpClient client = HttpClients.createDefault();

    public ConnectionEngine() {
        urlInUse = defaultUrl;
    }

    public ConnectionEngine(String url) {
        urlInUse = url;
    }

    public String post(String endpoint, String body) {
        try {
            HttpPost request = new HttpPost(urlInUse + endpoint);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(new StringEntity(body));
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String endpoint) {
        try {
            HttpGet request = new HttpGet(urlInUse + endpoint);
            request.setHeader("Accept", "application/json");
            HttpResponse response = client.execute(request);
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity());
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String endpoint, Map<String, String> parameters) {
        try {
            HttpGet request = new HttpGet(urlInUse + endpoint);
            URIBuilder uriBuilder = new URIBuilder(request.getURI());
            for (String key : parameters.keySet()) {
                uriBuilder.addParameter(key, parameters.get(key));
            }
            request.setURI(uriBuilder.build());
            request.setHeader("Accept", "application/json");
            HttpResponse response = client.execute(request);
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity());
            } else {
                return null;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
