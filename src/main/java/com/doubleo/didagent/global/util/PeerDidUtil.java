package com.doubleo.didagent.global.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class PeerDidUtil {

    public static String createPeerDid2(
            String publicKeyBase58, String routingKey, String serviceEndpoint) {

        String enc1 = "V" + publicKeyBase58;

        String enc2 = "E" + publicKeyBase58;

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

        return "did:peer:2." + enc1 + "." + enc2 + "." + enc3;
    }
}
