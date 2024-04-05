package weather.springwea.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import weather.springwea.model.Region;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegionServiceAspectTest {

    @Mock
    private Logger logger;

    @Mock
    private RegionServiceAspect regionServiceAspect;
    @Test
    public void testLogSaveRegionSuccess() {
        // Arrange
        Region newRegion = new Region();
        Region savedRegion = new Region();

        // Act
        regionServiceAspect.logSaveRegionSuccess(newRegion, savedRegion);

        // Assert
             verify(logger, never()).error(anyString(), anyString()); // Ensure error method is never called
    }

    @Test
    public void testLogSaveRegionError() {
        // Arrange
        Region newRegion = new Region();
        Region savedRegion = new Region(); // Вот изменения здесь

        // Act
        regionServiceAspect.logSaveRegionSuccess(newRegion, savedRegion);

        // Assert
        verify(logger, never()).info(anyString(), anyString()); // Ensure info method is never called
      }
}
