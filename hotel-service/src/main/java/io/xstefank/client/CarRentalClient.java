package io.xstefank.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8084")
@Path("/car")
public interface CarRentalClient {

    @POST
    @Path("/book")
    void bookCar(String id);
}
