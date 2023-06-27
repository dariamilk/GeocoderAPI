package geocoder.clients;
import geocoder.model.Coordinates;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;


@QuarkusTest
    class GeolocationServiceTest {
    private static final String CORRECT_ADDRESS = "kazan kremlevskaya 18";
    private static final String INCORRECT_ADDRESS = "dfjdfjfdk";
    private static final String ADDRESS_THROWS_EXCEPTION = "moscow";

    @Inject
    @RestClient
    GeolocationService geolocationService;

    @Test
    void testGetCoordinates() {
        Set<Coordinates> coordinates = new HashSet<>();
        Coordinates coordinate1 = new Coordinates(49.12976274048962, 55.7948636);
        coordinates.add(coordinate1);
        Assertions.assertNotNull(geolocationService.getCoordinates(CORRECT_ADDRESS));
        Assertions.assertEquals(coordinates, geolocationService.getCoordinates(CORRECT_ADDRESS));
        Assertions.assertFalse(geolocationService.getCoordinates(CORRECT_ADDRESS).isEmpty());
    }
    @Test
    void testGetCoordinatesWhenResponseBodyIsEmpty() {
        Assertions.assertNull(geolocationService.getCoordinates(INCORRECT_ADDRESS));
    }

    @Test
    void testToExceptionIfThrowsExceptionWhenAPIIsUnavailable () {
        Assertions.assertThrows(RuntimeException.class, () -> GeolocationService.toException(
                Response.ok(
                        geolocationService.getCoordinates(ADDRESS_THROWS_EXCEPTION)).build()));
    }

}