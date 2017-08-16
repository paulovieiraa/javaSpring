package example.service;

import example.model.Greeting;
import example.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository repository;

    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = repository.findAll();
        return greetings;
    }

    @Override
    public Greeting findOne(Long id) {
        Greeting greeting = repository.findOne(id);
        return greeting;
    }

    @Override
    public Greeting create(Greeting greeting) {
        if (greeting.getId() != null) {
            return null;
        }
        //create and update are the same.
        Greeting saveGreeting = repository.save(greeting);
        return saveGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = findOne(greeting.getId());

        if (greetingPersisted == null) {
            return null;
        }

        Greeting updatedGreeting = repository.save(greeting);
        return updatedGreeting;
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
