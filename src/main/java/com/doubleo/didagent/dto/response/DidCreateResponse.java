package com.doubleo.didagent.dto.response;

import jakarta.validation.constraints.NotBlank;

public record DidCreateResponse(
        @NotBlank String peerDid2, @NotBlank String publicKey, @NotBlank String privateKey) {}
