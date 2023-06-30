package geocoder.clients;


import geocoder.exceptions.GeolocationServerException;
import geocoder.model.Coordinates;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;



@QuarkusTest
@QuarkusTestResource(WiremockGeolocationAPIService.class)
public class MockGeolocationAPIServiceTest {

    private static final String CORRECT_ADDRESS = "kazan kremlevskaya 18";
    private static final String INCORRECT_ADDRESS = "dfjdfjfdk";
    private static final String ADDRESS_THROWS_EXCEPTION = "moscow";

    @Inject
    @RestClient
    GeolocationAPIService geolocationAPIService;

    @Test
    void testGetCoordinates() {
        Set<Coordinates> coordinates = new HashSet<>();
        Coordinates coordinate1 = new Coordinates(49.12976274048962, 55.7948636);
        coordinates.add(coordinate1);
        Assertions.assertNotNull(geolocationAPIService.getCoordinates(CORRECT_ADDRESS));
        Assertions.assertEquals(coordinates, geolocationAPIService.getCoordinates(CORRECT_ADDRESS));
        Assertions.assertFalse(geolocationAPIService.getCoordinates(CORRECT_ADDRESS).isEmpty());
    }
    @Test
    void testGetCoordinatesWhenResponseBodyIsEmpty() {
        Assertions.assertNull(geolocationAPIService.getCoordinates(INCORRECT_ADDRESS));
    }

    @Test
    void testToExceptionIfThrowsExceptionWhenAPIResponseIsNot200 () {
        Assertions.assertThrows(GeolocationServerException.class, () -> GeolocationAPIService.toException(
                Response.ok(
                        geolocationAPIService.getCoordinates(ADDRESS_THROWS_EXCEPTION)).build()));
    }
}
