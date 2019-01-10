package com.outboundengine.contacthygien.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Status {
    String apiVersion;
    String datasourceClass;
    String databaseVersion;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getDatasourceClass() {
        return datasourceClass;
    }

    public void setDatasourceClass(String datasourceClass) {
        this.datasourceClass = datasourceClass;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(String databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
