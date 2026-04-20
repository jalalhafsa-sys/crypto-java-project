package com.crypto.app;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public final class DecryptionTools {

    public static final String TRANSFORMATION = "AES";

    private DecryptionTools() {
        // Empêcher l'instanciation
    }

    public static String decryptText(final String encryptedText, final SecretKey secretKey) throws Exception {
        if (encryptedText == null || encryptedText.isEmpty()) {
            throw new IllegalArgumentException("Le texte chiffré ne doit pas être vide.");
        }

        if (secretKey == null) {
            throw new IllegalArgumentException("La clé secrète ne doit pas être nulle.");
        }

        final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        final byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        final byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}