package xyz.lennyesquivel.helpers;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class ConnectionEngine {

    private final URL defaultUrl = new URL("http://localhost:5000");
    private final URL urlInUse;
    private final CloseableHttpClient client = HttpClients.createDefault();

    public ConnectionEngine() throws MalformedURLException {
        urlInUse = defaultUrl;
    }

    public ConnectionEngine(String url) throws MalformedURLException {
        urlInUse = new URL(url);
    }

    public ConnectionEngine(URL url) throws MalformedURLException {
        urlInUse = url;
    }

    public String post(String endpoint, String body) {
        try {
            HttpPost request = new HttpPost(urlInUse + endpoint);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(new StringEntity(body));
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String endpoint) {
        try {
            HttpGet request = new HttpGet(urlInUse + endpoint);
            request.setHeader("Accept", "application/json");
            // TO-DO Update deprecated use of execute
            CloseableHttpResponse response = client.execute(request);
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity());
            } else {
                return null;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String endpoint, Map<String, String> parameters) {
        try {
            HttpGet request = new HttpGet(urlInUse + endpoint);
            URIBuilder uriBuilder = new URIBuilder(request.getUri());
            for (String key : parameters.keySet()) {
                uriBuilder.addParameter(key, parameters.get(key));
            }
            request.setUri(uriBuilder.build());
            request.setHeader("Accept", "application/json");
            CloseableHttpResponse response = client.execute(request);
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity());
            } else {
                return null;
            }
        } catch (IOException | URISyntaxException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
