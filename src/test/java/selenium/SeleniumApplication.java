package selenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;

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
 *  Zsh :
 *      echo 'export PATH=$PATH:/{bin}' >> ~/.zshrc
 *      source ~/.zshrc
 *
 *  Bash :
 *      echo 'export PATH=$PATH:/{설치경로}' >> ~/.bash_profile
 *      source ~/.bash_profile
 *
 * 4. 드라이버를 실행해서 경로, 권한 확인
 *  >>chromedirver
 *  </pre>
 * */
//@ActiveProfiles("canary")
@ConfigurationPropertiesScan("selenium")
@SpringBootTest
public class SeleniumApplication {

    public static void main(final String[] args)
    {
        SpringApplication.run(SeleniumApplication.class, args);
    }

}
