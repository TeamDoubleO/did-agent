package com.doubleo.didagent.grpc.client;

import com.doubleo.passservice.grpc.server.PassServiceGrpc;
import com.doubleo.passservice.grpc.server.UpdateConnectionStatusRequest;
import com.doubleo.passservice.grpc.server.UpdateConnectionStatusResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PassClient {

    @GrpcClient("pass-service")
    private PassServiceGrpc.PassServiceBlockingStub blockingStub;

    public UpdateConnectionStatusResponse updateConnectionStatus(
            String tenantId, Long passId, String connectionId) {
        UpdateConnectionStatusRequest.Builder builder = UpdateConnectionStatusRequest.newBuilder();

        UpdateConnectionStatusRequest request =
                builder.setTenantId(tenantId)
                        .setPassId(passId)
                        .setConnectionId(connectionId)
                        .build();

        return blockingStub.updateConnectionState(request);
    }
}
