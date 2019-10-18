package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Data {
    String date;
    String datatype;
    String station;
    List<String> attributes;
    long value;
 }
