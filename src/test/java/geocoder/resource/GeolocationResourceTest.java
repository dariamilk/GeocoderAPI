package geocoder.resource;

import geocoder.exceptions.RemoteServerIsUnavailableException;
import geocoder.model.Coordinates;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Set;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
 class GeolocationResourceTest {

    private static final String CORRECT_ADDRESS = "казань жуковского 4";
    private static final String INCORRECT_ADDRESS = "djhvjffk";
    private static final String ADDRESS_THROWS_EXCEPTION = "moscow";
    @InjectMock
    GeolocationService geolocationService;

    @Test
    void testGetCoordinates () {
        Set<Coordinates> coordinates = Set.of(new Coordinates(49.12976274048962F, 55.7948636F));
        Mockito.when(geolocationService.getCoordinates(CORRECT_ADDRESS)).thenReturn(Response.ok(coordinates).build());
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
        Mockito.when(geolocationService.getCoordinates(INCORRECT_ADDRESS)).thenReturn(Response.status(Response.Status.NO_CONTENT).build());
        given()
                .pathParam("address", INCORRECT_ADDRESS)
                .when().get("/{address}")
                .then()
                .statusCode(204);
    }
    @Test
    void mapExceptionTest () {
        Mockito.when(geolocationService.getCoordinates(ADDRESS_THROWS_EXCEPTION)).thenThrow(RemoteServerIsUnavailableException.class);
        given()
                .pathParam("address", ADDRESS_THROWS_EXCEPTION)
                .when().get("/{address}")
                .then()
                .statusCode(404);
    }
}
