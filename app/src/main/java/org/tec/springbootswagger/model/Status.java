package org.tec.springbootswagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Status {
    String apiVersion;
    String datasourceClass;
    String databaseVersion;
}
