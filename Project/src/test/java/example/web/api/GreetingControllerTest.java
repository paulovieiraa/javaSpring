package example.web.api;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import example.base.DefaultDefaultBaseControllerTest;
import example.model.Greeting;
import example.service.EmailService;
import example.service.GreetingService;

@WebMvcTest(GreetingController.class)
public class GreetingControllerTest extends DefaultDefaultBaseControllerTest {

    @MockBean
    private GreetingService greetingService;
    @MockBean
    private EmailService emailService;

    private Collection<Greeting> greetings;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        greetings = new HashSet<>();

        Greeting greeting = new Greeting();
        greeting.setId(1L);
        greeting.setText("Hello");

        greetings.add(greeting);
    }

    @Test
    public void getAll() throws Exception {
        addHeader("X-OtherId", "SomeOtherId");

        when(greetingService.findAll()).thenReturn(greetings);

        get("/api/greetings", status().isOk(), new ResponseEntity<>(greetings, HttpStatus.OK))
                .andDo(MockMvcRestDocumentation.document("greetings-all-sucess",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]").description("Lista de greetings"),
                                fieldWithPath("[].id").description("id do greeting"),
                                fieldWithPath("[].text").description("texto do greeting")
                        )));
    }
}