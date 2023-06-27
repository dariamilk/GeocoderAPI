package geocoder.resource;

import geocoder.clients.GeolocationService;
import geocoder.model.Coordinates;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashSet;
import java.util.Set;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
 class GeolocationResourceTest {

    private static final String CORRECT_ADDRESS = "казань жуковского 4";
    private static final String INCORRECT_ADDRESS = "djhvjffk";
    @InjectMock
    @RestClient
    GeolocationService geolocationService;

    @Test
    void testGetCoordinates () {
        Set<Coordinates> coordinates = Set.of(new Coordinates(49.12976274048962F, 55.7948636F));
        Mockito.when(geolocationService.getCoordinates(CORRECT_ADDRESS)).thenReturn(coordinates);
        given()
                .pathParam("address", CORRECT_ADDRESS)
                .when().get("/{address}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].lon", is(49.12976274048962F))
                .body("[0].lat", is(55.7948636F));
    }
    @Test
    void testGetCoordinatesWhenResponseBodyIsEmpty () {
        Set<Coordinates> coordinates = new HashSet<>();
        Mockito.when(geolocationService.getCoordinates(INCORRECT_ADDRESS)).thenReturn(coordinates);
        given()
                .pathParam("address", INCORRECT_ADDRESS)
                .when().get("/{address}")
                .then()
                .statusCode(404);
    }
}
