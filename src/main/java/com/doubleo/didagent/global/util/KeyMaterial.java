package com.doubleo.didagent.global.util;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;

public record KeyMaterial(
        @NotNull byte[] rawPub,
        @NotNull byte[] rawPriv,
        @NotBlank String pub58,
        @NotBlank String priv58,
        @NotNull EdECPublicKey jdkPub,
        @NotNull EdECPrivateKey jdkPriv) {}
