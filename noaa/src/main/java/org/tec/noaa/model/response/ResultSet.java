package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * noaa rest response resultset
 */
@Getter
@Setter
@ToString
public class ResultSet {
    private long offset;
    private long count;
    private long limit;
}
