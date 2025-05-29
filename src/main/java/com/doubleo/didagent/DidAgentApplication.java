package com.doubleo.didagent;

import com.doubleo.didagent.global.util.Ed25519KeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DidAgentApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DidAgentApplication.class, args);

        var keyMat = Ed25519KeyGenerator.generate();

        System.out.println("✅ raw public 32 bytes : " + keyMat.rawPub().length);
        System.out.println("🔐 Public  (multibase) : " + keyMat.pub58());
        System.out.println("🔒 Private (base58raw) : " + keyMat.priv58());
    }
}
