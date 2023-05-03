package org.d0ms0n.repository;

import org.d0ms0n.dto.Sample;

import java.time.temporal.ChronoUnit;
import java.util.List;

public interface SampleRepository {

    void storeSample(Sample sample);

    List<Sample> getAllSamples();

    List<Sample> getMean(long start, ChronoUnit unit, String sensor);

}
