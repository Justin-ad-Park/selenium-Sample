package selenium.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import selenium.SeleniumApplication;
import selenium.member.login.TAMemberLogin;
import selenium.support.SeleniumSupporter;

//@ActiveProfiles("qa")
public class TMSearch extends SeleniumSupporter {

    public static void qASearch(String searchText) {
        openWebPage();

        findElementAfterWait(By.cssSelector(".fb__input-text__inner > input")).click();
        findElementAfterWait(By.cssSelector(".fb__input-text__inner > input")).sendKeys(searchText);
        findElementAfterWait(By.cssSelector(".fb__input-text__inner > input")).sendKeys(Keys.ENTER);
    }

    /**
     * 검색 결과로 나온 상품수를 확인한다.
     * @return 검색결과수
     */
    public static int getSearchResultCount() {
        String resultCount = getStringByClassID("fb__tabs__count");

        return Integer.parseInt(resultCount);
    }

    @Test
    @Order(1)
    void 검색_비로그인_테스트() {
        String searchText = "두부";
        qASearch(searchText);

        int resultCount = getSearchResultCount();
        System.out.println(searchText + " 검색결과 : " + resultCount);
        Assertions.assertTrue(resultCount > 1);

    }

    @Test
    @Order(2)
    void 검색_로그인회원_테스트() {
        TAMemberLogin taMemberLogin = new TAMemberLogin();

        taMemberLogin.QALogin();

        String searchText = "두부";

        qASearch(searchText);

        int resultCount = getSearchResultCount();

        System.out.println(searchText + " 검색결과 : " + resultCount);

        Assertions.assertTrue(resultCount > 1);

    }

    @Test
    void 검색_결과없음() {
        qASearch("말똥구리");

        WebElement searchResultNoItem = findElementAfterWait(By.className("search__result--noItem"));
        Assertions.assertNotNull(searchResultNoItem);
    }
}
