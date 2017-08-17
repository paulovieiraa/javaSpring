package example.web.api;

import example.model.Greeting;
import example.service.EmailService;
import example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.Future;

@RestController
public class GreetingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private EmailService emailService;

    /**
     * Get all
     */
    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {

        logger.info("> getGreetings");
        Collection<Greeting> greetings = greetingService.findAll();

        logger.info("< getGreetings");

        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    /**
     * Get by Id
     */
    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {

        logger.info("> getGreetings by id");
        Greeting greeting = greetingService.findOne(id);

        if (greeting == null) {
            logger.info("< getGreetings");
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }

        logger.info("< getGreetings by id");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }


    /**
     * Metodo Post
     */
    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {

        logger.info("> createGreetings");
        Greeting saveGreeting = greetingService.create(greeting);

        logger.info("< createGreetings");
        return new ResponseEntity<Greeting>(saveGreeting, HttpStatus.CREATED);
    }

    /**
     * Metodo update
     */
    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
        logger.info("> updateGreetings");
        Greeting updatedGreeting = greetingService.update(greeting);

        if (updatedGreeting == null) {
            logger.info("< updateGreetings");
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("< updateGreetings");
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    /**
     * Metodo Delete
     */
    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id) {
        logger.info("> deleteGreetings");
        greetingService.delete(id);

        logger.info("< deleteGreetings");
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

    /**
     * Send Async
     */
    @RequestMapping(value = "/api/greetings/{id}/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> sendGreeting
    (@PathVariable("id") Long id,
     @RequestParam(value = "wait", defaultValue = "false")
             boolean waitForAsyncResult) {

        logger.info("> sendGreeting");
        Greeting greeting = null;

        try {
            greeting = greetingService.findOne(id);

            if (greeting == null) {
                logger.info("< sendGreeting");
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }

            if (waitForAsyncResult) {

                Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
                boolean emailSent = asyncResponse.get();
                logger.info("- greeting email sent? {}", emailSent);
            } else {
                emailService.sendAsync(greeting);
            }
        } catch (Exception e) {
            logger.error("A problem occurred sending the Greeting.", e);
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("< sendGreeting");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}
