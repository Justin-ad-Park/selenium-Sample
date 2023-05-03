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
- canshop   # 테스트 환경 선택
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
  etcvalue: 123,456,789
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
    public static List<Integer> ETC_TEST_VALUE;

    @Value("${shop.etcvalue}")
    public void setEtcTestValue(List<Integer> value) {
        ETC_TEST_VALUE = value;
    }
```


# 4. 테스트 클래스 분류
* 테스트 클래스는 test.java.selenium 하위에 각 업무 도메인별 패키지에 구성한다.
