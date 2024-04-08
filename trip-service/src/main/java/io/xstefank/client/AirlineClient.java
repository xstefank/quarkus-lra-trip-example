package io.xstefank.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8082")
@Path("/airline")
public interface AirlineClient {

    @POST
    @Path("/book")
    void bookFlight(String id);
}
