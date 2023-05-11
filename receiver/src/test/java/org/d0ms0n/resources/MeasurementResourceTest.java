package org.d0ms0n.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.d0ms0n.dto.TemperatureMeasurement;
import org.d0ms0n.services.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;

@QuarkusTest
public class MeasurementResourceTest {

    TemperatureMeasurement measurement = new TemperatureMeasurement("sensor1", "celsius", Instant.now(), 100.0d);
    TemperatureMeasurement meanSample = new TemperatureMeasurement("sensor1", "celsius", null, 150.0d);
    @InjectMock
    MeasurementService measurementService;

    @BeforeEach
    public void setup() {
        Mockito.when(measurementService.getAllSamples())
                .thenReturn(Collections.singletonList(measurement));
        Mockito.when(measurementService.getMean("h", "sensor1"))
                .thenReturn(Collections.singletonList(meanSample));
    }

    @Test
    public void testGetAllSamples() {
        given()
                .accept(ContentType.JSON)
                .when().get("/measurements")
                .then()
                .statusCode(200)
                .body("name", hasItem("sensor1"));
    }

    @Test
    public void testWriteSample() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(measurement)
                .when().post("/measurements")
                .then()
                .statusCode(201)
                .body("name", equalTo("sensor1"));

        Mockito.verify(measurementService).createSample(refEq(measurement));
    }

    @Test
    public void testGetMean() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("range", "h")
                .param("sensor", "sensor1")
                .when().get("/measurements/mean")
                .then()
                .statusCode(200)
                .body("name", hasItem("sensor1"));

        Mockito.verify(measurementService).getMean(eq("h"), eq("sensor1"));
    }
}
