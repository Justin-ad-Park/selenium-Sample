package testsample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import selenium.SeleniumApplication;
import selenium.constants.FrontConstants;
import selenium.support.SeleniumSupporter;

@SpringBootTest(classes = SeleniumApplication.class)
@EnableAutoConfiguration
public class ZetcTest extends SeleniumSupporter {

    @Autowired
    Environment environment;

    @Test
    void Test() {
        System.out.println(System.getenv("QAPWD"));
        System.out.println(System.getenv("PATH"));


        System.out.println(environment.getProperty("shop.front-url"));
        System.out.println(SHOP_FRONT_URL);
    }
}
