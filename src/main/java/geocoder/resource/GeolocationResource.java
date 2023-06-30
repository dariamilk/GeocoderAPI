package geocoder.resource;

import geocoder.exceptions.RemoteServerIsUnavailableException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Set;

@Path("")
public class GeolocationResource {

    @Inject
    GeolocationService geolocationService;

    @GET
    @Path("/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoordinates (@PathParam("address") String address) {
        return geolocationService.getCoordinates(address);
    }
    @ServerExceptionMapper
    public RestResponse<String> mapException(RemoteServerIsUnavailableException x) {
        return RestResponse.status(Response.Status.NOT_FOUND, x.getMessage());
    }
}
