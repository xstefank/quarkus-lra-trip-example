package io.xstefank.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8082")
@Path("/airline")
public interface AirlineClient {

    @POST
    @Path("/book")
    void bookFlight(String id);
}
