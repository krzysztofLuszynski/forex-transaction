package forex.transaction.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private final App app = new App();

    @Test
    void appHasAGreeting() {
        assertThat(app.getGreeting()).isEqualTo("Hello World!");
    }
}
