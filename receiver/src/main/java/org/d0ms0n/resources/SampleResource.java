package org.d0ms0n.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.d0ms0n.dto.Sample;
import org.d0ms0n.services.SampleService;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/samples")
public class SampleResource {

    private static final Logger logger = LoggerFactory.getLogger(SampleResource.class);
    private final SampleService sampleService;

    @Inject
    public SampleResource(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSamples() {
        return Response.ok(sampleService.getAllSamples()).build();
    }

    @GET
    @Path("mean")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMean(
            @Parameter(description = "you can use h for last hour, d for last day and m for last month")
            @QueryParam("range") String range,
            @Parameter(description = "sensor1 or sensor2")
            @QueryParam("sensor") String sensor) {
        return Response.ok(sampleService.getMean(range, sensor)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response writeSample(@Valid Sample sample) {
        sampleService.createSample(sample);
        return Response.ok(sample).build();
    }
}
