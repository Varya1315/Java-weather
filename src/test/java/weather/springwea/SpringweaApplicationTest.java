package weather.springwea;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class SpringweaApplicationTest {



        @Autowired
        private ApplicationContext context;

        @Test
        public void contextLoads() {
            try {
                // Проверяем, что контекст приложения загружается успешно,
                // что означает, что все бины и конфигурации настроены правильно.
                assertThat(context).isNotNull();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


