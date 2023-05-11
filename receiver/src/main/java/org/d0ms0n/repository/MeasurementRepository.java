package org.d0ms0n.repository;

import org.d0ms0n.dto.TemperatureMeasurement;

import java.time.temporal.ChronoUnit;
import java.util.List;

public interface MeasurementRepository {

    void storeMeasurement(TemperatureMeasurement measurement);

    List<TemperatureMeasurement> getAllMeasurements();

    List<TemperatureMeasurement> getMean(long start, ChronoUnit unit, String sensor);

}
