package example.web.api;

import example.AbstractControllerTest;
import example.model.Greeting;
import example.service.EmailService;
import example.service.GreetingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@WebMvcTest(GreetingController.class)
public class GreetingControllerTest extends AbstractControllerTest {

    @MockBean
    private GreetingService greetingService;
    @MockBean
    private EmailService emailService;

    private String url;
    private Greeting greeting;

    @Before
    public void setUp() {
        url = "/api/greetings";

        greeting = new Greeting();
        greeting.setId(1L);
        greeting.setText("Teste greeting");
    }

    @Test
    public void findAll() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    @Test
    public void findOne() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url + "/1").accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        assertEquals(404, status);
    }
}