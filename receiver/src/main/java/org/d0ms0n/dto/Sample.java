package org.d0ms0n.dto;

import java.time.Instant;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Measurement(name = "sample")
public class Sample {

    @Column(tag = true)
    @Pattern(regexp = "celsius|fahrenheit")
    private String unit;

    @Column(tag = true)
    @NotBlank
    private String name;

    @Column(timestamp = true)
    private Instant timestamp;

    @Column
    @DecimalMin(value = "-15.0")
    @DecimalMax(value = "45.0")
    @Digits(integer=2, fraction=2)
    private float value;

    public Sample(String name, String unit, Instant timestamp, float value) {
        this.name = name;
        this.unit = unit;
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant created) {
        this.timestamp = created;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}