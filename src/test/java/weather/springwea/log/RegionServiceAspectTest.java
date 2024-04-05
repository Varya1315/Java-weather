package weather.springwea.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import weather.springwea.log.RegionServiceAspect;
import weather.springwea.model.Towns;

import java.util.ArrayList;
import java.util.List;

import static javax.management.Query.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegionServiceAspectTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private RegionServiceAspect regionServiceAspect;


    private String eq(String regionName) {
        return regionName;
    }

}
