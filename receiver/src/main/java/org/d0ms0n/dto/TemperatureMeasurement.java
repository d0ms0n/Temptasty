package org.d0ms0n.dto;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import jakarta.validation.constraints.*;

import java.time.Instant;

@Measurement(name = "measurement")
public class TemperatureMeasurement {

    @Column(tag = true)
    @Pattern(regexp = "celsius")
    private String unit;

    @Column(tag = true)
    @NotBlank
    private String name;

    @Column(timestamp = true)
    private Instant time;

    @Column
    @DecimalMin(value = "10.0")
    @DecimalMax(value = "300.0")
    @Digits(integer = 3, fraction = 2)
    private double value;

    public TemperatureMeasurement(String name, String unit, Instant time, double value) {
        this.name = name;
        this.unit = unit;
        this.time = time;
        this.value = value;
    }

    public TemperatureMeasurement() {
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant created) {
        this.time = created;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TemperatureMeasurement{" +
                "unit='" + unit + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", value=" + value +
                '}';
    }
}
