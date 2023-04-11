package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * <h2>Selenium WebDriver Download</h2>
 * 셀레늄 웹(브라우저) 드라이버 다운로드 <a href="https://www.selenium.dev/downloads/">Selenium Downloads</a> {@link Selenium.dev }
 * <p>
 * <img width="640" height="317" src="../../resources/javadoc/seleniumDriverDownload.jpg" alt="">
 * </p>
 * <br/>
 *
 * <h2>다운로드 및 설치 순서</h3>
 *  <pre>
 *  1. 다운로드
 *  2. 압축해제
 *  3. 압축해제된 파일을 시스템 환경변수 PATH 경로에 복사 또는 PATH에 경로 추가
 *
 *  Bash :
 *      echo 'export PATH=$PATH:/{설치경로}' >> ~/.bash_profile
 *      source ~/.bash_profile
 *
 *  Zsh :
 *      echo 'export PATH=$PATH:/{bin}' >> ~/.zshenv
 *      source ~/.zshenv
 *
 * 4. 드라이버를 실행해서 경로, 권한 확인
 *  >>chromedirver
 *  </pre>
 * */
public class FirstScriptTest {

    @Test
    public void eightComponents() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals("Web form", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");

        Thread.sleep(3000L);

        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals("Received!", value);

        Thread.sleep(3000L);

        driver.quit();
    }
}