package selenium.support;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import selenium.SeleniumApplication;
import selenium.constants.FrontConstants;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {SeleniumApplication.class, FrontConstants.class} )
@EnableAutoConfiguration
public class SeleniumSupporter {
    public static String SHOP_FRONT_URL;

    @Value("${shop.front-url}")
    public void setShopFrontUrl(String value) {
        SHOP_FRONT_URL = value;
    }

    public static final int WIDTH = 1278;
    public static final int HEIGHT = 1448;

    protected static WebDriver driver;
    protected static Map<String, Object> vars;
    protected static int DEFAULT_WAITSECONDS_FOR_ELEMENT = 3;
    protected static Stopwatch stopwatch;
    protected static JavascriptExecutor js;

    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();

        stopwatch = Stopwatch.createStarted();
        System.out.println("==Start StopWatch==");

    }

    @AfterAll
    public static void tearDown()
    {
        stopwatch.stop();
        System.out.println("==Stop StopWatch==");
        System.out.println("Duration is " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");


        driver.quit();
    }

    /**
     * 드라이버 준비
     * @param url 테스트 시작 페이지 URL
     */
    protected static void initDriver(String url) {
        driver.get(url);
        driver.manage().window().setSize(new Dimension(WIDTH, HEIGHT));
    }

    protected static String getStringByClassID(String className) {
        WebElement textElement = findElementAfterWait(By.className(className));

        return textElement.getText();
    }


    protected static WebElement findElementAfterWait(By byElement) {
        return findElementAfterWait(byElement, DEFAULT_WAITSECONDS_FOR_ELEMENT);
    }


    protected static WebElement findElementAfterWait(By byElement, int waitSeconds) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
                .until(driver -> driver.findElement(byElement));

        return element;
    }

    public static String waitForWindow(int timeout) {
        sleep(timeout);

        Set<String> whNow = driver.getWindowHandles();
        Set<String> whThen = (Set<String>) vars.get("window_handles");
        if (whNow.size() > whThen.size()) {
            whNow.removeAll(whThen);
        }
        return whNow.iterator().next();
    }

    protected static void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
