package example.service;

import example.model.Greeting;
import example.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService {

    private final GreetingRepository repository;

    @Autowired
    public GreetingServiceBean(GreetingRepository repository) {
        this.repository = repository;
    }

    /**
     * findAll
     */
    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = repository.findAll();
        return greetings;
    }

    /**
     * findOne
     */
    @Override
    @Cacheable(value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        Greeting greeting = repository.findOne(id);
        return greeting;
    }

    /**
     * Save
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#result.id")
    public Greeting create(Greeting greeting) {
        if (greeting.getId() != null) {
            return null;
        }
        //create and update are the same.
        Greeting saveGreeting = repository.save(greeting);

        /*A annotation @Transactional, realiza inserções no banco de dados. Quando elas falharem, pode se ocorrer um rollBack*/
        if (saveGreeting.getId() == 4L) {
            throw new RuntimeException("\n\n*****************************" +
                    "\nRoll me back !!!!! " +
                    "\nExemplo de erro com a annotation @Transactional." +
                    "\n*****************************");
        }
        return saveGreeting;
    }

    /**
     * Update
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = findOne(greeting.getId());

        if (greetingPersisted == null) {
            return null;
        }

        Greeting updatedGreeting = repository.save(greeting);
        return updatedGreeting;
    }

    /**
     * Delete
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(value = "greetings", key = "#id")
    public void delete(Long id) {
        repository.delete(id);
    }

    /**
     * EvictCache
     */
    @Autowired
    @CacheEvict(value = "greetings", allEntries = true)
    public void evictCache() {
    }
}
