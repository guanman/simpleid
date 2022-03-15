package com.guanman.simple.plugin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "segment")
@PropertySource("classpath:simple.properties")
public class SegmentIdProperties {

    private String url;

    private String username;

    private String password;
}
