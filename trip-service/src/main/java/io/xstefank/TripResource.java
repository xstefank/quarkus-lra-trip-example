package io.xstefank;

import io.xstefank.client.AirlineClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/trip")
@ApplicationScoped
public class TripResource {

    @Inject
    @RestClient
    AirlineClient airlineClient;

    @LRA
    @GET
    @Path("/book")
    public Response bookTrip(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
                           @RestQuery boolean fail) {
        logNicely("Booking new trip " + lraId);

        try {
            airlineClient.bookFlight(lraId);
        } catch (Throwable throwable) {
            // intentionally empty
        }

        if (fail) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok("Booking will be processed").build();
    }

    @LRA(end = true)
    @GET
    @Path("/end-trip")
    public Response endTripBooking(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        logNicely("Ending booking for " + lraId);

        return Response.ok().build();
    }

    @AfterLRA
    @PUT
    @Path("/bookingProcessed")
    public Response bookingProcessed(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) String lraId, LRAStatus status) {
        logNicely("Booking processed " + lraId + ", " + status);
        return Response.ok().build();
    }

    private void logNicely(String value) {
        System.out.println("===============");
        System.out.println("value = " + value);
        System.out.println("===============");
        System.out.println();
    }
}
