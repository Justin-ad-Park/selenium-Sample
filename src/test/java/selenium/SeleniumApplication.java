package selenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// @ActiveProfiles("qa")
@SpringBootTest
public class SeleniumApplication {

    public static void main(final String[] args)
    {
        SpringApplication.run(SeleniumApplication.class, args);
    }

}
