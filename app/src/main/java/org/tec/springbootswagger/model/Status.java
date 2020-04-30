package org.tec.springbootswagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Properties;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Status {
    private String appName;
    private Properties versionInformation;
}
