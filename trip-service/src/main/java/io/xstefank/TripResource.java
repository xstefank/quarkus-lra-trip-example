package io.xstefank;

import io.xstefank.client.HotelClient;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/trip")
@ApplicationScoped
public class TripResource {

    @Inject
    @RestClient
    HotelClient hotelClient;

    @LRA
    @GET
    @Path("/book")
    public String bookTrip(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        logNicely("Booking new trip " + lraId);

        performBooking(lraId);

        return "Booking will be processed";
    }

    @AfterLRA
    @PUT
    @Path("/bookingProcessed")
    public Response bookingProcessed() {
        logNicely("Booking processed");
        return Response.ok().build();
    }

    private void performBooking(String id) {
        hotelClient.bookHotel(id);
    }

    private void logNicely(String value) {
        System.out.println("===============");
        System.out.println("value = " + value);
        System.out.println("===============");
        System.out.println();
    }
}
