package geocoder.resource;

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
public class GeolocationResourceTest {
    @InjectMock
    @RestClient
    GeolocationService geolocationService;

    @Test
    public void testGetCoordinates () {
        Set<Coordinates> coordinates = new HashSet<>();
        Coordinates coordinate1 = new Coordinates(49.12976274048962, 55.7948636);
        coordinates.add(coordinate1);
        Mockito.when(geolocationService.getCoordinates("казань жуковского 4")).thenReturn(coordinates);
        given()
                .pathParam("address", "казань жуковского 4")
                .when().get("/{address}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].lon", is(49.12976274048962F))
                .body("[0].lat", is(55.7948636F));
    }
    @Test
    public void testGetCoordinatesWhenResponseBodyIsEmpty () {
        Set<Coordinates> coordinates = new HashSet<>();
        Mockito.when(geolocationService.getCoordinates("djhvjffk")).thenReturn(coordinates);
        given()
                .pathParam("address", "djhvjffk")
                .when().get("/{address}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is("[]"));
    }
}
