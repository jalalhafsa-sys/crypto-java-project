package com.crypto.app;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public final class EncryptionTools {

    public static final String ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES";

    private EncryptionTools() {
        // Empêcher l'instanciation
    }

    public static String encryptText(final String plainText, final SecretKey secretKey) throws Exception {
        if (plainText == null || plainText.isEmpty()) {
            throw new IllegalArgumentException("Le texte à chiffrer ne doit pas être vide.");
        }

        if (secretKey == null) {
            throw new IllegalArgumentException("La clé secrète ne doit pas être nulle.");
        }

        final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        final byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
        final byte[] encryptedBytes = cipher.doFinal(plainBytes);

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}