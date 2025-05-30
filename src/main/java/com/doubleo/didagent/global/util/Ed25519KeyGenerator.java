package com.doubleo.didagent.global.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.util.Arrays;
import org.bitcoinj.core.Base58;

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

        return new KeyMaterial(
                rawPub,
                rawPriv,
                publicKeyBase58,
                privateKeyBase58,
                (EdECPublicKey) kp.getPublic(),
                (EdECPrivateKey) kp.getPrivate());
    }
}
