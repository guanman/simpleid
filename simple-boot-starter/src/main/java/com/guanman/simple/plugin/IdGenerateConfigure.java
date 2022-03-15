package com.guanman.simple.plugin;

import com.guanman.simple.factory.impl.IdGeneratorFactoryServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass(IdGeneratorFactoryServer.class)
@EnableConfigurationProperties(SegmentIdProperties.class)
public class IdGenerateConfigure {

    @Autowired
    private SegmentIdProperties properties;

    @Bean
    @ConditionalOnMissingBean(IdGeneratorFactoryServer.class)
    IdGeneratorFactoryServer idGeneratorFactoryServer() {
        return new IdGeneratorFactoryServer(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }
}
