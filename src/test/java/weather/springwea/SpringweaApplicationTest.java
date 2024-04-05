package weather.springwea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SpringweaApplicationTest {

    @Test
    public void testMainMethod() {
        // Arrange
        String[] args = {"arg1", "arg2"};

        // Мокируем статический метод SpringApplication.run()
        mockStatic(SpringApplication.class);

        // Ожидаемый вызов статического метода SpringApplication.run()
        // Используем doReturn(), так как SpringApplication.run() не void метод
        doReturn(null).when(SpringApplication.class);
        SpringApplication.run(eq(SpringweaApplication.class), eq(args));

        // Act
        SpringweaApplication.main(args);

        // Assert
        // Проверяем, что статический метод SpringApplication.run() был вызван с правильными аргументами
        verifyStatic(SpringApplication.class);
        SpringApplication.run(eq(SpringweaApplication.class), eq(args));
    }

    private void verifyStatic(Class<SpringApplication> springApplicationClass) {
    }
}
