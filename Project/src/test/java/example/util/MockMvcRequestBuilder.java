package example.util;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class MockMvcRequestBuilder extends MockMvcRequestBuilders {

    public static MockHttpServletRequestBuilder get(String urlTemplate) {
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(urlTemplate);
        return mockHttpServletRequestBuilder;
    }

    public static MockHttpServletRequestBuilder post(String urlTemplate, Object body) {
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(urlTemplate);
        if (body != null) {
            mockHttpServletRequestBuilder.content(JsonHelper.objectToJson(body));
        }
        return mockHttpServletRequestBuilder;
    }

    public static MockHttpServletRequestBuilder put(String urlTemplate, Object body) {
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(urlTemplate);
        if (body != null) {
            mockHttpServletRequestBuilder.content(JsonHelper.objectToJson(body));
        }
        return mockHttpServletRequestBuilder;
    }

    public static MockHttpServletRequestBuilder delete(String urlTemplate) {
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete(urlTemplate);
        return mockHttpServletRequestBuilder;
    }

}
