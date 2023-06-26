package geocoder.resource;

import geocoder.model.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Set;

@Path("/search")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey ="geocoder-api")
public interface GeolocationService {
    @GET
    Set<Coordinates> getCoordinates(@QueryParam("q") String address);
}
