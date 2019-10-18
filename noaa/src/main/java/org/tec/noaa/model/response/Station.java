package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.tec.noaa.enums.ElevationUnit;

/**
 * noaa rest station
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Station extends BaseNoaaDateCoverage{
    private long elevation;
    private ElevationUnit elevationUnit;
    private double latitude;
    private double longitude;
}
