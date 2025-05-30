package com.doubleo.didagent.global.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class PeerDidUtil {

    /**
     * did:peer:2 DID를 생성합니다.
     *
     * @param publicKeyBase58 z6... 형식의 공개키 (인증 및 키교환 동일 사용)
     * @param routingKey z6... 형식의 라우팅 키 (1개만 사용, 없으면 null 또는 빈 문자열)
     * @param serviceEndpoint DIDComm 메시지를 받을 서비스 엔드포인트
     * @return did:peer:2 DID 문자열
     */
    public static String createPeerDid2(
            String publicKeyBase58, String routingKey, String serviceEndpoint) {
        // enc1: 인증용 키
        String enc1 = "V" + publicKeyBase58;

        // enc2: 키 교환용 키 (현재는 동일 키 사용)
        String enc2 = "E" + publicKeyBase58;

        // enc3: 서비스 JSON → base64url 인코딩
        JSONObject service = new JSONObject();
        service.put("id", "#didcomm-0");
        service.put("type", "did-communication");
        service.put("priority", 0);

        JSONArray recipientKeys = new JSONArray().put("#key-1");
        service.put("recipientKeys", recipientKeys);

        if (routingKey != null && !routingKey.isBlank()) {
            service.put("routingKeys", new JSONArray().put(routingKey));
        }

        service.put("serviceEndpoint", serviceEndpoint);

        String enc3 =
                "S"
                        + Base64.getUrlEncoder()
                                .withoutPadding()
                                .encodeToString(
                                        service.toString().getBytes(StandardCharsets.UTF_8));

        // 최종 DID 조합
        return "did:peer:2." + enc1 + "." + enc2 + "." + enc3;
    }
}
