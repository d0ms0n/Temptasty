package org.d0ms0n.services;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.exceptions.InfluxException;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.MeanFlux;
import com.influxdb.query.dsl.functions.RangeFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.d0ms0n.dto.Sample;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class SampleService implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);
    public static final Long DATA_RETENTION_DAYS = -14L;
    private InfluxDBClient influxDBClient;

    @ConfigProperty(name = "influxdb.connectionUrl")
    String connectionUrl;
    @ConfigProperty(name = "influxdb.token")
    String token;
    @ConfigProperty(name = "influxdb.orgId")
    String orgId;
    @ConfigProperty(name = "influxdb.data.bucketId")
    String bucketId;
    @ConfigProperty(name = "influxdb.data.bucketName")
    String bucketName;

    public SampleService() {
    }

    @PostConstruct
    private void initializeInfluxDBClient() {
        logger.info("Connecting to: {}, token: {}, org: {}, bucketId: {}",
                connectionUrl, token, orgId, bucketId);
        this.influxDBClient = InfluxDBClientFactory.create(connectionUrl, token.toCharArray(), orgId, bucketId);
    }

    @Override
    public void close() throws Exception {
        this.influxDBClient.close();
    }


    public void createSample(Sample sample) throws InfluxException {
        WriteApi writeApi = influxDBClient.getWriteApi();
        writeApi.writeMeasurement(bucketName, orgId, WritePrecision.NS, sample);
        writeApi.close();

    }

    public List<Sample> getAllSamples() throws InfluxException {
        String temperatureByTimeQuery = Flux.from(bucketName)
                .range(-1L, ChronoUnit.YEARS)
                .toString();
        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(temperatureByTimeQuery, Sample.class);
    }

    public List<Sample> getMean(String range, String sensor) throws InfluxException {
        Flux flux = Flux.from(bucketName);
        RangeFlux rangeFlux = null;
        MeanFlux meanFlux;

        if (range != null) {
            switch (range) {
                case "h" -> rangeFlux = flux.range(-1L, ChronoUnit.HOURS);
                case "d" -> rangeFlux = flux.range(-1L, ChronoUnit.DAYS);
                case "m" -> rangeFlux = flux.range(-1L, ChronoUnit.MONTHS);
                default -> rangeFlux = flux.range(-1L, ChronoUnit.YEARS);
            }
        }

        if (rangeFlux == null) {
            throw new IllegalArgumentException("range could not be parsed");
        }

        if (sensor != null) {
            meanFlux = rangeFlux.filter(Restrictions.column("name").equal(sensor)).mean();
        } else {
            meanFlux = rangeFlux.mean();
        }

        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(meanFlux.toString(), Sample.class);
    }
}
