package example.service;

import example.base.DefaultBaseServiceTest;
import example.exception.ValidationException;
import example.model.Greeting;
import example.repository.GreetingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.actuate.metrics.CounterService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

/**
 * @author Paulo Vieira
 */

public class GreetingServiceBeanTestDefault extends DefaultBaseServiceTest {

    /**
     * Testes unitarios
     */

    private GreetingService service;
    private List<Greeting> list;
    private Greeting greeting;

    @Mock
    private GreetingRepository repository;
    @Mock
    private CounterService counterService;
    private Greeting returnMethod;

    @Before
    public void setUp() throws Exception {
        service = new GreetingServiceBean(repository, counterService);

        list = new ArrayList<>();
        greeting = new Greeting();
        greeting.setId(1L);
        greeting.setText("Text");
        list.add(greeting);
    }

    @Test
    public void findAll() {
        when(repository.findAll()).thenReturn(list);
        Collection<Greeting> returnMethod = service.findAll();

        assertNotNull(returnMethod);
    }

    @Test
    public void findOne() {
        when(repository.findOne(notNull(Long.class))).thenReturn(greeting);
        returnMethod = service.findOne(1L);

        assertNotNull(returnMethod);
    }

    @Test
    public void findOneException() {
        greeting = null;
        when(repository.findOne(notNull(Long.class))).thenReturn(greeting);
        thrown.expect(ValidationException.class);

        service.findOne(1L);
    }

    @Test
    public void save() {
        Greeting greeting2 = new Greeting();
        greeting2.setText("Text");

        when(repository.save(greeting2)).thenReturn(greeting2);

        returnMethod = service.create(greeting2);
        assertNotNull(returnMethod.getText());
    }

    @Test
    public void saveException() {
        when(repository.save(greeting)).thenReturn(greeting);
        thrown.expect(ValidationException.class);
        thrown.expectMessage("Algo deu errado. O greeting id está diferente de Null.");

        service.create(greeting);
    }

    @Test
    public void update() {
        when(repository.findOne(notNull(Long.class))).thenReturn(greeting);
        when(repository.save(greeting)).thenReturn(greeting);

        returnMethod = service.update(greeting);
        assertNotNull(repository);
    }

    @Test
    public void delete() {
        service.delete(1L);
    }
}