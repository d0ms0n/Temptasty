package org.d0ms0n.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.d0ms0n.dto.Sample;
import org.d0ms0n.repository.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class SampleService {
    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    private final SampleRepository sampleRepository;

    @Inject
    @Named("influxSampleRepository")
    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public void createSample(Sample sample) {
        if (sample == null){
            throw new IllegalArgumentException("sample must not be null!");
        }
        logger.info("try to store sample " + sample.toString());
        this.sampleRepository.storeSample(sample);
    }

    public List<Sample> getAllSamples() {
        logger.info("get all samples");
        return this.sampleRepository.getAllSamples();
    }

    public List<Sample> getMean(@Nullable String range, @Nullable String sensor) {
        ChronoUnit unit = ChronoUnit.YEARS;
        if (range != null) {
            switch (range) {
                case "h" -> unit = ChronoUnit.HOURS;
                case "d" -> unit = ChronoUnit.DAYS;
                case "m" -> unit = ChronoUnit.MONTHS;
            }
        }
        logger.info("get mean from samples");
        return this.sampleRepository.getMean(-1L, unit, sensor);
    }
}
