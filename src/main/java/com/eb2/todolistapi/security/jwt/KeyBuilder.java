package com.eb2.todolistapi.security.jwt;

import com.eb2.todolistapi.security.properties.RsaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeyBuilder {


    private final RsaProperties rsaProperties;


    public PublicKey loadPublicKey() {

        try {
            String keyAsString = rsaProperties.getPublicKey().getContentAsString(StandardCharsets.UTF_8);

            String publicKeyPEM = this.cleanKey(keyAsString);

            byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));

        }  catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }


    }

    public PrivateKey loadPrivateBuilder() {

        try {
            String keyAsString = rsaProperties.getPrivateKey().getContentAsString(StandardCharsets.UTF_8);
            String privateKeyPEM = this.cleanKey(keyAsString);

            byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));

        }  catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    private String cleanKey(String keyAsString) {

        return Arrays.stream(keyAsString.split("\n"))
                .filter(line -> !line.startsWith("-----BEGIN") && !line.startsWith("-----END"))
                .map(String::trim)
                .collect(Collectors.joining());
    }
}
