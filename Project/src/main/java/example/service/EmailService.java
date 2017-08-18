package example.service;

import example.model.Greeting;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author Paulo Vieira
 */

public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);
}
