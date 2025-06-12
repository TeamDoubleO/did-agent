package com.doubleo.didagent.grpc.server;

import com.doubleo.didagent.agent.HospitalAgent;
import com.doubleo.didagent.dto.request.hospital.HospitalInvitationCreateRequest;
import com.doubleo.didagent.dto.response.hospital.HospitalInvitationCreateResponse;
import com.doubleo.didagent.grpc.client.HospitalTenantClient;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AcapyGrpcServiceImpl extends AcapyServiceGrpc.AcapyServiceImplBase {

    private final HospitalAgent hospitalAgent;
    private final HospitalTenantClient hospitalTenantClient;

    @Override
    public void getPassConnectionId(
            PassConnectionIdRequest request,
            StreamObserver<PassConnectionIdResponse> responseObserver) {

        HospitalInvitationCreateResponse invitation =
                hospitalAgent
                        .createHospitalInvitation(
                                HospitalInvitationCreateRequest.create(request.getTenantId()),
                                "token"
                                //                ,hospitalTenantClient.getTenantId()
                                )
                        .block();
        invitation.invitationUrl();
        hospitalAgent.getHospitalConnectionInfoByOobId();

        hospitalAgent.createHospitalDid();
        hospitalAgent.postHospitalDid();
        hospitalAgent.issueHospitalVc();

        PassConnectionIdResponse response =
                PassConnectionIdResponse.newBuilder().setPassConnectionId(sb.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
