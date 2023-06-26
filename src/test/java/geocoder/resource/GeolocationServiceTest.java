package geocoder.resource;

import geocoder.model.Coordinates;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

@QuarkusTest
public class GeolocationServiceTest {

    @Inject
    @RestClient
    GeolocationService geolocationService;

    @Test
    public void testGetCoordinates() {
        Set<Coordinates> coordinates = new HashSet<>();
        Coordinates coordinate1 = new Coordinates(49.12976274048962, 55.7948636);
        coordinates.add(coordinate1);
        Assertions.assertNotNull(geolocationService.getCoordinates("казань жуковского 4"));
        Assertions.assertEquals(coordinates, geolocationService.getCoordinates("казань жуковского 4"));
        Assertions.assertFalse(geolocationService.getCoordinates("казань жуковского 4").isEmpty());
    }
    @Test
    public void testGetCoordinatesWhenResponseBodyIsEmpty() {
        Assertions.assertTrue(geolocationService.getCoordinates("апоаплродвоар").isEmpty());
    }
}