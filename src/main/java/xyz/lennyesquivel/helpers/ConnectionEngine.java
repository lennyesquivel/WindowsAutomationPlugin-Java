package xyz.lennyesquivel.helpers;


import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.http.ContentType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ConnectionEngine {

    private final URL defaultUrl = new URL("http://localhost:5000");
    private final URL urlInUse;
    private final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
    private String clientSessionId;

    public ConnectionEngine() throws MalformedURLException {
        urlInUse = defaultUrl;
        client.start();
    }

    public ConnectionEngine(String url) throws MalformedURLException {
        urlInUse = new URL(url);
        client.start();
    }

    public ConnectionEngine(URL url) throws MalformedURLException {
        urlInUse = url;
        client.start();
    }

    public void setClientSessionId(String id) {
        this.clientSessionId = id;
    }

    public String post(String endpoint, String body) {
        try {
            SimpleHttpRequest request = SimpleRequestBuilder.post(urlInUse + endpoint)
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-type", "application/json")
                    .addHeader("clientSessionId", this.clientSessionId)
                    .setBody(body, ContentType.APPLICATION_JSON).build();
            Future<SimpleHttpResponse> future = client.execute(request, null);
            SimpleHttpResponse response = future.get();
            if (response.getBody() != null) {
                return response.getBody().getBodyText();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String endpoint) {
        try {
            SimpleHttpRequest request = SimpleRequestBuilder.get(urlInUse + endpoint)
                    .addHeader("Accept", "application/json")
                    .addHeader("clientSessionId", this.clientSessionId).build();
            Future<SimpleHttpResponse> future = client.execute(request, null);
            SimpleHttpResponse response = future.get();
            if (response.getBody() != null) {
                return response.getBody().getBodyText();
            } else {
                return null;
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String endpoint, Map<String, String> parameters) {
        try {
            SimpleRequestBuilder requestBuilder = SimpleRequestBuilder.get(urlInUse + endpoint)
                    .addHeader("Accept", "application/json")
                    .addHeader("clientSessionId", this.clientSessionId);
            for (String key : parameters.keySet()) {
                requestBuilder.addParameter(key, parameters.get(key));
            }
            SimpleHttpRequest request = requestBuilder.build();
            Future<SimpleHttpResponse> future = client.execute(request, null);
            SimpleHttpResponse response = future.get();
            if (response.getBody() != null) {
                return response.getBody().getBodyText();
            } else {
                return null;
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String delete(String endpoint, Map<String, String> parameters) {
        try {
            SimpleRequestBuilder requestBuilder = SimpleRequestBuilder.delete(urlInUse + endpoint)
                    .addHeader("Accept", "application/json")
                    .addHeader("clientSessionId", this.clientSessionId);
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    requestBuilder.addParameter(key, parameters.get(key));
                }
            }
            SimpleHttpRequest request = requestBuilder.build();
            Future<SimpleHttpResponse> future = client.execute(request, null);
            SimpleHttpResponse response = future.get();
            if (response.getBody() != null) {
                return response.getBody().getBodyText();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
