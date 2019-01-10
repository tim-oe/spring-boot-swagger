package com.outboundengine.contacthygien.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value="file:${user.home}/.OutboundEngine.properties", ignoreResourceNotFound = true)
})
public class OEDataSource {
}
