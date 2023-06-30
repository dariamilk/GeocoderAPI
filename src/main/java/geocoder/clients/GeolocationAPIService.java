package geocoder.clients;

import geocoder.exceptions.GeolocationServerException;
import geocoder.model.Coordinates;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Set;

@Path("/search")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey ="geocoder-api")
public interface GeolocationAPIService {
    @GET
    Set<Coordinates> getCoordinates(@QueryParam("q") String address);

    @ClientExceptionMapper
     static RuntimeException toException (Response response) {
        if (response.getStatus() != 200) {
            return new GeolocationServerException("The remote service returned error");
        }
        return null;
    }
}
