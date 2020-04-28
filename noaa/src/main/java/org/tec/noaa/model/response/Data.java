package org.tec.noaa.model.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.tec.noaa.rest.DataDateTimeDeserializer;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Data {
    @JsonDeserialize(using=DataDateTimeDeserializer.class)
    protected LocalDateTime date;
    protected String datatype;
    protected String station;
    protected String attributes;
    protected long value;
}
