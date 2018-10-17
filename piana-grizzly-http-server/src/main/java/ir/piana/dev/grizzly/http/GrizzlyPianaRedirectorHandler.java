package ir.piana.dev.grizzly.http;

import org.glassfish.grizzly.http.util.Header;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * @author Mohammad Rahmati, 10/17/2018
 */
@Path("")
public class GrizzlyPianaRedirectorHandler {
    @GET
    public Response get(String path) {
        return Response.status(301)
                .header(Header.Location.name(), "https://localhost:8443/")
                .build();
    }

    @POST
    public Response post() {
        return Response.status(301)
                .header(Header.Location.name(), "https://localhost:8443")
                .build();
    }

    @PUT
    public Response put() {
        return Response.status(301)
                .header(Header.Location.name(), "https://localhost:8443")
                .build();
    }

    @DELETE
    public Response delete() {
        return Response.status(301)
                .header(Header.Location.name(), "https://localhost:8443")
                .build();
    }

    @HEAD
    public Response head() {
        return Response.status(301)
                .header(Header.Location.name(), "https://localhost:8443")
                .build();
    }

    @OPTIONS
    public Response options() {
        return Response.status(301)
                .header(Header.Location.name(), "https://localhost:8443")
                .build();
    }
}
