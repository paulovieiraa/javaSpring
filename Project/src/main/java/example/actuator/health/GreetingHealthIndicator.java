package example.actuator.health;

import example.model.Greeting;
import example.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Paulo Vieira
 */

@Component
public class GreetingHealthIndicator implements HealthIndicator {

    private final GreetingService greetingService;

    @Autowired
    public GreetingHealthIndicator(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public Health health() {
        Collection<Greeting> greetings = greetingService.findAll();

        if (greetings == null || greetings.isEmpty()) {
            return Health.down().withDetail("count", 0).build();
        }
        return Health.up().withDetail("count", greetings.size()).build();
    }
}
