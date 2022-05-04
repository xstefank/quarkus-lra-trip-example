package io.xstefank;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/hotel")
@ApplicationScoped
public class HotelResource {

    @LRA(value = LRA.Type.MANDATORY,end = false)
    @POST
    @Path("/book")
    public Response bookHotel(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        logNicely("Booking hotel for " + lraId);

        return Response.status(500).build();
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
