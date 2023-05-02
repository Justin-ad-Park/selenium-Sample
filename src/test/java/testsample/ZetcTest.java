package testsample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import selenium.SeleniumApplication;
import selenium.support.SeleniumSupporter;

@ActiveProfiles("qa")
@SpringBootTest(classes = SeleniumApplication.class)
@EnableAutoConfiguration
public class ZetcTest extends SeleniumSupporter {

    @Autowired
    Environment environment;

    @Test
    void Test() {
        String qapwd = System.getenv("QAPWD");

        System.out.println(environment.getProperty("shop.front-url"));
        System.out.println(SHOP_FRONT_URL);

        Assertions.assertFalse(qapwd.isEmpty());

        Assertions.assertEquals("https://qashop.pulmuone.online/", SHOP_FRONT_URL);
    }
}
