package selenium;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QAShop03AfterWait {
    public static final int WIDTH = 1278;
    public static final int HEIGHT = 1448;
    public static final String HTTPS_QASHOP_PULMUONE_ONLINE = "https://qashop.pulmuone.online/";
    private static WebDriver driver;
    private static Map<String, Object> vars;
    private static int DEFAULT_WAITSECONDS_FOR_ELEMENT = 3;
    private static Stopwatch stopwatch;
    private static JavascriptExecutor js;


    @BeforeAll
    public static void setUp() throws MalformedURLException {
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

    public String waitForWindow(int timeout) {
        sleep(timeout);

        Set<String> whNow = driver.getWindowHandles();
        Set<String> whThen = (Set<String>) vars.get("window_handles");
        if (whNow.size() > whThen.size()) {
            whNow.removeAll(whThen);
        }
        return whNow.iterator().next();
    }

    private void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private WebElement findElementAfterWait(By byElement) {
        return findElementAfterWait(byElement, DEFAULT_WAITSECONDS_FOR_ELEMENT);
    }


    private WebElement findElementAfterWait(By byElement, int waitSeconds) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
                .until(driver -> driver.findElement(byElement));

        return element;
    }

    private String getStringByClassID(String className) {
        WebElement textElement = findElementAfterWait(By.className(className));

        return textElement.getText();
    }

    /**
     * 드라이버 준비
     * @param url 테스트 시작 페이지 URL
     */
    private void initDriver(String url) {
        driver.get(url);
        driver.manage().window().setSize(new Dimension(WIDTH, HEIGHT));
    }


    public void QALogout() {
        initDriver(HTTPS_QASHOP_PULMUONE_ONLINE);

        findElementAfterWait(By.linkText("로그아웃")).click();
    }

    public String QALogin() {
        String qapwd = System.getenv("QAPWD");

        initDriver("https://qashop.pulmuone.online/");

        findElementAfterWait(By.linkText("로그인")).click();


        findElementAfterWait(By.cssSelector(".fb__input-text > .fb__input-text__inner > input")).click();
        findElementAfterWait(By.cssSelector(".fb__input-text > .fb__input-text__inner > input")).sendKeys("sk11sk");
        findElementAfterWait(By.cssSelector("div > .fb__input-text__inner > input")).click();
        findElementAfterWait(By.cssSelector("div > .fb__input-text__inner > input")).sendKeys(qapwd);
        findElementAfterWait(By.cssSelector("div > .fb__input-text__inner > input")).sendKeys(Keys.ENTER);

        String username = getStringByClassID("username");

        return username;
    }

    public void qASearch(String searchText) {
        initDriver("https://qashop.pulmuone.online/");

        findElementAfterWait(By.cssSelector(".fb__input-text__inner > input")).click();
        findElementAfterWait(By.cssSelector(".fb__input-text__inner > input")).sendKeys(searchText);
        findElementAfterWait(By.cssSelector(".fb__input-text__inner > input")).sendKeys(Keys.ENTER);
    }

    /**
     * 검색 결과로 나온 상품수를 확인한다.
     * @return 검색결과수
     */
    private int getSearchResultCount() {
        String resultCount = getStringByClassID("fb__tabs__count");

        return Integer.parseInt(resultCount);
    }

    @Test
    @Order(1)
    void 로그인_테스트() {
        String username = QALogin();

        Assertions.assertEquals("김정은", username);

    }

    @Test
    @Order(2)
    void 로그아웃_테스트() {

        QALogout();

        WebElement login = findElementAfterWait(By.linkText("로그인"));

        Assertions.assertEquals("로그인", login.getText());
    }



    @Test
    @Order(3)
    void 비로그인_검색_테스트() {
        String searchText = "두부";

        qASearch(searchText);

        int resultCount = getSearchResultCount();

        System.out.println(searchText + " 검색결과 : " + resultCount);

        Assertions.assertTrue(resultCount > 1);

    }

    @Test
    void SearchTestEmployee() {
        QALogin();

        String searchText = "두부";

        qASearch(searchText);

        int resultCount = getSearchResultCount();

        System.out.println(searchText + " 검색결과 : " + resultCount);

        Assertions.assertTrue(resultCount > 1);

    }

    @Test
    void 로그인후검색테스트() {
        QALogin();

        qASearch("말똥구리");

        int resultCount = getSearchResultCount();

        System.out.println( " 검색결과 : " + resultCount);

        Assertions.assertTrue(resultCount == 0);

    }
}