package org.d0ms0n.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.d0ms0n.dto.TemperatureMeasurement;
import org.d0ms0n.repository.MeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class MeasurementService {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementService.class);

    private final MeasurementRepository measurementRepository;

    @Inject
    @Named("influxSampleRepository")
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public void createSample(TemperatureMeasurement measurement) {
        if (measurement == null){
            throw new IllegalArgumentException("measurement must not be null!");
        }
        logger.info("try to store measurement " + measurement.toString());
        this.measurementRepository.storeMeasurement(measurement);
    }

    public List<TemperatureMeasurement> getAllSamples() {
        logger.info("get all measurements");
        return this.measurementRepository.getAllMeasurements();
    }

    public List<TemperatureMeasurement> getMean(@Nullable String range, @Nullable String sensor) {
        ChronoUnit unit = ChronoUnit.YEARS;
        if (range != null) {
            switch (range) {
                case "h" -> unit = ChronoUnit.HOURS;
                case "d" -> unit = ChronoUnit.DAYS;
                case "m" -> unit = ChronoUnit.MONTHS;
            }
        }
        logger.info("get mean from measurements");
        return this.measurementRepository.getMean(-1L, unit, sensor);
    }
}
