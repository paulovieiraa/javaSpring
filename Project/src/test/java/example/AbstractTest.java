package example;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe default para testes - Spring.
 */

/*TU - Junit*/
@RunWith(SpringRunner.class)
/*Teste Integrado - Contexto*/
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
