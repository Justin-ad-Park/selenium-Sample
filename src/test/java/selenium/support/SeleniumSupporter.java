package selenium.support;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import selenium.SeleniumApplication;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {SeleniumApplication.class} )
@EnableAutoConfiguration
public class SeleniumSupporter {
    public static String SHOP_FRONT_URL;

    @Value("${shop.front-url}")
    public void setShopFrontUrl(String value) {
        SHOP_FRONT_URL = value;
    }

    //메인 팝업 확인 : 메인 팝업이 있어도, 자동화 테스트가 성공하도록 "오늘 하루 보지 않기" 처리함.
    public static boolean CHECK_MAIN_POPUP = true;

    public static final int WIDTH = 1280;   // 브라우저 화면 크기
    public static final int HEIGHT = 900;

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
     * 지정한 페이지로 테스트 시작
     * @param url 테스트 시작 페이지 URL
     */
    protected static void openWebPage(String url) {
        driver.get(url);
        driver.manage().window().setSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * 메인 페이지로 테스트 시작
     */
    protected static void openWebPage() {
        openWebPage(SHOP_FRONT_URL);

        // 처음 메인 페이지를 띄울 때만 팝업 노출 여부를 확인한다.
        if(CHECK_MAIN_POPUP) clickDoNotShowTodayButton();
    }

    /**
     * <p>메인 팝업의 오늘 하루 보지 않기</p>
     * - 클릭 처리 및 이후 테스트에서는 이 로직을 타지 않도록 함
     */
    private static void clickDoNotShowTodayButton() {
        CHECK_MAIN_POPUP = false;    //이후 테스트부터는 메인 팝업을 확인하지 않는다.

        WebElement doNotShowButton = findElementAfterWait(By.cssSelector(".notice__btn__today"));
        //팝업이 있으면, 오늘 하루 보지 않음 버튼 클릭
        if(doNotShowButton != null) {
            doNotShowButton.click();
        }
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
