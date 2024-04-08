package io.xstefank;

import io.xstefank.client.CarRentalClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hotel")
@ApplicationScoped
public class HotelResource {

    @Inject
    @RestClient
    CarRentalClient carRentalClient;

    @LRA(value = LRA.Type.MANDATORY, end = false)
    @POST
    @Path("/book")
    public Response bookHotel(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        logNicely("Booking hotel for " + lraId);

        // propagate the saga to the car rental after the hotel is booked
        try {
            carRentalClient.bookCar(lraId);
        } catch (Throwable throwable) {
            // intentionally empty
        }

        return Response.ok().build();
    }

    @Compensate
    @PUT
    @Path("/compensate")
    public Response compensateHotel(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        logNicely("Compensate for hotel " + lraId);

        return Response.ok().build();
    }

    @Complete
    @PUT
    @Path("/complete")
    public Response completeHotel(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        logNicely("Complete for hotel " + lraId);

        return Response.ok().build();
    }

    private void logNicely(String value) {
        System.out.println("===============");
        System.out.println("value = " + value);
        System.out.println("===============");
        System.out.println();
    }
}
