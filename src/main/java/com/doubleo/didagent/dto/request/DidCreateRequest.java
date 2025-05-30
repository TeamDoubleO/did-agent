package com.doubleo.didagent.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DidCreateRequest(@NotBlank String routingKey, @NotBlank String serviceEndpoint) {}
