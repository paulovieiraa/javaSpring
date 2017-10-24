package example;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Classe default para testes - Spring.
 */

/*TU - Junit*/
@RunWith(SpringRunner.class)

/*Teste Integrado - Contexto*/
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public abstract class AbstractTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**/
    public void validateAndCoverConstructorPrivate(final Class<?> clazz) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        assertEquals(1, clazz.getDeclaredConstructors().length);

        final Constructor<?> constructor = clazz.getDeclaredConstructor();

        assertFalse(constructor.isAccessible());
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
    }
}
