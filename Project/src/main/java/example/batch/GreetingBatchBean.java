package example.batch;

import example.model.Greeting;
import example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Profile("batch")
@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GreetingService greetingService;

    @Autowired
    public GreetingBatchBean(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /*Forma mais utilizada atualmente.*/
    @Scheduled(cron = "${batch.greeting.cron}")
    public void cronJob() {
        logger.info("> cronJob");

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greetings in the data store.", greetings.size());

        logger.info("< cronJob");
    }


    /*Formas antigas de fazer o Scheduled com tempo de Delay*/

    // Metodo 1
//        @Scheduled(initialDelayString = "${batch.greeting.initialDelay}", fixedRateString = "${batch.greeting.fixedRate")
    public void fixedRateJobWithInitialDelay() {
        logger.info("> fixedRateJobWithInitialDelay");

        long pause = 5000;
        long start = System.currentTimeMillis();

        do {
            if (start + pause < System.currentTimeMillis()) {
                break;
            }
        } while (true);

        logger.info("Processing time was {} seconds.", pause / 1000);
        logger.info("< fixedRateJobWithInitialDelay");
    }

    // Metodo 2
//        @Scheduled (initialDelayString = "${batch.greeting.initialDelay" , fixedDelayString = "${batch.greeting.fixedDelay")
    public void fixedDelayJobWithInitialDelay() {
        fixedRateJobWithInitialDelay();
    }

}
