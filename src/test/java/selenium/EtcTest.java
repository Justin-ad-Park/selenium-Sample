package selenium;

import org.junit.jupiter.api.Test;

public class EtcTest {

    @Test
    void Test() {
        System.out.println(System.getenv("QAPWD"));
        System.out.println(System.getenv("PATH"));
    }
}
