package bs_bm;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.lightbody.bmp.core.har.Har;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class BrowserMobProxy.
 */
public class BrowserMobProxyClient {

  /** The service. */
  private WebResource service;

  /** The mapper. */
  private ObjectMapper mapper = new ObjectMapper();

  private int port;

  /** The Constant PORT_BEGININDEX. */
  public static final int PORT_BEGININDEX = 8;

  /** The Constant PORT_ENDINDEX. */
  public static final int PORT_ENDINDEX = 12;

  /** The Constant HTTP_STATUS_CODE_200. */
  public static final int HTTP_STATUS_CODE_200 = 200;

  /** The Constant HTTP_STATUS_CODE_204. */
  public static final int HTTP_STATUS_CODE_204 = 204;

  /**
   * Instantiates a new browser mob proxy.
   *
   * @param host
   *            the host
   * @param port
   *            the port
   */
  public BrowserMobProxyClient(String host, int port) {
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    service = client.resource(host + ":" + port + "/proxy");
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getPortUsingUpstreamProxy(String upStreamProxyServer) {
    ClientResponse response = service.queryParam("httpProxy",
        upStreamProxyServer).post(ClientResponse.class);
    return Integer.parseInt(response.getEntity(String.class).substring(
        PORT_BEGININDEX, PORT_ENDINDEX));
  }

  public boolean addHeader(Map<String, String> headers)
      throws IOException {
    ClientResponse response = service.path(Integer.toString(port))
        .path("headers")
        .post(ClientResponse.class, mapper.writeValueAsString(headers));
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean autoBasicAuthorization(String domain, String username,
      String password) throws IOException {
    Map<String, String> auth = new HashMap<String, String>();
    auth.put("username", username);
    auth.put("password", password);

    ClientResponse response = service.path(Integer.toString(port))
        .path("auth").path("basic").path(domain)
        .post(ClientResponse.class, mapper.writeValueAsString(auth));
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean blacklistRequests(String pattern, int responseCode) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("regex", pattern);
    formData.add("status", Integer.toString(responseCode));
    ClientResponse response = service.path(Integer.toString(port))
        .path("blacklist").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean clearDNSCache() {
    ClientResponse response = service.path(Integer.toString(port))
        .path("dns").path("cache").delete(ClientResponse.class);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public Har getHar() throws IOException {
    ClientResponse response = service.path(Integer.toString(port))
        .path("har").get(ClientResponse.class);
    String responseBody = response.getEntity(String.class);
    return mapper.readValue(responseBody, Har.class);
  }

  public int getPort() {
    ClientResponse response = service.post(ClientResponse.class);
    String responseBody = response.getEntity(String.class);
    int actPort = Integer.parseInt(responseBody.substring(PORT_BEGININDEX,
        PORT_ENDINDEX));
    return actPort;
  }

  public int getPort(int port) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("port", Integer.toString(port));
    ClientResponse response = service.post(ClientResponse.class, formData);
    int newPort = Integer.parseInt(response.getEntity(String.class)
        .substring(PORT_BEGININDEX, PORT_ENDINDEX));
    return newPort;
  }

  public boolean enableLimiter(boolean status) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("enable", Boolean.toString(status));
    ClientResponse response = service.path(Integer.toString(port))
        .path("limit").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setDownstreamKbps(long downstreamKbps) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("downstreamKbps", Long.toString(downstreamKbps));
    ClientResponse response = service.path(Integer.toString(port))
        .path("limit").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setLatency(long latency) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("latency", Long.toString(latency));
    ClientResponse response = service.path(Integer.toString(port))
        .path("limit").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setMaxBitsPerSecondThreshold(long maxBitsPerSecond) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("maxBitsPerSecond", Long.toString(maxBitsPerSecond));
    ClientResponse response = service.path(Integer.toString(port))
        .path("limit").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setPayloadPercentage(long payloadPercentage) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("payloadPercentage", Long.toString(payloadPercentage));
    ClientResponse response = service.path(Integer.toString(port))
        .path("limit").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setUpstreamKbps(long upstreamKbps) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("upstreamKbps", Long.toString(upstreamKbps));
    ClientResponse response = service.path(Integer.toString(port))
        .path("limit").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean newHar(String initialPageRef, boolean captureContent,
      boolean captureHeaders, boolean captureBinaryContent) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    if (!initialPageRef.isEmpty()) {
      formData.add("initialPageRef", initialPageRef);
    }
    if (captureContent) {
      formData.add("captureContent", Boolean.toString(captureContent));
    }
    if (captureHeaders) {
      formData.add("captureHeaders", Boolean.toString(captureHeaders));
    }
    if (captureBinaryContent) {
      formData.add("captureBinaryContent",
          Boolean.toString(captureBinaryContent));
    }
    ClientResponse response = service.path(Integer.toString(port))
        .path("har").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_204);
  }

  public boolean newPage(String pageRef) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    if (!pageRef.isEmpty()) {
      formData.add("pageRef", pageRef);
    }
    ClientResponse response = service.path(Integer.toString(port))
        .path("har").path("pageRef")
        .put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean remapHost(Map<String, String> hosts)
      throws IOException {
    ClientResponse response = service.path(Integer.toString(port))
        .path("hosts")
        .post(ClientResponse.class, mapper.writeValueAsString(hosts));
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean rewriteUrl(String matchRegex, String replace) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("matchRegex", matchRegex);
    formData.add("replace", replace);
    ClientResponse response = service.path(Integer.toString(port))
        .path("rewrite").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setConnectionTimeout(long connectionTimeout) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("connectionTimeout", Long.toString(connectionTimeout));
    ClientResponse response = service.path(Integer.toString(port))
        .path("timeout").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setDNSCacheTimeout(long dnsCacheTimeout) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("dnsCacheTimeout", Long.toString(dnsCacheTimeout));
    ClientResponse response = service.path(Integer.toString(port))
        .path("timeout").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setRequestTimeout(long requestTimeout) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("requestTimeout", Long.toString(requestTimeout));
    ClientResponse response = service.path(Integer.toString(port))
        .path("timeout").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setRetryCount(int retrycount) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("retrycount", Integer.toString(retrycount));
    ClientResponse response = service.path(Integer.toString(port))
        .path("retry").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean setSocketOperationTimeout(long readTimeout) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("readTimeout", Long.toString(readTimeout));
    ClientResponse response = service.path(Integer.toString(port))
        .path("timeout").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean stop() {
    ClientResponse response = service.path(Integer.toString(port)).delete(
        ClientResponse.class);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean waitForNetworkTrafficToStop(long quietPeriodInMs,
      long timeoutInMs) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("quietPeriodInMs", Long.toString(quietPeriodInMs));
    formData.add("timeoutInMs", Long.toString(timeoutInMs));
    ClientResponse response = service.path(Integer.toString(port))
        .path("wait").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

  public boolean whitelistRequests(String patterns, int responseCode) {
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.add("regex", patterns);
    formData.add("status", Integer.toString(responseCode));
    ClientResponse response = service.path(Integer.toString(port))
        .path("whitelist").put(ClientResponse.class, formData);
    return (response.getStatus() == HTTP_STATUS_CODE_200);
  }

}
