package selenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;

//@ActiveProfiles("canary")
@ConfigurationPropertiesScan("selenium")
@SpringBootTest
public class SeleniumApplication {

    public static void main(final String[] args)
    {
        SpringApplication.run(SeleniumApplication.class, args);
    }

}
