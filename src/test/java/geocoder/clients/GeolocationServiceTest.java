package geocoder.clients;
import geocoder.exceptions.RemoteServerIsUnavailableException;
import geocoder.model.Coordinates;
import geocoder.resource.GeolocationService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashSet;
import java.util.Set;



@QuarkusTest
    class GeolocationServiceTest {
    private static final String CORRECT_ADDRESS = "казань жуковского 4";
    private static final String INCORRECT_ADDRESS = "djhvjffk";

    @Inject
    GeolocationService geolocationService;
    @InjectMock
    @RestClient
    GeolocationAPIService geolocationAPIService;

    @Test
    void getCoordinatesOK () {
        Set<Coordinates> coordinates = Set.of(new Coordinates(49.12976274048962F, 55.7948636F));
        Mockito.when(geolocationAPIService.getCoordinates(CORRECT_ADDRESS)).thenReturn(coordinates);
        Assertions.assertEquals(Response.ok(coordinates).build().getEntity(), geolocationService.getCoordinates(CORRECT_ADDRESS).getEntity());
        Assertions.assertEquals(200, geolocationService.getCoordinates(CORRECT_ADDRESS).getStatus());
    }

    @Test
    void getCoordinatesKO () {
        Set<Coordinates> coordinates = new HashSet<>();
        Mockito.when(geolocationAPIService.getCoordinates(INCORRECT_ADDRESS)).thenReturn(coordinates);
        Assertions.assertEquals(Response.noContent().build().getEntity(), geolocationService.getCoordinates(INCORRECT_ADDRESS).getEntity());
        Assertions.assertEquals(204, geolocationService.getCoordinates(INCORRECT_ADDRESS).getStatus());
    }
    @Test
    void getCoordinatesThrowsException () {
        Mockito.when(geolocationAPIService.getCoordinates("")).thenThrow(ProcessingException.class);
        Assertions.assertThrows(RemoteServerIsUnavailableException.class, () -> geolocationService.getCoordinates(""));
    }
}