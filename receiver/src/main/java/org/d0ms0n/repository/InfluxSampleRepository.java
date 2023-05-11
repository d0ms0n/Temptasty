package org.d0ms0n.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.MeanFlux;
import com.influxdb.query.dsl.functions.RangeFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.d0ms0n.dto.Sample;
import org.d0ms0n.services.SampleService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
@Named("influxSampleRepository")
public class InfluxSampleRepository implements SampleRepository, AutoCloseable {
    @ConfigProperty(name = "influxdb.connectionUrl")
    String connectionUrl;
    @ConfigProperty(name = "influxdb.token")
    String token;
    @ConfigProperty(name = "influxdb.orgId")
    String orgId;
    @ConfigProperty(name = "influxdb.data.bucketName")
    String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);
    private InfluxDBClient influxDBClient;


    @PostConstruct
    void initializeInfluxDBClient() {
        logger.info("Connecting to: {}, token: {}, org: {}, bucket: {}",
                connectionUrl, token, orgId, bucketName);
        this.influxDBClient = InfluxDBClientFactory.create(connectionUrl, token.toCharArray(), orgId, bucketName);
    }


    public InfluxSampleRepository() {
    }

    @Override
    public void storeSample(Sample sample) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writeMeasurement(bucketName, orgId, WritePrecision.NS, sample);
    }

    @Override
    public List<Sample> getAllSamples() {
        String temperatureByTimeQuery = Flux.from(bucketName)
                .range(-1L, ChronoUnit.YEARS)
                .toString();
        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(temperatureByTimeQuery, Sample.class);
    }

    @Override
    public List<Sample> getMean(long start, ChronoUnit unit, String sensor) {
        Flux flux = Flux.from(bucketName);
        RangeFlux rangeFlux = flux.range(start, unit);
        MeanFlux meanFlux;

        if (sensor != null) {
            meanFlux = rangeFlux.filter(Restrictions.column("name").equal(sensor)).mean();
        } else {
            meanFlux = rangeFlux.mean();
        }

        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(meanFlux.toString(), Sample.class);
    }

    @Override
    public void close() {
        this.influxDBClient.close();
    }
}
