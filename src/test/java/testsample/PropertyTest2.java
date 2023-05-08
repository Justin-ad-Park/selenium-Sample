package testsample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import selenium.SeleniumApplication;
import selenium.constants.FrontConstants;
import selenium.support.SeleniumSupporter;

import java.util.List;

//@ActiveProfiles("qa")
@SpringBootTest(classes = SeleniumApplication.class)
public class PropertyTest2 extends SeleniumSupporter {
    @Autowired
    Environment environment;

    @Autowired
    FrontConstants frontConstants;

    public static String SHOP_FRONT_URL;

    @Value("${shop.front-url}")
    public void setShopFrontUrl(String value) {
        SHOP_FRONT_URL = value;
    }

    public static List<Integer> ETC_TEST_VALUE;

    @Value("${shop.etcvalue}")
    public void setEtcTestValue(List<Integer> value) {
        ETC_TEST_VALUE = value;
    }

    @Test
    void Test() {
        List<Integer> etcValue = environment.getProperty("shop.etcvalue", List.class);

        System.out.println(etcValue);
        System.out.println(ETC_TEST_VALUE);

        String qapwd = System.getenv("QAPWD");


        Assertions.assertFalse(qapwd.isEmpty());

        Assertions.assertEquals("https://qashop.pulmuone.online/", SHOP_FRONT_URL);
    }

    @Test
    void propertyTest() {
        System.out.println(frontConstants.getFrontUrl());
        Assertions.assertFalse(frontConstants.getFrontUrl().isEmpty());
    }
}
