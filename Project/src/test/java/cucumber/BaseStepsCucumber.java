package cucumber;

import cucumber.helper.HeaderSettingRequestCallback;
import cucumber.helper.ResponseResultErrorHandler;
import cucumber.helper.ResponseResults;
import example.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public abstract class BaseStepsCucumber {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String BASE_URL = "http://localhost:8080";

    protected static ResponseResults latestResponse;

    @Autowired
    protected RestTemplate restTemplate;

    protected void getGreeting(String url) throws Exception {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);

        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError()) {
                return errorHandler.getResults();
            } else {
                log.info("Iniciando o Response: ");
                return new ResponseResults(response);
            }
        });

        System.out.println(latestResponse.toString());
    }

    protected void executePost(String url, String body) throws IOException {
        executePost(url, body, null);
    }

    protected void executePost(String url, String body, Map<String, String> headersOptions) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        if (headersOptions != null) {
            headers.putAll(headersOptions);
        }

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers, body);

        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.POST, requestCallback, response -> {
            if (errorHandler.hadError()) {
                return errorHandler.getResults();
            } else {
                return new ResponseResults(response);
            }
        });

        System.out.println(latestResponse.toString());
    }

    protected void executePut(String url, String body) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers, body);

        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.PUT, requestCallback, response -> {
            if (errorHandler.hadError()) {
                return errorHandler.getResults();
            } else {
                return new ResponseResults(response);
            }
        });

        System.out.println(latestResponse.toString());
    }

    protected void executeDelete(String url) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);

        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.DELETE, requestCallback, response -> {
            if (errorHandler.hadError()) {
                return errorHandler.getResults();
            } else {
                return new ResponseResults(response);
            }
        });

        System.out.println(latestResponse.toString());
    }
}
