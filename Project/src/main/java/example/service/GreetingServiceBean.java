package example.service;

import example.exception.ValidationException;
import example.model.Greeting;
import example.repository.GreetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Paulo Vieira
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CounterService counterService;
    private final GreetingRepository repository;

    @Autowired
    public GreetingServiceBean(GreetingRepository repository, @Qualifier("counterService") CounterService counterService) {
        this.repository = repository;
        this.counterService = counterService;
    }

    /**
     * findAll
     */
    @Override
    public Collection<Greeting> findAll() {
        counterService.increment("method.invoked.greetingServiceBean.findAll");

        Collection<Greeting> greetings = repository.findAll();
        return greetings;
    }

    /**
     * findOne
     */
    @Override
    @Cacheable(value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        counterService.increment("method.invoked.greetingServiceBean.findOne");

        Greeting greeting = repository.findOne(id);
        if (greeting == null) {
            log.info("Greeting Null");
            throw new ValidationException("Não encontrei o greeting com id " + id + " .");
        } else
            return greeting;
    }

    /**
     * Save
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#result.id")
    public Greeting create(Greeting greeting) {
        counterService.increment("method.invoked.greetingServiceBean.create");

        if (greeting.getId() != null) {
            log.info("Greeting não está null");
            throw new ValidationException("Algo deu errado. O greeting id está diferente de Null.");
        }

        Greeting saveGreeting = repository.save(greeting);

        if (greeting.getId() != null) {
        /*A annotation @Transactional, realiza inserções no banco de dados. Quando elas falharem, pode se ocorrer um rollBack*/
            if (saveGreeting.getId() == 4L) {
                throw new RuntimeException("\n\n*****************************" +
                        "\nRoll me back !!!!! " +
                        "\nExemplo de erro com a annotation @Transactional." +
                        "\n*****************************");
            }
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
        counterService.increment("method.invoked.greetingServiceBean.update");

        Greeting greetingPersisted = findOne(greeting.getId());

        if (greetingPersisted == null) {
            log.info("Greeting Null");
            throw new ValidationException("Greeting Null");
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
        counterService.increment("method.invoked.greetingServiceBean.delete");

        try {
            repository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ValidationException("Id " + id + " não existe.");
        } catch (Exception e) {
            throw new RuntimeException("Outra excecao.");
        }
    }

    /**
     * EvictCache
     */
    @Autowired
    @CacheEvict(value = "greetings", allEntries = true)
    public void evictCache() {
    }
}
