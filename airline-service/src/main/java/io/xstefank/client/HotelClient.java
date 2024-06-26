package io.xstefank.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8083")
@Path("/hotel")
public interface HotelClient {

    @POST
    @Path("/book")
    void bookHotel(String id);
}
