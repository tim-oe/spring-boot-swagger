package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class BaseNoaa {
    private String id;
    private String name;
}
