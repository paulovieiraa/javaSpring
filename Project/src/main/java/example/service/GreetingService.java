package example.service;

import example.model.Greeting;

import java.util.Collection;
import java.util.List;

/**
 * @author Paulo Vieira
 */

public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    void delete(Long id);

    void evictCache();
}
