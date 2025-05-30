package com.doubleo.didagent.global.util;

import com.doubleo.didagent.global.exception.CommonException;
import com.doubleo.didagent.global.exception.errorcode.DIDErrorCode;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Base58;
import org.json.JSONObject;

@Slf4j
public class PeerDidValidator {

    public static void isValidPeerDid2(String did) {
        try {
            if (!did.startsWith("did:peer:2.")) {
                log.error("did:peer:2 형식이 아닙니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }

            String[] parts = did.substring("did:peer:2.".length()).split("\\.");
            if (parts.length != 3) {
                log.error("DID는 enc1, enc2, enc3 세 부분으로 구성되어야 합니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }

            // --- enc1 ---
            String enc1 = parts[0];
            if (!enc1.startsWith("V") || !enc1.substring(1).startsWith("z")) {
                log.error("enc1 (V 접두사)가 올바르지 않습니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }
            Base58.decode(enc1.substring(2)); // 'Vz6...'

            // --- enc2 ---
            String enc2 = parts[1];
            if (!enc2.startsWith("E") || !enc2.substring(1).startsWith("z")) {
                log.error("enc2 (E 접두사)가 올바르지 않습니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }
            Base58.decode(enc2.substring(2)); // 'Ez6...'

            // --- enc3 ---
            String enc3 = parts[2];
            if (!enc3.startsWith("S")) {
                log.error("enc3 (S 접두사)가 없습니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }

            String serviceBase64 = enc3.substring(1);
            byte[] serviceBytes = Base64.getUrlDecoder().decode(serviceBase64);
            String serviceJsonString = new String(serviceBytes, StandardCharsets.UTF_8);
            JSONObject service = new JSONObject(serviceJsonString);

            if (!service.has("id") || !service.has("type") || !service.has("serviceEndpoint")) {
                log.error("service JSON에 필수 필드가 없습니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }

            if (!service.has("recipientKeys")) {
                log.error("recipientKeys가 없습니다.");
                throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
            }

            log.info("peer did check completed");

        } catch (CommonException e) {
            log.error("예외 발생: {}", e.getMessage());
            throw new CommonException(DIDErrorCode.MALFORMED_PEER_DID);
        }
    }
}
