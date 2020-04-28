package org.tec.noaa.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseNoaa {
    private String id;
    private String name;

    protected BaseNoaa() {
        //default ctor
    }
}
