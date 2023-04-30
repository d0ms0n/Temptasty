package org.d0ms0n.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Instant;

import org.d0ms0n.dto.*;
import org.d0ms0n.services.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/samples")
public class SampleResource {

    private static final Logger logger = LoggerFactory.getLogger(SampleResource.class);
    private SampleService sampleService;

    @Inject
    public SampleResource(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSamples() {
        return Response.ok(sampleService.getAllSamples()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response writeSample(@Valid Sample sample) {
        sampleService.createSample(sample);
        return Response.ok(sample).build();
    }
}
