package cucumber.steps;

import cucumber.BaseStepsCucumber;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import example.model.Greeting;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyStepdefs extends BaseStepsCucumber {

    //findAll
    @Quando("^chamarmos a BASE_URL concatenado com a url \"([^\"]*)\"$")
    public void chamarmosABASE_URLConcatenadoComAUrl(String string) throws Throwable {
        String url = BASE_URL.concat(string);
        getGreeting(url);
    }

    @Entao("^o serviço devera retonar HTTP-STATUS (\\d+), listando os greetings$")
    public void oServiçoDeveraRetonarHTTPSTATUSListandoOsGreetings(int status) throws Throwable {
        List<Greeting> greetingsList = (List<Greeting>) latestResponse.getBodyAsObject(List.class);
        int responseHttpStatus = latestResponse.getTheResponse().getRawStatusCode();

        assertEquals(status, responseHttpStatus);
        assertNotNull(greetingsList);
    }

    //findOne
    @Quando("^chamarmos a BASE_URL concatenado com a url \"([^\"]*)\" concatenando com o id \"([^\"]*)\" do greeting selecionado$")
    public void chamarmosABASE_URLConcatenadoComAUrlConcatenandoComOIdDoGreetingSelecionado(String string, String numberGreeting) throws Throwable {
        String url = BASE_URL.concat(string).concat(numberGreeting);
        getGreeting(url);
    }

    @Entao("^o serviço devera retonar HTTP-STATUS (\\d+), listando o greeting$")
    public void oServiçoDeveraRetonarHTTPSTATUSListandoOGreeting(int status) throws Throwable {
        Greeting greeting = latestResponse.getBodyAsObject(Greeting.class);
        int responseHttpStatus = latestResponse.getTheResponse().getRawStatusCode();

        assertEquals(status, responseHttpStatus);
        assertNotNull(greeting);
        assertNotNull(greeting.getText());
    }

    //post
    @Quando("^chamarmos a BASE_URL concatenado com a url \"([^\"]*)\", inserindo o texto \"([^\"]*)\"$")
    public void chamarmosABASE_URLConcatenadoComAUrlInserindoOTexto(String string, String text) throws Throwable {
        String url = BASE_URL.concat(string);

        String body = String.format("{\n" +
                "  \"text\": \"%s\"\n" +
                "}", text);

        executePost(url, body);

    }

    @Entao("^o serviço devera retonar HTTP-STATUS (\\d+), listando o greeting inserido$")
    public void oServiçoDeveraRetonarHTTPSTATUSListandoOGreetingInserido(int status) throws Throwable {
        Greeting greeting = latestResponse.getBodyAsObject(Greeting.class);
        int responseHttpStatus = latestResponse.getTheResponse().getRawStatusCode();

        assertEquals(status, responseHttpStatus);
        assertNotNull(greeting);
    }

    //update
    @Quando("^chamarmos a BASE_URL concatenado com a url \"([^\"]*)\" com o id \"([^\"]*)\", inserindo o \"([^\"]*)\" e alterando o  \"([^\"]*)\" do greeting$")
    public void chamarmosABASE_URLConcatenadoComAUrlComOIdInserindoOEAlterandoODoGreeting(String string, String id, String numberGreeting, String text) throws Throwable {
        String url = BASE_URL.concat(string).concat(id);

        String body = String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"text\": \"%s\"\n" +
                "}", numberGreeting, text);

        executePut(url, body);
    }

    @Entao("^o serviço devera retonar HTTP-STATUS (\\d+), listando o greeting atualizado$")
    public void oServiçoDeveraRetonarHTTPSTATUSListandoOGreetingAtualizado(int status) throws Throwable {
        Greeting greeting = latestResponse.getBodyAsObject(Greeting.class);
        int responseHttpStatus = latestResponse.getTheResponse().getRawStatusCode();

        assertEquals(status, responseHttpStatus);
        assertNotNull(greeting);
    }

    //delete
    @Entao("^o serviço devera retonar HTTP-STATUS (\\d+)$")
    public void oServiçoDeveraRetonarHTTPSTATUS(int status) throws Throwable {
        int responseStatus = latestResponse.getTheResponse().getRawStatusCode();
        assertEquals(status, responseStatus);
    }

}
