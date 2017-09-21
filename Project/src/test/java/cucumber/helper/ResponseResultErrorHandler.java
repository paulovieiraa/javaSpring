package cucumber.helper;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ResponseResultErrorHandler implements ResponseErrorHandler {

    private ResponseResults results = null;
    private Boolean error = false;

    public ResponseResults getResults() {
        return results;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        error = response.getRawStatusCode() >= 400;
        return error;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        results = new ResponseResults(response);
    }

    public Boolean hadError() {
        return error;
    }
}
