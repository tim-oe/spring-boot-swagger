package org.tec.springbootswagger.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.tec.springbootswagger.service.VersionScv;

import java.io.InputStream;
import java.util.Properties;

@Service
public class VersionScvImpl implements VersionScv {

    @Value("classpath:version.properties")
    private transient Resource resourceFile;

    @Override
    public Properties getVersionInformation() {
        try(InputStream in = resourceFile.getInputStream()) {
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (Exception e) {
            throw new RuntimeException("failed to load version.properties", e);
        }
    }
}
