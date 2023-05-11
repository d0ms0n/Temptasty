package org.d0ms0n.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.d0ms0n.dto.TemperatureMeasurement;
import org.d0ms0n.services.MeasurementService;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@Path("/measurements")
public class MeasurementResource {

    private static final Logger logger = LoggerFactory.getLogger(MeasurementResource.class);
    private final MeasurementService measurementService;

    @Inject
    public MeasurementResource(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSamples() {
        logger.info("getAllSamples called");
        return Response.ok(measurementService.getAllSamples()).build();
    }

    @GET
    @Path("mean")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMean(
            @Parameter(description = "you can use h for last hour, d for last day and m for last month")
            @QueryParam("range") String range,
            @Parameter(description = "sensor1 or sensor2")
            @QueryParam("sensor") String sensor) {
            logger.info("getMean called");
        return Response.ok(measurementService.getMean(range, sensor)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response writeSample(@Valid TemperatureMeasurement measurement) {
        logger.info("writeSample called");
        measurementService.createSample(measurement);
        return Response.status(CREATED).entity(measurement).build();
    }
}
