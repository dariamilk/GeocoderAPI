package geocoder.resource;

import geocoder.clients.GeolocationAPIService;
import geocoder.exceptions.RemoteServerIsUnavailableException;
import geocoder.model.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;


import java.util.Set;
@ApplicationScoped
public class GeolocationService {


    @RestClient
    GeolocationAPIService geolocationAPIService;

    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoordinates (String address) {
        try {
            Set<Coordinates> coordinates = geolocationAPIService.getCoordinates(address);
            if (coordinates.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.ok(coordinates).build();
            }
        } catch (ProcessingException e) {
            throw new RemoteServerIsUnavailableException("Remote server is not responding.");
        }
    }

}
