package com.doubleo.didagent.grpc.client;

import com.doubleo.tenantservice.domain.tenant.grpc.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HospitalTenantClient {

    @GrpcClient("tenant-service")
    private HospitalTenantServiceGrpc.HospitalTenantServiceBlockingStub blockingStub;

    public UpdateTokensResponse updateTokens(Map<String, String> tokens) {
        UpdateTokensRequest.Builder builder = UpdateTokensRequest.newBuilder();

        for (Map.Entry<String, String> entry : tokens.entrySet()) {
            TenantWalletToken token =
                    TenantWalletToken.newBuilder()
                            .setTenantId(entry.getKey())
                            .setWalletToken(entry.getValue())
                            .build();
            builder.addTokens(token);
        }

        UpdateTokensRequest request = builder.build();

        return blockingStub.updateTokensByTenantId(request);
    }

    public GetTokenResponse getTokenByTenantId(String tenantId) {
        GetTokensRequest.Builder builder = GetTokensRequest.newBuilder();
        GetTokensRequest request = builder.setTenantId(tenantId).build();
        return blockingStub.getTokenByTenantId(request);
    }

    public String getTenantIdByHospitalId(Long hospitalId) {

        try {
            HospitalIdToTenantIdRequest request =
                    HospitalIdToTenantIdRequest.newBuilder().setHospitalId(hospitalId).build();
            HospitalIdToTenantIdResponse response = blockingStub.getTenantIdByHospitalId(request);
            return response.getTenantId();
        } catch (Exception e) {
            throw new StatusRuntimeException(
                    Status.INTERNAL.withDescription(e.getMessage()).withCause(e));
        }
    }
}
