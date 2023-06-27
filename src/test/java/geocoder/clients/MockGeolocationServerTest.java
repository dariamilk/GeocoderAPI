package geocoder.clients;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;


@QuarkusTest
@QuarkusTestResource(WiremockGeolocationService.class)
public class MockGeolocationServerTest {
    @Test
    void testGetCoordinatesResponseStatusCodeWhenAPIIsUnavailable () {
        given()
                .pathParam("address", "moscow")
                .when().get("/{address}")
                .then()
                .log()
                .ifError()
                .assertThat()
                .statusCode(500);
    }
}
