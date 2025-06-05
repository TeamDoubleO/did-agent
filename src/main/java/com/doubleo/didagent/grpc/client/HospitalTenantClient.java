package com.doubleo.didagent.grpc.client;

import com.doubleo.tenantservice.domain.tenant.grpc.HospitalTenantServiceGrpc;
import com.doubleo.tenantservice.domain.tenant.grpc.TenantWalletToken;
import com.doubleo.tenantservice.domain.tenant.grpc.UpdateTokensRequest;
import com.doubleo.tenantservice.domain.tenant.grpc.UpdateTokensResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HospitalTenantClient {

    @GrpcClient("tenant-service")
    private final HospitalTenantServiceGrpc.HospitalTenantServiceBlockingStub blockingStub;

    public HospitalTenantClient(
            HospitalTenantServiceGrpc.HospitalTenantServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

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
}
