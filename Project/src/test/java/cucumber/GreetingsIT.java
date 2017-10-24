package cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Paulo Vieira on 21/09/17.
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/cucumber/steps", glue = "cucumber.steps")
public class GreetingsIT {

    /**
     * Start - Testes Cucumber
     * */
}
