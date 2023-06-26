package geocoder.resource;

import geocoder.model.Coordinates;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Set;

@Path("")
public class GeolocationResource {

    @RestClient
    GeolocationService geolocationService;

    @GET
    @Path("/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoordinates (@PathParam("address") String address) {
        Set<Coordinates> coordinates = geolocationService.getCoordinates(address);
        return Response.ok(coordinates).build();
    }
}
