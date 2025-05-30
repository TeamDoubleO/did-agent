package com.doubleo.didagent.service;

import com.doubleo.didagent.dto.request.DidCreateRequest;
import com.doubleo.didagent.dto.response.DidCreateResponse;
import com.doubleo.didagent.global.exception.CommonException;
import com.doubleo.didagent.global.exception.errorcode.DidErrorCode;
import com.doubleo.didagent.global.util.Ed25519KeyGenerator;
import com.doubleo.didagent.global.util.KeyMaterial;
import com.doubleo.didagent.global.util.PeerDidUtil;
import com.doubleo.didagent.global.util.PeerDidValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DidService {

    public DidCreateResponse createPeer2Did(DidCreateRequest request) {
        KeyMaterial key = getKeyMaterial();
        String peer2Did =
                PeerDidUtil.createPeerDid2(
                        key.pub58(), request.routingKey(), request.serviceEndpoint());
        PeerDidValidator.isValidPeerDid2(peer2Did);
        log.info("Created PeerDid2: {}", peer2Did);
        return new DidCreateResponse(peer2Did, key.pub58(), key.priv58());
    }

    private KeyMaterial getKeyMaterial() throws CommonException {
        try {
            return Ed25519KeyGenerator.generate();
        } catch (Exception e) {
            throw new CommonException(DidErrorCode.KEY_GENERATION_FAILED);
        }
    }
}
