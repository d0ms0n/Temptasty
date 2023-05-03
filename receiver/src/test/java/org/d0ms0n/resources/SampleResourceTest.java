package org.d0ms0n.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.d0ms0n.dto.Sample;
import org.d0ms0n.services.SampleService;
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
public class SampleResourceTest {

    Sample sample = new Sample("sensor1", "celsius", Instant.now(), 100.0d);
    Sample meanSample = new Sample("sensor1", "celsius", null, 150.0d);
    @InjectMock
    SampleService sampleService;

    @BeforeEach
    public void setup() {
        Mockito.when(sampleService.getAllSamples())
                .thenReturn(Collections.singletonList(sample));
        Mockito.when(sampleService.getMean("h", "sensor1"))
                .thenReturn(Collections.singletonList(meanSample));
    }

    @Test
    public void testGetAllSamples() {
        given()
                .accept(ContentType.JSON)
                .when().get("/samples")
                .then()
                .statusCode(200)
                .body("name", hasItem("sensor1"));
    }

    @Test
    public void testWriteSample() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(sample)
                .when().post("/samples")
                .then()
                .statusCode(201)
                .body("name", equalTo("sensor1"));

        Mockito.verify(sampleService).createSample(refEq(sample));
    }

    @Test
    public void testGetMean() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("range", "h")
                .param("sensor", "sensor1")
                .when().get("/samples/mean")
                .then()
                .statusCode(200)
                .body("name", hasItem("sensor1"));

        Mockito.verify(sampleService).getMean(eq("h"), eq("sensor1"));
    }
}
