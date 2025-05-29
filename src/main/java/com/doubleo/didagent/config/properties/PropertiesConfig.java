package com.doubleo.didagent.config.properties;

import com.doubleo.didagent.config.acapy.AcapyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({AcapyProperties.class})
@Configuration
public class PropertiesConfig {}
