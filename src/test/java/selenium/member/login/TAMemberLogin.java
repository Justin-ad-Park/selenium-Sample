package selenium.member.login;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import selenium.SeleniumApplication;
import selenium.constants.FrontConstants;
import selenium.support.SeleniumSupporter;

@SpringBootTest(classes = SeleniumApplication.class)
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TAMemberLogin extends SeleniumSupporter {

    public void QALogout() {
        initDriver(SHOP_FRONT_URL);

        findElementAfterWait(By.linkText("로그아웃")).click();
    }

    public void QALogin() {
        initDriver(SHOP_FRONT_URL);

        String qapwd = System.getenv("QAPWD");
        findElementAfterWait(By.linkText("로그인")).click();


        findElementAfterWait(By.cssSelector(".fb__input-text > .fb__input-text__inner > input")).click();
        findElementAfterWait(By.cssSelector(".fb__input-text > .fb__input-text__inner > input")).sendKeys("sk11sk");
        findElementAfterWait(By.cssSelector("div > .fb__input-text__inner > input")).click();
        findElementAfterWait(By.cssSelector("div > .fb__input-text__inner > input")).sendKeys(qapwd);
        findElementAfterWait(By.cssSelector("div > .fb__input-text__inner > input")).sendKeys(Keys.ENTER);

    }

    public static String getLoginName()
    {
        return getStringByClassID("username");
    }

    @Test
    @Order(1)
    void 로그인_테스트() {
        QALogin();

        Assertions.assertEquals("김정은", getLoginName());
    }

    @Test
    @Order(2)
    void 로그아웃_테스트() {
        QALogout();

        WebElement login = findElementAfterWait(By.linkText("로그인"));

        Assertions.assertEquals("로그인", login.getText());
    }

}