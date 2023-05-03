package org.d0ms0n;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.d0ms0n.services.SampleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SampleResourceTest {

    @Inject
    SampleService sampleService;

    @BeforeAll
    public static void setup() {
        SampleService mock = Mockito.mock(SampleService.class);
        Mockito.when(mock.greet("Stuart")).thenReturn("A mock for Stuart");
        QuarkusMock.installMockForType(mock, SampleService.class);
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/samples")
                .then()
                .statusCode(200)
                .body(is("Hello RESTEasy"));
    }

}
