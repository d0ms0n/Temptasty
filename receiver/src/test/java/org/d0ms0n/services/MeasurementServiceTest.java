package org.d0ms0n.services;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.d0ms0n.dto.TemperatureMeasurement;
import org.d0ms0n.repository.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class MeasurementServiceTest {
    TemperatureMeasurement meanSample = new TemperatureMeasurement("sensor1", "celsius", null, 150.0d);
    @Inject
    MeasurementService measurementService;
    @InjectMock
    MeasurementRepository measurementRepository;

    @Captor
    ArgumentCaptor<ChronoUnit> captor = ArgumentCaptor.forClass(ChronoUnit.class);

    @Test
    void getMeanWithRangeHourDoesCallHours() {
        when(measurementRepository.getMean(eq(-1L), eq(ChronoUnit.HOURS), eq("sensor1")))
                .thenReturn(Collections.singletonList(meanSample));
        List<TemperatureMeasurement> mean = measurementService.getMean("h", "sensor1");
        assertThat(mean.get(0), equalTo(meanSample));
        verify(measurementRepository).getMean(eq(-1L), captor.capture(), eq("sensor1"));
        assertThat(captor.getValue(), equalTo(ChronoUnit.HOURS));
    }

    @Test
    void getMeanWithoutRangeDoesCallYears() {
        when(measurementRepository.getMean(eq(-1L), eq(ChronoUnit.YEARS), eq("sensor1")))
                .thenReturn(Collections.singletonList(meanSample));
        List<TemperatureMeasurement> mean = measurementService.getMean(null, "sensor1");
        assertThat(mean.get(0), equalTo(meanSample));
        verify(measurementRepository).getMean(eq(-1L), captor.capture(), eq("sensor1"));
        assertThat(captor.getValue(), equalTo(ChronoUnit.YEARS));
    }
}
