package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * noaa rest response
 */
@Getter
@Setter
@ToString
public class Response<T> {
    Metadata metadata;
    List<T> results;
}
