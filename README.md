# 개발 환경 구성
## java, git, intellij 설치
### 1. java 설치
+ https://www.oracle.com/java/technologies/downloads/
+ 위 페이지에서 자신의 OS에 맞는 최신 버전 설치
+ IntelliJ - Project Structure - Project SDK - Add SDK를 통해 설치해도 됨. JDK 16 설치 권고

### 2. git 설치 (소스코드 관리 도구)
+ https://www.lainyzine.com/ko/article/how-to-install-latest-version-of-git-on-windows-10/

### 3. 셀레늄 웹 드라이버 설치 
https://justinadpark.tistory.com/173

### 4. 본 프로젝트를 각자 PC에 clone(복제) 
+ windows 기준
    - 개인 업무용(개발용) PC에 프로젝트를 관리할 폴더 생성
    - Windows + R 엔터
    - cmd 엔터
    - cd {위에서 만든 폴더}
    - 폴더 변경 확인 : 만약 C: 드라이브가 아닌 다른 드라이브에 폴더를 만들었으면 해당 드라이브로 이동
      예) d: 엔터
    - clone https://github.com/Justin-ad-Park/selenium-Sample.git

### 5. QAPWD를 시스템 환경설정에 추가 
    + 추가 방법은 path 설정 방식과 동일
    + QAPWD 변수값에 비밀번호 등록

## git 사용 방법
### 클론 : 원격 저장소를 로컬에 복제해오는 명령
    git clone 원격_저장소_경로
### 브랜치 생성
    

# Selenium 개발 방법
## 1. 프로퍼티를 등록하는 방법
+ 셀레늄 테스트 프로퍼티란? 테스트에 사용되는 값으로 자주 사용되며, 테스트 상황에 따라 변경 가능성이 있는 값
+ application.yml 에  프로퍼티 등록이 가능하다.
+ 테스트에 사용할 프로파일 그룹은 spring.profiles.active의 값으로 지정한다.
```yaml
spring:
  profiles:
    active:
      - canshop   # 테스트 환경 선택
```
+ 프로퍼티 그룹은 하나 또는 그 이상의 프로파일을 등록할 수 있다.
```yaml
    group:  # 테스트 환경 선택
      qa:
        - common
        - qashop
      canary:
        - common
        - canshop
```    
    
+ 프로파일은 아래와 같이 --- 로 시작하며, spring.config.activate.on-profile에 이름을 지정한다.
```yaml
--- #qashop 설정
spring:
  config:
    activate:
      on-profile: "qashop"
```
+ 프로파일에는 여러 프로퍼티를 등록할 수 있다. 
    - 프로퍼티는 다음과 같이 등록한다.
        * 들여쓰기를 통해 계층(hierarchy)를 만들어 프로퍼티를 묶는다.
        * 값은 -에 넣거나, : 우측에 공백문자를 하나 입력한 후 값을 입력한다.
        * 문자열은 공백 인식 등을 위해 가급적 ""으로 묶는다. 
        * ,를 이용해 하나의 프로퍼티에 여러 값을 배열로 등록하는 것도 가능하다.
```yaml
shop:
  front-url: "https://qashop.pulmuone.online/"
  basketCoupon: 123,456,789
```

## 2. 프로퍼티의 선택
### 2-1. 프로퍼티 파일(application.yml)에 spring.profiles.active로 지정한다.    
```yaml
spring:
  profiles:
    active:
      - qa   # 테스트 환경 선택
``` 

### 2-2. 각 테스트 클래스에 @ActivePrifiles("{프로퍼티그룹명}") 어노테이션으로 지정 가능하다.
+ 이 경우 yml에 선언된 spring.prifiles.active 보다 우선순위가 높게 인식된다.
```java
@ActiveProfiles("qa")
@SpringBootTest(classes = SeleniumApplication.class)
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TAMemberLogin extends SeleniumSupporter {
```
+ SeleniumSupporter 클래스에 @ActiveProfiles("{프로퍼티그룹명}")를 지정하는 것도 가능하다.
    - [주의]SeleniumSupporter는 모든 테스트 클래스의 부모 클래스로 테스트 클래스에 선언된 @ActiveProfiles 보다 우선순위가 높게 인식된다.

## 3. 등록된 프로퍼티의 사용
+ static 변수에 자동으로 바인딩 시키는 방법
    - static 으로 변수를 선언한다.
        * 변수의 이름은 대문자와 언더스코어(_)로 만든다.
    - public void set메소드에 @value 어노테이션으로 프로퍼티에 정의된 값을 할당한다.
        * 어노테이션 지정 방식은 다음과 같다. - @value("${프로퍼티명}")
        * @value에 프로퍼티 이름을 지정할 때는 계층(들여쓰기, hierarchy)은 .으로 구분한다.
        * set 메소드명은 set + static 변수명의 각 언더스코어 사이의 문자를 첫글자만 대문자, 나머지 소문자로 붙여서 만든다.
        * 예를 들어 SHOP_FRONT_URL이면 메소드명은 setShopFrontUrl 로 만들어야 한다.
```yaml
shop:
  front-url: "https://canshop.pulmuone.online/"
  values: 123,456,789
```
```java
public class ZetcTest extends SeleniumSupporter {
    @Autowired
    Environment environment;

    //단일 값 바인딩 선언
    public static String SHOP_FRONT_URL;

    // 프로퍼티 바인딩
    @Value("${shop.front-url}")
    public void setShopFrontUrl(String value) {
        SHOP_FRONT_URL = value;
    }
```

* static 변수에 배열을 바인딩 시키는 방법
    - 배열은 문자열이면 List<String>, 정수면 List<Integer>로 선언한다.
```java
    public static List<Integer> VALUES;

    @Value("${shop.values}")
    public void setValues(List<Integer> values) {
        VALUES = values;
    }
```


# 4. 테스트 클래스 분류
* 테스트 클래스는 test.java.selenium 하위에 각 업무 도메인별 패키지에 구성한다.
