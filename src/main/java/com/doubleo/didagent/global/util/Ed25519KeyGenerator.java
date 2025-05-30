package com.doubleo.didagent.global.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.util.Arrays;
import org.bitcoinj.core.Base58;

public class Ed25519KeyGenerator {
    /** JDK 17 Ed25519 키쌍을 생성해 raw + Base58 형식으로 리턴 */
    public static KeyMaterial generate() throws Exception {
        // 1. 표준 JDK로 키쌍 생성
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();

        // 2. raw 32 byte 추출
        byte[] pubSpki = kp.getPublic().getEncoded(); // 44 bytes (12 byte header + 32 byte raw)
        byte[] rawPub = Arrays.copyOfRange(pubSpki, pubSpki.length - 32, pubSpki.length);

        byte[] privPkcs8 = kp.getPrivate().getEncoded(); // 48 bytes (16 byte header + 32 byte raw)
        byte[] rawPriv = Arrays.copyOfRange(privPkcs8, privPkcs8.length - 32, privPkcs8.length);

        // 3. multicodec(+multibase) 접두어 - 0xED01 (‘ed25519-pub’) + Base58btc(z…)
        byte[] prefixed = new byte[rawPub.length + 2];
        prefixed[0] = (byte) 0xED; // multicodec 0xED 0x01
        prefixed[1] = 0x01;
        System.arraycopy(rawPub, 0, prefixed, 2, rawPub.length);

        String publicKeyBase58 = "z" + Base58.encode(prefixed); // multibase ‘z’ + Base58
        String privateKeyBase58 = Base58.encode(rawPriv); // 비공개키는 접두어 없이 raw 만

        return new KeyMaterial(
                rawPub,
                rawPriv,
                publicKeyBase58,
                privateKeyBase58,
                (EdECPublicKey) kp.getPublic(),
                (EdECPrivateKey) kp.getPrivate());
    }

    /** 결과 보관용 record (Java 16+ 기능) */
}
