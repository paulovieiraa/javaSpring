package example.base;

import example.TestApplication;
import example.util.JsonHelper;
import example.util.MockMvcRequestBuilder;
import example.web.api.BaseController;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/*Test Controllers*/
@ContextConfiguration(classes = TestApplication.class)
public abstract class DefaultDefaultBaseControllerTest extends DefaultBaseServiceTest {

    @Autowired
    private WebApplicationContext context;

//    @MockBean
//    BaseController controllerExceptionHandler;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;

    private HttpHeaders httpHeaders;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation).uris()
                        .withScheme("https")
                        .withPort(8080)
                        .withHost("localhost://")
                )
                .build();

        httpHeaders = new HttpHeaders();

        httpHeaders.add("X-Application-Key", "SomeAppKey");
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");
    }

    /**
     * Get
     */
    public ResultActions get(String urlTemplate, ResultMatcher resultMatcher) throws Exception {
        return get(urlTemplate, resultMatcher, null);
    }

    public ResultActions get(String urlTemplate, ResultMatcher resultMatcher, Object result) throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilder.get(urlTemplate).headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    public ResultActions get(String urlTemplate, ResultMatcher resultMatcher, Object result, Object... params) throws Exception {
        final ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get(urlTemplate, params)
                .headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    /**
     * Post
     */
    public ResultActions post(String urlTemplate, ResultMatcher resultMatcher, Object result) throws Exception {
        return post(urlTemplate, resultMatcher, null, result);
    }

    public ResultActions post(String urlTemplate, ResultMatcher resultMatcher, Object requestBody, Object result) throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilder.post(urlTemplate, requestBody).headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    public ResultActions post(String urlTemplate, ResultMatcher resultMatcher, Object result, Object... params) throws Exception {
        final ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post(urlTemplate, params)
                .headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    /**
     * Put
     */

    public ResultActions put(String urlTemplate, ResultMatcher resultMatcher, Object body) throws Exception {
        return put(urlTemplate, resultMatcher, body, null);
    }

    public ResultActions put(String urlTemplate, ResultMatcher resultMatcher, Object body, Object result) throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilder.put(urlTemplate, body).headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    public ResultActions put(String urlTemplate, ResultMatcher resultMatcher, Object result, Object... params) throws Exception {
        final ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.put(urlTemplate, params)
                .headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    /**
     * Delete
     */

    public ResultActions delete(String urlTemplate, ResultMatcher resultMatcher) throws Exception {
        return delete(urlTemplate, resultMatcher, null);
    }

    public ResultActions delete(String urlTemplate, ResultMatcher resultMatcher, Object result) throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilder.delete(urlTemplate).headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    public ResultActions delete(String urlTemplate, ResultMatcher resultMatcher, Object result, Object... params) throws Exception {
        final ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete(urlTemplate, params)
                .headers(httpHeaders));

        expectDefault(resultMatcher, result, resultActions);

        return resultActions;
    }

    /**
     * expected
     */
    private void expectDefault(ResultMatcher resultMatcher, Object result, ResultActions resultActions) throws Exception {
        if (resultMatcher != null) {
            resultActions.andExpect(resultMatcher);
        }

        if (result != null) {
            if (result instanceof String) {
                resultActions.andExpect(content().string(String.valueOf(result)));
            }
            if (result instanceof ResponseEntity) {
                Object body = ((ResponseEntity) result).getBody();
                if (body != null) {
                    resultActions.andExpect(content().json(JsonHelper.objectToJson(body)));
                }
            } else {
                resultActions.andExpect(content().json(JsonHelper.objectToJson(result), true));
            }
        }
    }

    public void addHeader(String name, String value) {
        this.httpHeaders.add(name, value);
    }
}
