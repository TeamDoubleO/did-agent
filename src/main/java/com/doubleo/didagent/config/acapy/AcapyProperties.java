package com.doubleo.didagent.config.acapy;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "acapy")
public record AcapyProperties(
        @NotBlank String url,
        @NotBlank String agentToken) {
}
