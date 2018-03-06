package example.web.api;

import example.base.DefaultDefaultBaseControllerTest;
import example.model.Greeting;
import example.service.EmailService;
import example.service.GreetingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GreetingController.class)
public class GreetingControllerTest extends DefaultDefaultBaseControllerTest {

    //TODO TERMINAR DOC DO CONTROLLER

    @MockBean
    private GreetingService greetingService;

    @MockBean
    private EmailService emailService;

    private ResponseEntity responseEntity;
    private Collection<Greeting> greetings;
    private Greeting greeting;
    private Long number;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        number = 1L;

        greetings = new ArrayList<>();

        greeting = new Greeting();
        greeting.setId(number);
        greeting.setText("Hello");

        greetings.add(greeting);

        responseEntity = new ResponseEntity(HttpStatus.OK);

        addHeader("X-OtherId", "SomeOtherId");
    }

    @Test
    public void getAll() throws Exception {
        given(greetingService.findAll()).willReturn(greetings);

        get("/api/greetings", status().isOk(), greetings)
                .andDo(MockMvcRestDocumentation.document("greetings-all-sucess",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]").description("Lista de greetings"),
                                fieldWithPath("[0].id").description("id de greeting"),
                                fieldWithPath("[0].text").description("texto do greeting")
                        )));
    }
}