package example.service;

import example.AbstractTest;
import example.model.Greeting;
import example.repository.GreetingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

public class GreetingServiceBeanTest extends AbstractTest {

    private GreetingService service;
    private List<Greeting> list;

    @Mock
    private GreetingRepository repository;


    @Before
    public void setUp() throws Exception {
        service = new GreetingServiceBean(repository);

        list = new ArrayList<>();
        Greeting greeting = new Greeting();
        greeting.setId(1L);
        greeting.setText("Text");
        list.add(greeting);
    }

    @Test
    public void findAll() {
        when(repository.findAll()).thenReturn(list);
        Collection<Greeting> list2 = service.findAll();

        Assert.assertNotNull(list2);
    }
}