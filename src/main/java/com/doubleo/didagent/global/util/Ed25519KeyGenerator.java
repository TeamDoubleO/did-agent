package com.doubleo.didagent.global.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.util.Arrays;
import org.bitcoinj.core.Base58;
import org.bouncycastle.jcajce.interfaces.XDHPrivateKey;
import org.bouncycastle.jcajce.interfaces.XDHPublicKey;

public class Ed25519KeyGenerator {

    public static KeyMaterial generate() throws Exception {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();

        byte[] pubSpki = kp.getPublic().getEncoded();
        byte[] rawPub = Arrays.copyOfRange(pubSpki, pubSpki.length - 32, pubSpki.length);

        byte[] privPkcs8 = kp.getPrivate().getEncoded();
        byte[] rawPriv = Arrays.copyOfRange(privPkcs8, privPkcs8.length - 32, privPkcs8.length);

        byte[] prefixed = new byte[rawPub.length + 2];
        prefixed[0] = (byte) 0xED;
        prefixed[1] = 0x01;
        System.arraycopy(rawPub, 0, prefixed, 2, rawPub.length);

        String publicKeyBase58 = "z" + Base58.encode(prefixed);
        String privateKeyBase58 = Base58.encode(rawPriv);

        KeyPairGenerator xKpg = KeyPairGenerator.getInstance("X25519", "BC");
        KeyPair xKp = xKpg.generateKeyPair();

        byte[] xPubSpki = xKp.getPublic().getEncoded();
        byte[] xRawPub = Arrays.copyOfRange(xPubSpki, xPubSpki.length - 32, xPubSpki.length);

        /* multicodec: 0xEC 0x01 = X25519 public key */
        byte[] xPrefixed = new byte[xRawPub.length + 2];
        xPrefixed[0] = (byte) 0xEC;
        xPrefixed[1] = 0x01;
        System.arraycopy(xRawPub, 0, xPrefixed, 2, xRawPub.length);

        String agreementKeyMb58 = "z" + Base58.encode(xPrefixed);
        byte[] xPrivSpki = xKp.getPrivate().getEncoded();
        // PKCS#8 또는 SPKI 형식으로 인코딩된 값이 넘어오므로, 끝 32바이트가 실제 raw private
        byte[] xRawPriv = Arrays.copyOfRange(xPrivSpki, xPrivSpki.length - 32, xPrivSpki.length);

        // multicodec 형식 붙이기 (0xEC 0x01 = X25519 private multicodec)
        byte[] xPrivPrefixed = new byte[xRawPriv.length + 2];
        xPrivPrefixed[0] = (byte) 0xEC;
        xPrivPrefixed[1] = 0x01;
        System.arraycopy(xRawPriv, 0, xPrivPrefixed, 2, xRawPriv.length);

        // 최종적으로 multibase58 (z-prefixed) 문자열
        String x25519PrivateMb58 = "z" + Base58.encode(xPrivPrefixed);

        /* 반환 객체에 추가로 포함 */
        return new KeyMaterial(
                rawPub,
                rawPriv,
                publicKeyBase58,
                privateKeyBase58,
                agreementKeyMb58, // NEW: X25519 public multibase
                (EdECPublicKey) kp.getPublic(),
                (EdECPrivateKey) kp.getPrivate(),
                (XDHPublicKey) xKp.getPublic(),
                (XDHPrivateKey) xKp.getPrivate(),
                x25519PrivateMb58);
    }
}
