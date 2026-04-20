package com.crypto.app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class HashTools {

    public static final String HASH_ALGORITHM = "SHA-256";

    private HashTools() {
        // Empêcher l'instanciation
    }

    public static String generateSHA256(final String text) throws Exception {
        if (text == null) {
            throw new IllegalArgumentException("Le texte à hasher ne doit pas être nul.");
        }

        final MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        final byte[] hashBytes = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));

        final StringBuilder hexBuilder = new StringBuilder();

        for (final byte currentByte : hashBytes) {
            final String hexValue = Integer.toHexString(0xff & currentByte);
            if (hexValue.length() == 1) {
                hexBuilder.append('0');
            }
            hexBuilder.append(hexValue);
        }

        return hexBuilder.toString();
    }

    public static boolean compareHashes(final String firstHash, final String secondHash) {
        if (firstHash == null || secondHash == null) {
            return false;
        }
        return firstHash.equalsIgnoreCase(secondHash);
    }
}