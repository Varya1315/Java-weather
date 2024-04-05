package weather.springwea;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringweaApplicationTests {

    @Test
    public void contextLoads() {
        // Проверяем, что контекст приложения загружается успешно,
        // что означает, что все бины и конфигурации настроены правильно.
    }

    @Test
    public void applicationStarts() {
        // Проверяем, что приложение успешно запускается без ошибок.
        SpringweaApplication.main(new String[] {});
    }
}
