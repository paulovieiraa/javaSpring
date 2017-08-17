package example.batch;

import example.model.Greeting;
import example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    /*Forma mais utilizada atualmente.*/
    @Scheduled(cron = "0,30 * * * * *")
    public void cronJob() {
        logger.info("> cronJob");

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greetings in the data store.", greetings.size());

        logger.info("< cronJob");
    }


    /*Formas antigas de fazer o Scheduled com tempo de Delay*/

    // Metodo 1
    //    @Scheduled(initialDelay = 5000, fixedRate = 15000)
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
    //    @Scheduled (initialDelay = 5000 , fixedDelay = 15000)
    public void fixedDelayJobWithInitialDelay() {
        fixedRateJobWithInitialDelay();
    }

}
