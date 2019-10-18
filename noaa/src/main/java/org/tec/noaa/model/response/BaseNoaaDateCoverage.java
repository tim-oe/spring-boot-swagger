package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
public abstract class BaseNoaaDateCoverage extends BaseNoaa {
    //@JsonSerialize(using = LocalDateToStringSerializer.class)
    //@JsonDeserialize(using = StringToLocalDateDeserializer.class)
    private LocalDate mindate;
    private LocalDate maxdate;
    private double datacoverage;
}
